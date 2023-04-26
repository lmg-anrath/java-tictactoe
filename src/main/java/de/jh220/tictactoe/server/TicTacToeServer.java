package de.jh220.tictactoe.server;

import de.nrw.zentralabitur.netzwerke.Server;

import java.util.HashMap;

public class TicTacToeServer extends Server {
    private Database database;
    private HashMap<String, Token> tokens;
    public TicTacToeServer(int port) {
        super(port);
        database = new Database();
        database.connect("127.0.0.1", "root", "", "tictactoe");
        tokens = new HashMap<>();
        System.out.println("Server finished initializing and is now running under port " + port + ".");
    }

    @Override
    public void processNewConnection(String pClientIP, int pClientPort) {
        System.out.println("New connection from " + pClientIP + ":" + pClientPort);
        send(pClientIP, pClientPort, "connected");
    }

    @Override
    public void processMessage(String ip, int port, String message) {
        System.out.println("Received message from " + ip + ":" + port + ": " + message);
        String[] args = message.split(":");
        if (args[0].equals("login")) {
            if (database.login(args[1], args[2])) {
                Token token = new Token(ip, port);
                tokens.put(args[1], token);
                send(ip, port, "login:success:" + args[1] + ':' + token.getToken());
            } else
                send(ip, port, "login:failed");
        } else if (args[0].equals("logout")) {
            if (tokens.containsKey(args[1]) && tokens.get(args[1]).getToken().equals(args[2])) {
                tokens.remove(args[1]);
                send(ip, port, "logout:success");
            } else
                send(ip, port, "logout:failed");
        } else if (args[0].equals("register")) {
            if (database.exists(args[1])) {
                send(ip, port, "register:failed");
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
        System.out.println("Connection from " + pClientIP + ":" + pClientPort + " closed.");
    }

    @Override
    public void send(String ip, int port, String message) {
        System.out.println("Sending message to " + ip + ":" + port + ": " + message);
        super.send(ip, port, message);
    }
}