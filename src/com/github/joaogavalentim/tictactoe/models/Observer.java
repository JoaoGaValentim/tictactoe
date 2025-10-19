package com.github.joaogavalentim.tictactoe.models;

import java.util.List;

public interface Observer {
    void updateBoard(List<String> houses);
    void showResult(String message);
}
