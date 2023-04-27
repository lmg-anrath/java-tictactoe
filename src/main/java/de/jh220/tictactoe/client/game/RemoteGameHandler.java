package de.jh220.tictactoe.client.game;

import de.jh220.tictactoe.client.TicTacToeClient;

import java.util.Random;

public class RemoteGameHandler {
    private TicTacToeClient client;
    private String opponent;
    private char current;

    public RemoteGameHandler(TicTacToeClient client, String opponent, boolean first) {
        this.client = client;
        this.opponent = opponent;
        current = first ? 'X' : 'O';
    }

    public String getCurrentLabel() {
        if (getCurrentPlayerMark() == 'X') {
            return "Du bist am Zug!";
        } else {
            return "Warte auf " + opponent + "...";
        }
    }
    public char getCurrentPlayerMark() {
        return current;
    }

    public void setMark(int row, int col) {
        client.move(row, col);
    }

    public void newGame() {
        // TODO
    }

    public void switchUser() {
        current = current == 'X' ? 'O' : 'X';
    }
}