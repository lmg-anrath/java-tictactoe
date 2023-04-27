package de.jh220.tictactoe.client.gui;

import de.jh220.tictactoe.client.TicTacToeClient;
import de.jh220.tictactoe.client.game.RemoteGameHandler;
import de.jh220.tictactoe.client.listeners.MultiPlayerGameCloseWindowListener;
import de.jh220.tictactoe.client.listeners.MultiPlayerHomeCloseWindowListener;
import de.jh220.tictactoe.client.listeners.SinglePlayerGameCloseWindowListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MultiPlayerGUI extends JFrame implements ActionListener {
    private TicTacToeClient client;
    private String opponent;
    private RemoteGameHandler game;
    private int size;
    private JButton[][] buttons;
    private JButton reset;
    private JPanel panel;
    private JLabel label;

    public MultiPlayerGUI(TicTacToeClient client, String opponent, boolean starts) {
        super("TicTacToe - Multiplayer");
        this.client = client;
        this.opponent = opponent;
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(300, 300);

        game = new RemoteGameHandler(client, opponent, starts);
        size = 3;

        buttons = new JButton[size][size];
        (reset = new JButton("Nochmals spielen!")).addActionListener(this);
        (panel = new JPanel()).setLayout(new GridLayout(size, size));
        label = new JLabel(game.getCurrentLabel());

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
        addWindowListener(new MultiPlayerGameCloseWindowListener(client));
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
                    }
                }
            }
        }
    }

    private void endGame(boolean win) {
        if (win) {
            if (game.getCurrentPlayerMark() == 'X')
                label.setText("Du hast gewonnen!");
            else
                label.setText("Spieler " + opponent + " (" + game.getCurrentPlayerMark() + ") hat gewonnen!");
        } else
            label.setText("Unentschieden!");
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
        label.setText(game.getCurrentLabel());
    }

    public String getOpponent() {
        return opponent;
    }

    public void setField(int row, int col, char user) {
        buttons[row][col].setText(user + "");
        game.switchUser();
        label.setText(game.getCurrentLabel());
    }
}