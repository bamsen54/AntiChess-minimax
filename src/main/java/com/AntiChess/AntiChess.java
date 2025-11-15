package com.AntiChess;

import java.util.ArrayList;
import java.util.List;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class AntiChess {

    public static ProgramState programState = ProgramState.PLAY;

    public static Game mainGame = new Game(); // game that is shown on screen
    public static List<Move> moveHistory = new ArrayList<>();

    public static Move promotionMove;

    public static Move latestMove;

    // game parameters
    public static boolean forceAlternatingTurns = false;
    public static boolean mandatoryCapture      = false;

    public static boolean isFlipped = false;

    public static void init() {

        Gui.init();
    }

    public static void run() {

        while (!WindowShouldClose()) {
            IO.println( AntiChess.mainGame.enPassantSquareHistory.size() );

            GameLoop.update();
            BeginDrawing();
            ClearBackground(BLACK);
            GameLoop.draw();
            EndDrawing();
        }
    }
}
