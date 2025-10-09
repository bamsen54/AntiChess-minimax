package com.AntiChess;

import java.util.ArrayList;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class AntiChess {

    public static Game mainGame = new Game(); // game that is shown on screen

    public static boolean isFlipped = false;

    public static void init() {

        Gui.init();

        Move move1 = new Move('R', 1, 2, 3, 4);

        ArrayList<Move> moves = new ArrayList<>();
        moves.add(move1);

        System.out.println( Util.isMoveInArrayList( moves, move1 ) );
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
