package de.jh220.tictactoe.server;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class Database {
    private Connection connection;

    public void connect(String host, String user, String password, String database) {
        long start = System.currentTimeMillis();
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database + "?autoReconnect=true&useSSL=false", user, password);
            System.out.println("Successfully connected to the database! (" + (System.currentTimeMillis() - start) + "ms)");

            connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS users (username VARCHAR(255), password VARCHAR(255), PRIMARY KEY (username))");
            connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS points (username VARCHAR(255), points INT, PRIMARY KEY (username))");
        } catch (SQLException exception) {
            System.out.println("An error occurred while connecting to the database!");
            System.out.println("Message: " + exception.getMessage());
        }
    }

    public boolean login(String username, String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            String hashedPassword = new String(messageDigest.digest(password.getBytes()));
            PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return resultSet.getString("password").equals(hashedPassword);
        } catch (SQLException exception) {
            System.out.println("An error occurred while checking the credentials!");
            System.out.println("Message: " + exception.getMessage());
        } catch (NoSuchAlgorithmException exception) {
            throw new RuntimeException(exception);
        }
        return false;
    }
    public boolean exists(String username) {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return true;
        } catch (SQLException exception) {
            System.out.println("An error occurred while looking up the user!");
            System.out.println("Message: " + exception.getMessage());
        }
        return false;
    }

    public boolean register(String username, String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            String hashedPassword = new String(messageDigest.digest(password.getBytes()));
            PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException exception) {
            System.out.println("An error occurred while registering the user!");
            System.out.println("Message: " + exception.getMessage());
        } catch (NoSuchAlgorithmException exception) {
            throw new RuntimeException(exception);
        }
        return false;
    }

    public int getPoints(String username) {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM points WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt("points");
        } catch (SQLException exception) {
            System.out.println("An error occurred while looking up the user!");
            System.out.println("Message: " + exception.getMessage());
        }
        return 0;
    }

    public void addPoints(String username, int points) {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("UPDATE points SET points = points + ? WHERE username = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, points);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println("An error occurred while adding points to the user!");
            System.out.println("Message: " + exception.getMessage());
        }
    }

    // get top 3
    public String getScoreboard(String username) {
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM points ORDER BY points DESC");
            ResultSet resultSet = preparedStatement.executeQuery();
            int i = 1;
            String top = "";
            while (resultSet.next()) {
                if (i <= 3) {
                    top += i + ". " + resultSet.getString("username") + " - " + resultSet.getInt("points") + "\n";
                } else if (resultSet.getString("username").equals(username)) {
                    top += i + ". " + resultSet.getString("username") + " - " + resultSet.getInt("points") + "\n";
                }
                i++;
            }
            return top;
        } catch (SQLException exception) {
            System.out.println("An error occurred while getting the top 3!");
            System.out.println("Message: " + exception.getMessage());
        }
        return "";
    }
}