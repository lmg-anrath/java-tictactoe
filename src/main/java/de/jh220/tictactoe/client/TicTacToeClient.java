package de.jh220.tictactoe.client;

import de.nrw.zentralabitur.netzwerke.Client;
import de.jh220.tictactoe.client.gui.*;

import javax.swing.*;

public class TicTacToeClient extends Client {
    private String ip;
    private int port;

    private String username;
    private String token;
    private String awaitMessage;

    private HomeScreenGUI gui;
    private LoginGUI loginGUI;
    private SinglePlayerGUI singlePlayerGUI;
    private MultiPlayerGUI multiPlayerGUI;
    private MultiPlayerHomeGUI multiPlayerHomeGUI;

    public TicTacToeClient(String ip, int port) {
        super(ip, port);
        this.ip = ip;
        this.port = port;
        username = null;
        token = null;
        awaitMessage = null;
        (gui = new HomeScreenGUI(this)).setVisible(true);
        loginGUI = new LoginGUI(this);
        singlePlayerGUI = new SinglePlayerGUI(this);
        multiPlayerGUI = new MultiPlayerGUI(this);
        multiPlayerHomeGUI = new MultiPlayerHomeGUI(this);
    }

    @Override
    public void processMessage(String message) {
        System.out.println("Received message: " + message);
        String[] args = message.split(":");
        if (args[0].equals("connected")) {
            System.out.println("Connected to the server on " + ip + ":" + port + ".");
            return;
        }
        if (awaitMessage != null) {
            if (awaitMessage.equals("login")) {
                if(args[0].equals("login")) {
                    if (args[1].equals("success")) {
                        finishLogin(args[2], args[3]);
                    } else if (args[1].equals("failed"))
                        JOptionPane.showMessageDialog(loginGUI, "Der Benutzername oder das Passwort ist falsch.", "TicTacToe - Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (awaitMessage.equals("logout")) {
                if(args[0].equals("logout")) {
                    if (args[1].equals("success"))
                        JOptionPane.showMessageDialog(loginGUI, "Du wurdest erfolgreich abgemeldet!", "TicTacToe - Info", JOptionPane.INFORMATION_MESSAGE);
                    else if (args[1].equals("failed"))
                        JOptionPane.showMessageDialog(loginGUI, "Die Sitzung war bereits abgelaufen!", "TicTacToe - Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (awaitMessage.equals("register")) {
                if(args[0].equals("register")) {
                    if (args[1].equals("success")) {
                        JOptionPane.showMessageDialog(loginGUI, "Du hast dich erfolgreich registriert.", "TicTacToe - Info", JOptionPane.INFORMATION_MESSAGE);
                    } else if (args[1].equals("failed")) {
                        if (args[2].equals("userexists"))
                            JOptionPane.showMessageDialog(loginGUI, "Der Benutzername existiert bereits.", "TicTacToe - Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    public void login(String username, String password) {
        send("login:" + username + ":" + password);
        awaitMessage = "login";
    }
    private void finishLogin(String username, String token) {
        this.username = username;
        this.token = token;
        loginGUI.setVisible(false);
        loginGUI = new LoginGUI(this);
        gui.login(username);
        gui.setButtonsEnabled(true);
        gui.requestFocus();
    }

    public void logout() {
        send("logout:" + username + ":" + token);
        username = null;
        token = null;
        awaitMessage = "logout";
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
    public MultiPlayerGUI getMultiPlayerGUI() {
        return multiPlayerGUI;
    }
    public MultiPlayerHomeGUI getMultiPlayerHomeGUI() {
        return multiPlayerHomeGUI;
    }
}