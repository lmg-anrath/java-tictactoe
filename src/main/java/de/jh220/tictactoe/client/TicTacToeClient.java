package de.jh220.tictactoe.client;

import de.nrw.zentralabitur.netzwerke.Client;
import de.jh220.tictactoe.client.gui.*;

import javax.swing.*;

public class TicTacToeClient extends Client {
    private String ip;
    private int port;

    private String username;
    private String token;
    private int points;
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
        points = -1;
        awaitMessage = null;
        (gui = new HomeScreenGUI(this)).setVisible(true);
        loginGUI = new LoginGUI(this);
        singlePlayerGUI = new SinglePlayerGUI(this);
        multiPlayerGUI = null;
        multiPlayerHomeGUI = new MultiPlayerHomeGUI(this);
    }
    public TicTacToeClient(String ip, int port, String username, String password) {
        super(ip, port);
        this.ip = ip;
        this.port = port;
        this.username = null;
        token = null;
        points = -1;
        awaitMessage = null;
        (gui = new HomeScreenGUI(this)).setVisible(true);
        loginGUI = new LoginGUI(this);
        singlePlayerGUI = new SinglePlayerGUI(this);
        multiPlayerGUI = null;
        multiPlayerHomeGUI = new MultiPlayerHomeGUI(this);
        login(username, password);
    }

    @Override
    public void processMessage(String message) {
        System.out.println("Received message: " + message);
        String[] args = message.split(":");
        if (args[0].equals("connected")) {
            System.out.println("Connected to the server on " + ip + ":" + port + ".");
            return;
        } else if (args[0].equals("request")) {
            String challenger = args[1];
            int result = JOptionPane.showConfirmDialog(multiPlayerHomeGUI, "Der Spieler " + challenger + " möchte gegen dich spielen. Möchtest du die Herausforderung annehmen?", "TicTacToe - Info", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                send("accept:" + username + ":" + token + ":" + challenger);
                startRemoteGame(challenger, false);
            } else {
                send("deny:" + username + ":" + token + ":" + challenger);
            }
        } else if (args[0].equals("game")) {
            String type = args[1];
            if (type.equals("move")) {
                int row = Integer.parseInt(args[2]);
                int col = Integer.parseInt(args[3]);
                multiPlayerGUI.setField(row, col, 'O');
            } else if (type.equals("won")) {
                JOptionPane.showMessageDialog(multiPlayerGUI, "Du hast gewonnen!", "TicTacToe - Info", JOptionPane.INFORMATION_MESSAGE);
                multiPlayerGUI.setVisible(false);
                multiPlayerHomeGUI.setVisible(true);
            } else if (type.equals("lose")) {
                JOptionPane.showMessageDialog(multiPlayerGUI, "Du hast verloren!", "TicTacToe - Info", JOptionPane.INFORMATION_MESSAGE);
                multiPlayerGUI.setVisible(false);
                multiPlayerHomeGUI.setVisible(true);
            } else if (type.equals("tie")) {
                JOptionPane.showMessageDialog(multiPlayerGUI, "Unentschieden!", "TicTacToe - Info", JOptionPane.INFORMATION_MESSAGE);
                multiPlayerGUI.setVisible(false);
                multiPlayerHomeGUI.setVisible(true);
            }
        } else if(args[0].equals("move")) {
            if (args[1].equals("failed")) return;
            int row = Integer.parseInt(args[2]);
            int col = Integer.parseInt(args[3]);
            multiPlayerGUI.setField(row, col, 'X');
        }
        String awaitMessage = this.awaitMessage;
        if (awaitMessage != null) {
            this.awaitMessage = null;
            if (awaitMessage.equals("login")) {
                if(args[0].equals("login")) {
                    if (args[1].equals("success")) {
                        System.out.println("Logged in as " + args[2] + ".");
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
                        JOptionPane.showMessageDialog(loginGUI, "Der Benutzername existiert bereits.", "TicTacToe - Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else if (awaitMessage.equals("points")) {
                if(args[0].equals("points")) {
                    if (args[1].equals("failed")) {
                        JOptionPane.showMessageDialog(loginGUI, "Die Sitzung war bereits abgelaufen!", "TicTacToe - Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    points = Integer.parseInt(args[1]);
                    gui.setPoins(points);
                }
            } else if (awaitMessage.startsWith("challenge")) {
                if(args[0].equals("challenge")) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                    multiPlayerHomeGUI.setSearching(false);
                    switch (args[1]) {
                        case "error":
                            switch (args[2]) {
                                case "timeout" ->
                                        JOptionPane.showMessageDialog(multiPlayerHomeGUI, "Die Herausforderung wurde nicht angenommen.", "TicTacToe - Error", JOptionPane.ERROR_MESSAGE);
                                case "ratelimited" ->
                                        JOptionPane.showMessageDialog(multiPlayerHomeGUI, "Du kannst nur alle 10 Sekunden eine Herausforderung an einen Spieler senden.", "TicTacToe - Error", JOptionPane.ERROR_MESSAGE);
                                case "offline" ->
                                        JOptionPane.showMessageDialog(multiPlayerHomeGUI, "Der Spieler ist aktuell offline oder existiert nicht.", "TicTacToe - Info", JOptionPane.INFORMATION_MESSAGE);
                            }
                            break;
                        case "accepted":
                            String user = awaitMessage.split(":")[1];
                            startRemoteGame(user, true);
                            JOptionPane.showMessageDialog(multiPlayerHomeGUI, "Der Spieler " + user + " hat die Herausforderung angenommen.", "TicTacToe - Info", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        case "denied":
                            JOptionPane.showMessageDialog(multiPlayerHomeGUI, "Der Spieler hat die Herausforderung abgelehnt.", "TicTacToe - Info", JOptionPane.INFORMATION_MESSAGE);
                            break;
                    }
                }
            }
        }
    }

    public void login(String username, String password) {
        if (awaitMessage("login")) return;
        send("login:" + username + ":" + password);
    }
    private void finishLogin(String username, String token) {
        this.username = username;
        this.token = token;
        loginGUI.setVisible(false);
        loginGUI = new LoginGUI(this);
        gui.login(username);
        gui.setButtonsEnabled(true);
        gui.requestFocus();
        fetchPoints();
    }

    public void logout() {
        if (awaitMessage("logout")) return;
        send("logout:" + username + ":" + token);
        username = null;
        token = null;
    }
    public void register(String username, String password) {
        if (awaitMessage("register")) return;
        send("register:" + username + ":" + password);
    }

    public void challenge(String user) {
        if (awaitMessage("challenge:" + user)) return;
        send("challenge:" + username + ":" + token + ":" + user);
    }

    public void fetchPoints() {
        if (awaitMessage("points")) return;
        send("points:" + username + ":" + token);
    }

    public void move(int row, int col) {
        send("move:" + username + ":" + token + ":" + row + ":" + col);
    }

    private boolean awaitMessage(String message) {
        if (!isConnected()) {
            JOptionPane.showMessageDialog(loginGUI, "Der Server ist aktuell nicht erreichbar. Bitte versuche es später erneut!", "TicTacToe - Error", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        if (awaitMessage != null) {
            JOptionPane.showMessageDialog(loginGUI, "Bitte warte, bis die vorherige Aktion abgeschlossen ist!", "TicTacToe - Error", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        awaitMessage = message;
        return false;
    }

    public MultiPlayerGUI startRemoteGame(String opponent, boolean starts) {
        if (multiPlayerGUI != null) multiPlayerGUI.setVisible(false);
        multiPlayerGUI = new MultiPlayerGUI(this, opponent, starts);
        multiPlayerGUI.setVisible(true);
        gui.setVisible(false);
        multiPlayerHomeGUI.setVisible(false);
        return multiPlayerGUI;
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
    public String getUsername() {
        return username;
    }
    public String getToken() {
        return token;
    }

    @Override
    public void send(String message) {
        System.out.println("Sending message: " + message);
        super.send(message);
    }
}