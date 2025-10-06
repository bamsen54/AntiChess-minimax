package com.antichess;

import static com.raylib.Raylib.*;

public class Main {

    void main() {

        InitWindow( (int) 1.2 * 1280, (int) 1.2 * 720, "Anti Chess" );
        SetTargetFPS( 60 );

        AntiChess.run();

        CloseWindow();
    }
}
