package de.jh220.tictactoe.client.gui;

import de.jh220.tictactoe.client.TicTacToeClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartGUI extends JFrame implements ActionListener {
    private JTextField serverField, portField;
    private JButton connectButton;

    public StartGUI() {
        super("Connect to Server");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setSize(300, 150);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Server IP/Host:"));
        serverField = new JTextField("localhost");
        inputPanel.add(serverField);
        inputPanel.add(new JLabel("Port:"));
        portField = new JTextField("3000");
        inputPanel.add(portField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        connectButton = new JButton("Connect");
        connectButton.addActionListener(this);
        buttonPanel.add(connectButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == connectButton) {
            String server = serverField.getText();
            String port = portField.getText();
            if (server.isEmpty() || port.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a server and port", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    int portInt = Integer.parseInt(port);
                    TicTacToeClient client = new TicTacToeClient(server, portInt);
                    setVisible(false);
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid port", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
