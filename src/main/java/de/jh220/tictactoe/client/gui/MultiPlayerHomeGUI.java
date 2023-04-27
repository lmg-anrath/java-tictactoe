package de.jh220.tictactoe.client.gui;

import de.jh220.tictactoe.client.TicTacToeClient;
import de.jh220.tictactoe.client.listeners.MultiPlayerHomeCloseWindowListener;

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
        add(searchLabel, BorderLayout.PAGE_START);

        backButton = new JButton("Back");
        backButton.addActionListener(this);
        add(backButton, BorderLayout.SOUTH);

        addWindowListener(new MultiPlayerHomeCloseWindowListener(client));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == searchButton) {
            if (usernameField.getText().equals(client.getUsername())) {
                JOptionPane.showMessageDialog(this, "Du kannst dich nicht selbst herausfordern!", "TicTacToe - Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            client.challenge(usernameField.getText());
            setSearching(true);
        } else if (event.getSource() == backButton) {
            client.getGUI().setVisible(true);
            setVisible(false);
        }
    }

    public void setSearching(boolean searching) {
        searchButton.setEnabled(!searching);
        usernameField.setEnabled(!searching);
        backButton.setEnabled(!searching);
        searchLabel.setVisible(searching);
    }
}