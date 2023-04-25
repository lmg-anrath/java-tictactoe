package de.jh220.tictactoe.client;

import de.jh220.tictactoe.abitur.Client;
import de.jh220.tictactoe.client.gui.LoginGUI;
import de.jh220.tictactoe.client.gui.TicTacToeGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeClient extends Client {
    private LoginGUI loginGUI;
    private TicTacToeGUI gui;

    String awaitMessage;

    public TicTacToeClient(String ip, int port) {
        super(ip, port);
        awaitMessage = null;
        loginGUI = new LoginGUI(this);
        gui = new TicTacToeGUI();
        loginGUI.showGUI();
        //gui.showGUI(); // for testing purposes
    }

    @Override
    public void processMessage(String message) {
        System.out.println("Received message: " + message);
        String[] args = message.split(":");
        if (awaitMessage != null) {
            if (awaitMessage.equals("login")) {
                if (args[0].equals("login")) {
                    if (args[1].equals("success")) {
                        loginGUI.hideGUI();
                        gui.showGUI();
                    } else {
                        loginGUI.showLoginFailed();
                    }
                }
            }
        }
    }

    public void login(String username, String password) {
        send("login:" + username + ":" + password);
        awaitMessage = "login";
    }

    public void register(String username, String password) {
        send("register:" + username + ":" + password);
    }
}