package de.jh220.tictactoe.client.gui;

import de.jh220.tictactoe.client.TicTacToeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI extends JFrame implements ActionListener {
    private TicTacToeGame game;
    private int size;
    private JButton[][] buttons;
    private JButton reset;
    private JPanel panel;
    private JLabel label;

    public TicTacToeGUI() {
        super("TicTacToe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);

        game = new TicTacToeGame();
        size = 3;

        buttons = new JButton[size][size];
        (reset = new JButton("Neues Spiel")).addActionListener(this);
        (panel = new JPanel()).setLayout(new GridLayout(size, size));
        label = new JLabel("Spieler " + game.getCurrentPlayer() + " ist am Zug.");

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                buttons[row][col] = new JButton("-");
                buttons[row][col].addActionListener(this);
                panel.add(buttons[row][col]);
            }
        }

        add(panel, BorderLayout.CENTER);
        add(label, BorderLayout.SOUTH);
    }

    public void showGUI() {
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == reset) {
            game = new TicTacToeGame();
            Container contentPane = getContentPane();
            contentPane.remove(contentPane.getComponentCount() - 1);
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    buttons[row][col].setEnabled(true);
                    buttons[row][col].setText("-");
                }
            }
            label.setText("Spieler " + game.getCurrentPlayer() + " ist am Zug.");
            return;
        }

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (event.getSource() == buttons[row][col]) {
                    if (game.setMark(row, col)) {
                        buttons[row][col].setText(game.getCurrentPlayer() + "");
                        if (game.checkWin()) {
                            label.setText("Spieler " + game.getCurrentPlayer() + " hat gewonnen!");
                            endGame();
                        } else if (game.isFull()) {
                            label.setText("Unentschieden!");
                            endGame();
                        } else {
                            game.switchPlayer();
                            label.setText("Spieler " + game.getCurrentPlayer() + " ist am Zug.");
                        }
                    }
                }
            }
        }
    }

    private void endGame() {
        add(reset, BorderLayout.NORTH);
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                buttons[row][col].setEnabled(false);
            }
        }
    }
}