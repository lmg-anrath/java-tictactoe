package de.jh220.tictactoe.server;

import de.jh220.tictactoe.client.TicTacToeClient;

public class Main {
    public static void main(String[] args) {
        TicTacToeServer server = new TicTacToeServer(5000);
    }
}