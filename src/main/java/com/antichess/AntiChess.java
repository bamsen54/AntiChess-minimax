package com.antichess;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class AntiChess {

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
