package com.AntiChess;

import java.util.ArrayList;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class AntiChess {

    public static ProgramState programState = ProgramState.PLAY;

    public static Game mainGame = new Game(); // game that is shown on screen

    public static Move promotionMove;

    public static boolean isFlipped = false;

    public static void init() {

        Gui.init();
    }

    public static void run() {

        while (!WindowShouldClose()) {

            GameLoop.update();

            BeginDrawing();
            ClearBackground(BLACK);
            GameLoop.draw();
            EndDrawing();
        }
    }
}
