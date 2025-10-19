package com.github.joaogavalentim.tictactoe.views;

import com.github.joaogavalentim.tictactoe.models.Board;
import com.github.joaogavalentim.tictactoe.models.Observer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

public class Game extends JFrame implements Observer, ActionListener {
    private final List<JButton> buttons = new ArrayList<>();
    private final Board board = new Board();
    private final Random random = new Random();

    public Game() {
        super("Jogo da Velha");
        board.addContext(this);
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel boardPane = new JPanel();
        boardPane.setLayout(new GridLayout(3, 3));
        makeBoard();
        buttons.forEach(boardPane::add);

        add(boardPane, BorderLayout.CENTER);
        setVisible(true);
    }

    private void makeBoard() {
        for (int i = 0; i < 9; i++) {
            JButton button = new JButton();
            button.setOpaque(true);
            button.setFont(new Font("Arial", Font.BOLD, 40));
            button.setBorder(new LineBorder(Color.BLACK, 2));
            button.addActionListener(this);
            button.setActionCommand(String.valueOf(i));
            buttons.add(button);
        }
    }

    @Override
    public void updateBoard(List<String> houses) {
        for (int i = 0; i < houses.size(); i++) {

            if(houses.get(i).equalsIgnoreCase("o")) {
                buttons.get(i).setForeground(Color.RED);
            }

            if (houses.get(i).equalsIgnoreCase("x")) {
                buttons.get(i).setForeground(Color.BLACK);
            }

            buttons.get(i).setText(houses.get(i));
        }
    }

    @Override
    public void showResult(String message) {
        JOptionPane.showMessageDialog(this, message);

        int option = JOptionPane.showConfirmDialog(this, "Deseja jogar novamente?", "Fim de Jogo", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            board.reset();
        }

        if (option == JOptionPane.NO_OPTION) {
            System.exit(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            int position = Integer.parseInt(button.getActionCommand());
            String currentPlayer = board.getCurrentPlayer();

            if (board.makeMove(currentPlayer, position)) {
                if (!board.checkPlayerWin("X") && !board.isTie()) {
                    Timer timer = new Timer(500, ae -> makePcMove());
                    timer.setRepeats(false);
                    timer.start();
                }
            }
        }
    }

    private void makePcMove() {
        int pcPosition;
        do {
            pcPosition = random.nextInt(9);
        } while (!board.getBoardHouses().get(pcPosition).isEmpty());

        board.makeMove("O", pcPosition);
    }
}