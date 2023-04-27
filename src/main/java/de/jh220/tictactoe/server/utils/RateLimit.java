package de.jh220.tictactoe.server.utils;

import de.jh220.tictactoe.server.User;

public class RateLimit {
    private User challenger;
    private User challenged;
    private long unbanTime;

    public RateLimit(User challenger, User challenged) {
        this.challenger = challenger;
        this.challenged = challenged;
        unbanTime = System.currentTimeMillis() + 10000;
    }

    public boolean isAllowed() {
        return System.currentTimeMillis() > unbanTime;
    }

    public User getChallenger() {
        return challenger;
    }
    public User getChallenged() {
        return challenged;
    }
}