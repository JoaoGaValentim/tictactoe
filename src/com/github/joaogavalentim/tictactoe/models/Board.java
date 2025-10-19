package com.github.joaogavalentim.tictactoe.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
    private List<String> boardHouses;
    private String currentPlayer;
    private List<Observer> observers = new ArrayList<>();

    private final int[][] positions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    public Board() {
        this.boardHouses = new ArrayList<>(Collections.nCopies(9, ""));
        this.currentPlayer = "X";
    }

    public void addContext(Observer observer) {
        this.observers.add(observer);
    }

    public boolean makeMove(String player, int position) {
        if (position < 0 || position >= boardHouses.size() || !boardHouses.get(position).isEmpty()) {
            return false;
        }

        boardHouses.set(position, player);
        notifyObservers();

        if (checkPlayerWin(player)) {
            notifyWinner(player);
        } else if (isTie()) {
            notifyTie();
        } else {
            this.currentPlayer = player.equals("X") ? "O" : "X";
        }

        return true;
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.updateBoard(boardHouses);
        }
    }

    private void notifyWinner(String winner) {
        for (Observer observer : observers) {
            observer.showResult("O jogador " + winner + " venceu!");
        }
    }

    private void notifyTie() {
        for (Observer observer : observers) {
            observer.showResult("Empate!");
        }
    }

    public boolean checkPlayerWin(String player) {
        for (int[] winningCombination : positions) {
            if (boardHouses.get(winningCombination[0]).equals(player) &&
                    boardHouses.get(winningCombination[1]).equals(player) &&
                    boardHouses.get(winningCombination[2]).equals(player)) {
                return true;
            }
        }
        return false;
    }

    public boolean isTie() {
        for (String house : boardHouses) {
            if (house.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void reset() {
        this.boardHouses = new ArrayList<>(Collections.nCopies(9, ""));
        this.currentPlayer = "X";
        notifyObservers();
    }

    public List<String> getBoardHouses() {
        return boardHouses;
    }
}