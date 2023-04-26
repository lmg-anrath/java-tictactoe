package de.jh220.tictactoe.client.game;

import de.jh220.tictactoe.client.TicTacToeClient;

import java.util.Random;

public class RemoteGameHandler {
    private TicTacToeClient client;

    public RemoteGameHandler(TicTacToeClient client) {
        this.client = client;
    }

    public String getCurrentPlayer() {
        return new Random().nextBoolean() ? "Spieler " + "JH220" + " (" + getCurrentPlayerMark() + ") ist am Zug." : "Du (" + getCurrentPlayerMark() + ") bist am Zug.";
    }
    public char getCurrentPlayerMark() {
        return new Random().nextBoolean() ? 'X' : 'O';
    }

    public boolean setMark(int row, int col) {
        return true; // no player already placed there
        //return false; // player already placed there
    }

    public boolean checkWin() {
        return false; // no player won
        //return true; // player won
    }
    public boolean isTie() {
        return false; // no tie
        //return true; // tie
    }

    public void newGame() {
        // TODO
    }

    /*public void switchPlayer() {
        // have to implement in server
    }*/
}