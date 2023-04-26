package de.jh220.tictactoe.client.gui;

import de.jh220.tictactoe.client.TicTacToeClient;
import de.jh220.tictactoe.game.GameHandler;
import de.jh220.tictactoe.client.listeners.GameCloseWindowListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SinglePlayerGUI extends JFrame implements ActionListener {
    private TicTacToeClient client;
    private GameHandler game;

    private int size;
    private JButton[][] buttons;
    private JButton reset;
    private JPanel panel;
    private JLabel label;

    public SinglePlayerGUI(TicTacToeClient client) {
        super("TicTacToe - Singleplayer");
        this.client = client;
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(300, 300);

        game = new GameHandler();
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
        addWindowListener(new GameCloseWindowListener(client));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == reset) {
            game = new GameHandler();
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