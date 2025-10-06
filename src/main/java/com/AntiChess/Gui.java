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

    public static int screenWidth;
    public static int screenHeight;

    public static Color lightSquareColor = RayU.color( 241, 242, 249 );
    public static Color darkSquareColor  = RayU.color( 53,   53,  64 );

    public static HashMap<Character, Texture> icons = new HashMap<Character, Texture>();

    public static void init() {

        screenWidth  = GetScreenWidth();
        screenHeight = GetScreenHeight();

        final int smallestDimension = Math.min( screenWidth, screenHeight );

        marginAll = Math.max( (int) ( 0.025 * GetScreenWidth() ), (int) ( 0.025 * GetScreenHeight() ) ); // 2.5% of width

        squareSize     = (int) ( ( smallestDimension - 2 * marginAll ) / 8.0);
        boardPositionY = marginAll;
        boardPositionX = screenWidth / 2 - 4 * squareSize;
    }

    public static void draw_board() {

        for( int row = 0; row < 8; row++ ) {

            for( int col = 0; col < 8; col++ ) {

                int x = col;
                int y = row;

                if (AntiChess.isFlipped) {

                    x = 7 - col;
                    y = 7 - row;
                }

                x = boardPositionX + x * squareSize;
                y = boardPositionY + y * squareSize;

                if ((col + row) % 2 == 0)
                    DrawRectangle(x, y, squareSize, squareSize, lightSquareColor);

                else
                    DrawRectangle(x, y, squareSize, squareSize, darkSquareColor);


                //if( ActivePiece.col == col && ActivePiece.row == row ) {
                //
                //    DrawRectangle(x, y, s, s, Raylib.color(0, 255, 0, 150));
                //
                //    continue;
                //}
            }
        }
    }
}
