package de.jh220.tictactoe.client.gui;

import de.jh220.tictactoe.client.TicTacToeClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame implements ActionListener {
    private TicTacToeClient client;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginGUI(TicTacToeClient client) {
        super("TicTacToe - Login");
        this.client = client;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));
        setSize(300, 150);

        JLabel usernameLabel = new JLabel("Benutzername:");
        usernameField = new JTextField();
        add(usernameLabel);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Passwort:");
        passwordField = new JPasswordField();
        add(passwordLabel);
        add(passwordField);

        loginButton = new JButton("Anmelden");
        loginButton.addActionListener(this);
        registerButton = new JButton("Registrieren");
        registerButton.addActionListener(this);
        add(loginButton);
        add(registerButton);
    }

    public void showGUI() {
        setVisible(true);
    }
    public void hideGUI() {
        setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bitte Benutzername und Passwort eingeben!", "TicTacToe - Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!username.matches("[a-zA-Z0-9]+")) {
            JOptionPane.showMessageDialog(this, "Der Benutzername darf nur Buchstaben und Zahlen enthalten!", "TicTacToe - Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (password.contains(":")) {
            JOptionPane.showMessageDialog(this, "Das Passwort darf kein Doppelpunkt enthalten!", "TicTacToe - Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (event.getSource() == loginButton) {
            System.out.println("Login: " + username + ":" + password);
            client.login(username, password);
        } else if (event.getSource() == registerButton) {
            if (client.getexists(username)) return false;
            client.register(username, password);
        }
    }
}