package de.jh220.tictactoe.client.gui;

import de.jh220.tictactoe.client.TicTacToeClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeScreenGUI extends JFrame implements ActionListener {
    private TicTacToeClient client;
    private JButton singleplayerButton, multiplayerButton, quitButton, loginButton;
    private JLabel loggedInLabel;

    public HomeScreenGUI(TicTacToeClient client) {
        super("TicTacToe - Home");
        this.client = client;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setSize(300, 300);

        (singleplayerButton = new JButton("Singleplayer")).addActionListener(this);
        (multiplayerButton = new JButton("Multiplayer")).addActionListener(this);
        (quitButton = new JButton("Quit Game")).addActionListener(this);
        (loginButton = new JButton("Login")).addActionListener(this);
        loggedInLabel = new JLabel("Not logged in");

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
        buttonPanel.add(singleplayerButton);
        buttonPanel.add(multiplayerButton);
        buttonPanel.add(quitButton);

        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        loginPanel.add(loginButton);
        loginPanel.add(loggedInLabel);

        add(buttonPanel, BorderLayout.CENTER);
        add(loginPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == singleplayerButton) {
            SinglePlayerGUI game = new SinglePlayerGUI(client);
            game.setVisible(true);
            setVisible(false);
        } else if (event.getSource() == multiplayerButton) {
            JOptionPane.showMessageDialog(this, "This feature is not implemented yet!", "TicTacToe - Info", JOptionPane.INFORMATION_MESSAGE);
        } else if (event.getSource() == quitButton) {
            System.exit(0);
        } else if (event.getSource() == loginButton) {
            JOptionPane.showMessageDialog(this, "This feature is not implemented yet!", "TicTacToe - Info", JOptionPane.INFORMATION_MESSAGE);
            /*
            LoginGUI login = new LoginGUI(client);
            setButtonsEnabled(false);
            login.setVisible(true);
            */
        }
    }

    public void setButtonsEnabled(boolean enabled) {
        singleplayerButton.setEnabled(enabled);
        multiplayerButton.setEnabled(enabled);
        quitButton.setEnabled(enabled);
        loginButton.setEnabled(enabled);
    }
}