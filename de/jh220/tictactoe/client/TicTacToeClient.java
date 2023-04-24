package de.jh220.tictactoe.client;

import de.jh220.tictactoe.abitur.Client;
import de.jh220.tictactoe.client.gui.LoginGUI;
import de.jh220.tictactoe.client.gui.TicTacToeGUI;

public class TicTacToeClient extends Client {
    private LoginGUI loginGUI;
    private TicTacToeGUI gui;

    public TicTacToeClient(String ip, int port) {
        super(ip, port);
        loginGUI = new LoginGUI();
        gui = new TicTacToeGUI();
        loginGUI.showGUI();
        //gui.showGUI(); // for testing purposes
    }

    @Override
    public void processMessage(String pMessage) {
        // TODO
    }
}