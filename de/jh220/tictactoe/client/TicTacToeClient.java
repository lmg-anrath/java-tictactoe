package de.jh220.tictactoe.client;

import de.nrw.zentralabitur.netzwerke.Client;
import de.jh220.tictactoe.client.gui.*;

public class TicTacToeClient extends Client {
    private HomeScreenGUI gui;
    private LoginGUI loginGUI;
    private SinglePlayerGUI singlePlayerGUI;
    String awaitMessage;

    public TicTacToeClient(String ip, int port) {
        super(ip, port);
        awaitMessage = null;
        (gui = new HomeScreenGUI(this)).setVisible(true);
        loginGUI = new LoginGUI(this);
        singlePlayerGUI = new SinglePlayerGUI(this);
    }

    @Override
    public void processMessage(String message) {
        System.out.println("Received message: " + message);
        String[] args = message.split(":");
        if (awaitMessage != null) {
            if (awaitMessage.equals("login")) {
                if (args[0].equals("login")) {
                    // TODO
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
        awaitMessage = "register";
    }

    public HomeScreenGUI getGUI() {
        return gui;
    }
    public LoginGUI getLoginGUI() {
        return loginGUI;
    }
    public SinglePlayerGUI getSinglePlayerGUI() {
        return singlePlayerGUI;
    }
}