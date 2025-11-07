package com.AntiChess;

public enum ProgramState {

    MENU(0),
    PLAY(1),
    PROMOTION(2),
    GAMEOVER(3);

    final int state;

    ProgramState(int state) {

        this.state = state;
    }
}
