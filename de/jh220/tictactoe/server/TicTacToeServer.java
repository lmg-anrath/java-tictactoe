package de.jh220.tictactoe.server;

import de.nrw.zentralabitur.netzwerke.Server;

import java.util.HashMap;

public class TicTacToeServer extends Server {
    private Database database;
    private HashMap<String, Token> tokens;
    public TicTacToeServer(int port) {
        super(port);
        database = new Database();
        database.connect("localhost", "root", "", "tictactoe");
        tokens = new HashMap<>();
        System.out.println("Server finished initializing and is now running under port " + port + ".");
    }

    @Override
    public void processNewConnection(String pClientIP, int pClientPort) {

    }

    @Override
    public void processMessage(String ip, int port, String message) {
        System.out.println("Received message from " + ip + ":" + port + ": " + message);
        String[] args = message.split(":");
        if (args[0].equals("login")) {
            if (database.login(args[1], args[2])) {
                Token token = new Token(ip, port);
                tokens.put(args[1], token);
                send(ip, port, "login:success:" + token.getToken());
            } else
                send(ip, port, "login:failed");
        } else if (args[0].equals("register")) {
            if (database.exists(args[1])) {
                send(ip, port, "register:failed:userexists");
                return;
            }
            if (database.register(args[1], args[2])) {
                send(ip, port, "register:success");
            } else {
                send(ip, port, "register:failed");
            }
        }
    }

    @Override
    public void processClosingConnection(String pClientIP, int pClientPort) {

    }
}