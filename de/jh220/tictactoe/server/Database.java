package de.jh220.tictactoe.server;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class Database {
    private Connection connection;

    public void connect(String host, String user, String password, String database) {
        try {
            long start = System.currentTimeMillis();
            this.connection = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database + "?autoReconnect=true&useSSL=false", user, password);
            System.out.println("Successfully connected to the database! (" + (System.currentTimeMillis() - start) + "ms)");

            connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS users (username VARCHAR(255), password VARCHAR(255))");
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
            if (exists(username)) return false;
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
}