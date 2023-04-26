package de.jh220.tictactoe.client.gui;

import de.jh220.tictactoe.client.TicTacToeClient;
import de.jh220.tictactoe.client.game.RemoteGameHandler;
import de.jh220.tictactoe.client.listeners.GameCloseWindowListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MultiPlayerGUI extends JFrame implements ActionListener {
    private TicTacToeClient client;
    private RemoteGameHandler game;
    private int size;
    private JButton[][] buttons;
    private JButton reset;
    private JPanel panel;
    private JLabel label;

    public MultiPlayerGUI(TicTacToeClient client) {
        super("TicTacToe - Multiplayer");
        this.client = client;
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(300, 300);

        game = new RemoteGameHandler(client);
        size = 3;

        buttons = new JButton[size][size];
        (reset = new JButton("Nochmals herausfordern!")).addActionListener(this);
        (panel = new JPanel()).setLayout(new GridLayout(size, size));
        label = new JLabel(game.getCurrentPlayer());

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
            Container contentPane = getContentPane();
            contentPane.remove(contentPane.getComponentCount() - 1);
            reset();
            return;
        }

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (event.getSource() == buttons[row][col]) {
                    if (buttons[row][col].getText().equals("-")) {
                        game.setMark(row, col);
                        buttons[row][col].setText(game.getCurrentPlayerMark() + "");
                        label.setText(game.getCurrentPlayer());
                    }
                }
            }
        }
    }

    private void endGame(boolean win) {
        if (win) label.setText("Spieler " + game.getCurrentPlayer() + " hat gewonnen!");
        else label.setText("Unentschieden!");
        add(reset, BorderLayout.NORTH);
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                buttons[row][col].setEnabled(false);
            }
        }
    }
    public void reset() {
        game.newGame();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                buttons[row][col].setEnabled(true);
                buttons[row][col].setText("-");
            }
        }
        label.setText(game.getCurrentPlayer());
    }
}