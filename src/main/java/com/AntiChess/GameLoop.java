package com.AntiChess;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

import com.rayu.RayU;

public class GameLoop {

    public static void update() {

        if( IsMouseButtonPressed( 0 ) )
            pieceMovement();
    }

    public static void draw() {

        Gui.draw_board();
        Gui.displayPieces( AntiChess.mainGame );
    }

    public static void pieceMovement() {

        final int s  = Gui.squareSize;
        final int x0 = Gui.boardPositionX;
        final int y0 = Gui.boardPositionY;
        final int bw = 8 * s;

        Vector2 mouse        = GetMousePosition();
        final int colClicked = (int ) Math.floor( Util.mapInterval( mouse.x(), x0, x0 + bw, 0, 8 ) );
        final int rowClicked = (int ) Math.floor( Util.mapInterval( mouse.y(), y0, y0 + bw, 0, 8 ) );

        System.out.println( colClicked + " " + rowClicked );

        if( ActivePiece.isNull() )
            pickUpPiece( colClicked, rowClicked );

        else
            dropPiece( colClicked, rowClicked );
    }

    public static void pickUpPiece(int colClicked, int rowClicked) {


    }

    public static void dropPiece(int colClicked, int rowClicked) {

        
    }
}
