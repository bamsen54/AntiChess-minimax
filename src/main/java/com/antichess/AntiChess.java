package com.antichess;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class AntiChess {

    public static void run() {

        while( !WindowShouldClose() ) {

            BeginDrawing();
            ClearBackground( BLACK );
            EndDrawing();
        }
    }
}
