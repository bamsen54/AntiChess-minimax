package com.AntiChess;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

import com.rayu.RayU;

public class GameLoop {

    public static void update() {

        if( IsMouseButtonPressed( 0 ) && ActivePiece.isNull() )
            pickUpPiece();

        else if( IsMouseButtonReleased(0) && !ActivePiece.isNull() )
            dropPiece();
    }

    public static void draw() {

        Gui.draw_board();
        Gui.displayPieces( AntiChess.mainGame );
        Gui.displayActivePiece();
    }

    public static void pickUpPiece() {

        final int[] mouse = Util.getMouseCoordinates();

        final int colClicked = mouse[0];
        final int rowClicked = mouse[1];

        if( !Util.isOnBoard( colClicked, rowClicked ) )
            return;

        if( AntiChess.mainGame.board[rowClicked][colClicked] == ' ' )
            return;

        final char piece = AntiChess.mainGame.board[rowClicked][colClicked];

        if( Util.colorOfPiece( piece ) != AntiChess.mainGame.turn )
            return;

        // we have a piece that is same color as the current turn
        ActivePiece.type  =  piece;
        ActivePiece.col   =  colClicked;
        ActivePiece.row   =  rowClicked;
        ActivePiece.color =  AntiChess.mainGame.turn;
    }

    public static void dropPiece() {

        final int[] mouse = Util.getMouseCoordinates();

        final int colClicked = mouse[0];
        final int rowClicked = mouse[1];

        ActivePiece.clear();
    }
}
