package com.antichess;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class AntiChess {

    Game mainGame = new Game(); // game that is shown on screen

    public static void init() {

        
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
