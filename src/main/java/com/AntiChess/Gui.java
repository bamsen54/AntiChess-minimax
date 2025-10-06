package com.AntiChess;

import java.util.HashMap;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

import com.rayu.RayU;

public class Gui {

    public static int boardPositionX;
    public static int boardPositionY;
    public static int squareSize;
    public static int marginAll;

    public static Color lightSquareColor = RayU.color( 241, 242, 249 );
    public static Color darkSquareColor  = RayU.color( 53,   53,  64 );

    public static boolean isFlipped = false;

    public static HashMap<Character, Texture> icons = new HashMap<Character, Texture>();

    public static void init() {

        final int screenWidth             = GetScreenWidth();
        final int screenHeight            = GetScreenHeight();
        final int smallestDimension = Math.min( screenWidth, screenHeight );

        marginAll = Math.max( (int) ( 0.025 * GetScreenWidth() ), (int) ( 0.025 * GetScreenHeight() ) ); // 2.5% of width

        squareSize     = (int) ( ( smallestDimension - 2 * marginAll ) / 8.0);
        boardPositionY = marginAll;
        boardPositionX = screenWidth / 2 - 4 * squareSize;
    }
}
