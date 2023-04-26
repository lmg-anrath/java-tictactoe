package de.jh220.tictactoe.client.gui;

import de.jh220.tictactoe.client.TicTacToeClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MultiPlayerHomeGUI extends JFrame implements ActionListener {
    private TicTacToeClient client;
    private JLabel titleLabel, usernameLabel, searchLabel;
    private JTextField usernameField;
    private JButton searchButton, backButton;

    public MultiPlayerHomeGUI(TicTacToeClient client) {
        super("TicTacToe - Multiplayer");
        this.client = client;

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setSize(400, 150);

        titleLabel = new JLabel("Multiplayer Menu");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(1, 3));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        usernameLabel = new JLabel("Username:");
        inputPanel.add(usernameLabel);
        usernameField = new JTextField();
        inputPanel.add(usernameField);
        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        inputPanel.add(searchButton);
        add(inputPanel, BorderLayout.CENTER);

        searchLabel = new JLabel("Searching for opponents...");
        searchLabel.setHorizontalAlignment(JLabel.CENTER);
        searchLabel.setVisible(false);
        add(searchLabel, BorderLayout.SOUTH);

        backButton = new JButton("Back");
        backButton.addActionListener(this);
        add(backButton, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent event) {

    }

    public void setSearching(boolean searching) {
        searchButton.setEnabled(!searching);
        usernameField.setEnabled(!searching);
        backButton.setEnabled(!searching);
        searchLabel.setVisible(searching);
    }
}