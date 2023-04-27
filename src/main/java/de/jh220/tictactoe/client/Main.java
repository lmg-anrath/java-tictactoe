package de.jh220.tictactoe.client;

import de.jh220.tictactoe.client.gui.StartGUI;

public class Main {
    public static void main(String[] args) {
        new StartGUI();
        //new TicTacToeClient("localhost", 3000, "jh", "123"); // for testing purposes to skip the gui
    }
}