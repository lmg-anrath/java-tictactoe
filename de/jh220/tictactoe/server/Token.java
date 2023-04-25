package de.jh220.tictactoe.server;

import java.security.SecureRandom;

public class Token {
    private String token;
    private long expirationTime;
    private String ip;
    private int port;

    public Token(String ip, int port) {
        byte bytes[] = new byte[20];
        new SecureRandom().nextBytes(bytes);
        token = bytes.toString();
        expirationTime = System.currentTimeMillis() + 3600000;
        this.ip = ip;
        this.port = port;
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
}