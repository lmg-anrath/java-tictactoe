package de.jh220.tictactoe.server;

import de.jh220.tictactoe.abitur.Server;

public class TicTacToeServer extends Server {
    private Database database;
    public TicTacToeServer(int port) {
        super(port);
        database = new Database();
        database.connect("localhost", "root", "", "tictactoe");
        System.out.println("Server finished initializing and is now running under port " + port + ".");
    }

    @Override
    public void processNewConnection(String pClientIP, int pClientPort) {

    }

    @Override
    public void processMessage(String pClientIP, int pClientPort, String pMessage) {

    }

    @Override
    public void processClosingConnection(String pClientIP, int pClientPort) {

    }
}