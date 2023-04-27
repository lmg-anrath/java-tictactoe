package de.jh220.tictactoe.server.game;

import de.jh220.tictactoe.server.User;

public class GameHandler {
    private String[][] board;
    private User player1;
    private User player2;
    private User currentPlayer;
    private String winField;

    public GameHandler(User player1, User player2) {
        board = new String[3][3];
        this.player1 = player1;
        this.player2 = player2;
        currentPlayer = player1;
        winField = null;
        initBoard();
    }

    public boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == null) return false;
            }
        }
        return true;
    }

    /**
     * Internal method to initialize the board when a new game is started.
     */
    private void initBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = null;
            }
        }
    }

    /**
     * Checks if the position is free and sets the mark if it is.
     * @param row the row of the position
     * @param col the column of the position
     * @return true if the position is free and the mark was set, false otherwise.
     */
    public boolean setMark(int row, int col) {
        if (checkWin()) return false;
        if ((row >= 0) && (row < 3)) {
            if ((col >= 0) && (col < 3)) {
                if (board[row][col] == null) {
                    board[row][col] = currentPlayer.getUsername();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the current player has won the game.
     * @return true if the current player has won the game, false otherwise.
     */
    public boolean checkWin() {
        return (checkRows() || checkColumns() || checkDiagonals());
    }

    private boolean checkRows() {
        for (int i = 0; i < 3; i++) {
            if (check(board[i][0], board[i][1], board[i][2])) {
                winField = i + ",0 " + i + ",1 " + i + ",2";
                return true;
            }
        }
        return false;
    }
    private boolean checkColumns() {
        for (int i = 0; i < 3; i++) {
            if (check(board[0][i], board[1][i], board[2][i])) {
                winField = "0," + i + " 1," + i + " 2," + i;
                return true;
            }
        }
        return false;
    }
    private boolean checkDiagonals() {
        if (check(board[0][0], board[1][1], board[2][2])) {
            winField = "0,0 1,1 2,2";
            return true;
        }
        if (check(board[0][2], board[1][1], board[2][0])) {
            winField = "0,2 1,1 2,0";
            return true;
        }
        return false;
    }
    private boolean check(String pos1, String pos2, String pos3) {
        return ((pos1 != null) && pos1.equals(pos2) && pos2.equals(pos3));
    }

    public void switchPlayer() {
        if (currentPlayer.equals(player1))
            currentPlayer = player2;
        else
            currentPlayer = player1;
    }
    public User getCurrentPlayer() {
        return currentPlayer;
    }

    public String getWinField() {
        return winField;
    }

    public boolean isPlaying(User user) {
        return (player1.equals(user) || player2.equals(user));
    }

    public User getOtherPlayer(User user) {
        if (player1.equals(user))
            return player2;
        else
            return player1;
    }
}