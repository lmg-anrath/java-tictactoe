package de.jh220.tictactoe.client.listeners;

import de.jh220.tictactoe.client.TicTacToeClient;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MultiPlayerHomeCloseWindowListener implements WindowListener {
    private TicTacToeClient client;

    public MultiPlayerHomeCloseWindowListener(TicTacToeClient client) {
        this.client = client;
    }

    @Override
    public void windowOpened(WindowEvent event) {

    }

    @Override
    public void windowClosing(WindowEvent event) {
        client.getGUI().setVisible(true);
    }

    @Override
    public void windowClosed(WindowEvent event) {

    }

    @Override
    public void windowIconified(WindowEvent event) {

    }

    @Override
    public void windowDeiconified(WindowEvent event) {

    }

    @Override
    public void windowActivated(WindowEvent event) {

    }

    @Override
    public void windowDeactivated(WindowEvent event) {

    }
}
