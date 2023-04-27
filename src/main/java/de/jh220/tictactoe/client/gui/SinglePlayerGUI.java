package de.jh220.tictactoe.client.gui;

import de.jh220.tictactoe.client.TicTacToeClient;
import de.jh220.tictactoe.client.game.SinglePlayerGameHandler;
import de.jh220.tictactoe.client.listeners.SinglePlayerGameCloseWindowListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SinglePlayerGUI extends JFrame implements ActionListener {
    private TicTacToeClient client;
    private SinglePlayerGameHandler game;

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

        game = new SinglePlayerGameHandler();
        size = 3;

        buttons = new JButton[size][size];
        (reset = new JButton("Neues Spiel")).addActionListener(this);
        (panel = new JPanel()).setLayout(new GridLayout(size, size));
        label = new JLabel("Spieler " + game.getCurrentPlayer() + " ist am Zug.");

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                buttons[row][col] = new JButton("-");
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[row][col].addActionListener(this);
                panel.add(buttons[row][col]);
            }
        }

        add(panel, BorderLayout.CENTER);
        add(label, BorderLayout.SOUTH);
        addWindowListener(new SinglePlayerGameCloseWindowListener(client));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == reset) {
            Container contentPane = getContentPane();
            contentPane.remove(contentPane.getComponentCount() - 1);
            reset();
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
        String winField = game.getWinField();
        if (winField != null) {
            String[] fields = winField.split(" ");
            for (String field : fields) {
                String[] coords = field.split(",");
                buttons[Integer.parseInt(coords[0])][Integer.parseInt(coords[1])].setBackground(Color.GREEN);
            }
        }
    }
    public void reset() {
        game = new SinglePlayerGameHandler();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                buttons[row][col].setEnabled(true);
                buttons[row][col].setText("-");
                buttons[row][col].setBackground(null);
            }
        }
        label.setText("Spieler " + game.getCurrentPlayer() + " ist am Zug.");
    }
}