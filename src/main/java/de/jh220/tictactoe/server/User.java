package de.jh220.tictactoe.server;

import java.security.SecureRandom;
import java.util.HashMap;

public class User {
    private String username;
    private String token;
    private long expirationTime;
    private String ip;
    private int port;
    private HashMap<String, Long> rateLimits;

    public User(String ip, int port, String username) {
        this.username = username;
        byte bytes[] = new byte[20];
        new SecureRandom().nextBytes(bytes);
        token = bytes.toString();
        expirationTime = System.currentTimeMillis() + 3600000;
        this.ip = ip;
        this.port = port;
        rateLimits = new HashMap<>();
    }

    public boolean validateToken(String token) {
        return this.token.equals(token) && System.currentTimeMillis() < expirationTime;
    }
    public String getToken() {
        return token;
    }

    public String getIp() {
        return ip;
    }
    public int getPort() {
        return port;
    }
    public String getUsername() {
        return username;
    }

    /**
     * Checks if the user is ratelimited and adds a ratelimit if not.
     * @param challenged The challenged user by this user
     * @return true if the user is ratelimited, false if not
     */
    public boolean isRatelimited(String challenged) {
        if (rateLimits.containsKey(challenged.toLowerCase())) {
            if (System.currentTimeMillis() > rateLimits.get(challenged.toLowerCase())) {
                rateLimits.put(challenged.toLowerCase(), System.currentTimeMillis() + 10000);
                return false;
            }
            else return true;
        } else {
            rateLimits.put(challenged.toLowerCase(), System.currentTimeMillis() + 10000);
            return false;
        }
    }
}