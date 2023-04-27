package de.jh220.tictactoe.server;

import de.jh220.tictactoe.server.game.GameHandler;
import de.nrw.zentralabitur.netzwerke.Server;

import java.util.*;

public class TicTacToeServer extends Server {
    private Database database;
    private HashMap<String, User> users;
    private HashMap<User, User> awaitingChallenges;
    private List<GameHandler> games;
    public TicTacToeServer(int port) {
        super(port);
        database = new Database();
        database.connect("127.0.0.1", "root", "", "tictactoe");
        users = new HashMap<>();
        awaitingChallenges = new HashMap<>();
        games = new ArrayList<>();
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
                User user = new User(ip, port, args[1]);
                users.put(args[1], user);
                send(ip, port, "login:success:" + args[1] + ':' + user.getToken());
            } else
                send(ip, port, "login:failed");
        } else if (args[0].equals("logout")) {
            if (users.containsKey(args[1]) && users.get(args[1]).getToken().equals(args[2])) {
                users.remove(args[1]);
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
        } else if (args[0].equals("points")) {
            if (users.containsKey(args[1]) && users.get(args[1]).getToken().equals(args[2])) {
                send(ip, port, "points:" + database.getPoints(args[1]));
            } else
                send(ip, port, "points:failed");
        } else if (args[0].equals("challenge")) {
            User user = users.get(args[1]);
            if (users.containsKey(args[1]) && user.getToken().equals(args[2])) {
                if (user.isRatelimited(args[3])) {
                    send(ip, port, "challenge:error:ratelimited");
                    return;
                }
                if (users.containsKey(args[3])) {
                    send(users.get(args[3]).getIp(), users.get(args[3]).getPort(), "request:" + args[1]);
                    awaitingChallenges.put(users.get(args[3]), users.get(args[1]));
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (awaitingChallenges.containsKey(users.get(args[3]))) {
                                awaitingChallenges.remove(users.get(args[3]));
                                send(ip, port, "challenge:error:timeout");
                            }
                        }
                    }, 30000);
                }
                else send(ip, port, "challenge:error:offline");
            }
            else send(ip, port, "challenge:error:failed");
        } else if (args[0].equals("accept")) {
            if (users.containsKey(args[1]) && users.get(args[1]).getToken().equals(args[2])) {
                User challenged = users.get(args[1]);
                if (awaitingChallenges.containsKey(challenged)) {
                    User challenger = awaitingChallenges.get(users.get(args[1]));
                    GameHandler game = new GameHandler(challenger, challenged);
                    games.add(game);
                    awaitingChallenges.remove(users.get(args[1]));
                    send(challenger.getIp(), challenger.getPort(), "challenge:accepted:" + args[1]);
                }
                else send(ip, port, "accept:failed");
            }
            else send(ip, port, "accept:failed");
        } else if (args[0].equals("deny")) {
            if (users.containsKey(args[1]) && users.get(args[1]).getToken().equals(args[2])) {
                if (awaitingChallenges.containsKey(users.get(args[1]))) {
                    User challenger = awaitingChallenges.get(users.get(args[1]));
                    awaitingChallenges.remove(users.get(args[1]));
                    send(challenger.getIp(), challenger.getPort(), "challenge:denied:" + args[1]);
                } else
                    send(ip, port, "deny:failed");
            } else
                send(ip, port, "deny:failed");
        } else if (args[0].equals("move")) {
            User user = users.get(args[1]);
            if (users.containsKey(args[1]) && user.getToken().equals(args[2])) {
                for (GameHandler game : games) {
                    if (game.isPlaying(user)) {
                        User opponent = game.getOtherPlayer(user);
                        if (game.getCurrentPlayer().equals(user)) {
                            if (game.setMark(Integer.parseInt(args[3]), Integer.parseInt(args[4]))) {
                                send(user.getIp(), user.getPort(), "move:success:" + args[3] + ':' + args[4]);
                                send(opponent.getIp(), opponent.getPort(), "game:move:" + args[3] + ':' + args[4]);
                                if (game.isFull()) {
                                    send(user.getIp(), user.getPort(), "game:tie");
                                    send(opponent.getIp(), opponent.getPort(), "game:tie");
                                }
                                else if (game.checkWin()) {
                                    send(user.getIp(), user.getPort(), "game:won");
                                    database.addPoints(user.getUsername(), 3);
                                    send(opponent.getIp(), opponent.getPort(), "game:lose");
                                    database.addPoints(opponent.getUsername(), 1);
                                }
                                game.switchPlayer();
                            }
                            else
                                send(ip, port, "move:failed");
                        } else
                            send(ip, port, "move:failed");
                    }
                }
            } else
                send(ip, port, "move:error:failed");
        }
    }

    @Override
    public void processClosingConnection(String ip, int port) {
        System.out.println("Connection from " + ip + ":" + port + " closed.");
    }

    @Override
    public void send(String ip, int port, String message) {
        System.out.println("Sending message to " + ip + ":" + port + ": " + message);
        super.send(ip, port, message);
    }
}