package de.jh220.tictactoe.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginGUI() {
        super("TicTacToe - Login");
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

    @Override
    public void actionPerformed(ActionEvent event) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bitte Benutzername und Passwort eingeben!", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (event.getSource() == loginButton) {
            System.out.println("Login: " + username + ":" + password);
        } else if (event.getSource() == registerButton) {
            System.out.println("Register: " + username + ":" + password);
        }
    }
}