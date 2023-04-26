package de.jh220.tictactoe.client.game;

public class SinglePlayerGameHandler {
    private char[][] board;
    private char currentPlayer;

    public SinglePlayerGameHandler() {
        board = new char[3][3];
        currentPlayer = 'X';
        initBoard();
    }

    public boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') return false;
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
                board[i][j] = '-';
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
        if ((row >= 0) && (row < 3)) {
            if ((col >= 0) && (col < 3)) {
                if (board[row][col] == '-') {
                    board[row][col] = currentPlayer;
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
            if (check(board[i][0], board[i][1], board[i][2])) return true;
        }
        return false;
    }
    private boolean checkColumns() {
        for (int i = 0; i < 3; i++) {
            if (check(board[0][i], board[1][i], board[2][i])) return true;
        }
        return false;
    }
    private boolean checkDiagonals() {
        return ((check(board[0][0], board[1][1], board[2][2])) || (check(board[0][2], board[1][1], board[2][0])));
    }
    private boolean check(int pos1, int pos2, int pos3) {
        return ((pos1 != '-') && (pos1 == pos2) && (pos2 == pos3));
    }

    public void switchPlayer() {
        if (currentPlayer == 'X') currentPlayer = 'O';
        else currentPlayer = 'X';
    }
    public char getCurrentPlayer() {
        return currentPlayer;
    }
}