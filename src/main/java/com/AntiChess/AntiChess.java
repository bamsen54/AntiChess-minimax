package com.AntiChess;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class AntiChess {

    public static Game mainGame = new Game(); // game that is shown on screen

    public static boolean isFlipped = false;

    public static void init() {

        System.out.println( mainGame );
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
