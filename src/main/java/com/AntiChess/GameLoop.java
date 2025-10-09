package com.AntiChess;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

import com.rayu.RayU;

import java.util.ArrayList;

public class GameLoop {

    public static void update() {

        if( IsMouseButtonPressed( 0 ) && ActivePiece.isNull() )
            pickUpPiece();

        else if( IsMouseButtonReleased(0) && !ActivePiece.isNull() )
            dropPiece();
    }

    public static void draw() {

        Gui.drawBoard();
        Gui.highlightLegalMoves();               // using info from ActivePiece
        Gui.displayPieces( AntiChess.mainGame );
        Gui.displayActivePiece();
    }

    public static void pickUpPiece() {

        final int[] mouse    = Util.getMouseCoordinates();
        final int colClicked = mouse[0];
        final int rowClicked = mouse[1];

        if( !Util.isOnBoard( colClicked, rowClicked ) )
            return;

        if( AntiChess.mainGame.board[rowClicked][colClicked] == ' ' )
            return;

        final char piece = AntiChess.mainGame.board[rowClicked][colClicked];

        //if( Util.colorOfPiece( piece ) != AntiChess.mainGame.turn )
            //return;

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

        if( !Util.isOnBoard( colClicked, rowClicked ) ) {

            ActivePiece.clear();
            return;
        }

        ArrayList<Move> legalMoves = Moves.getKingMoves( AntiChess.mainGame, ActivePiece.col, ActivePiece.row );

        Move move = new Move(ActivePiece.type, ActivePiece.col, ActivePiece.row, colClicked, rowClicked);
        move.addExtraInfo( ActivePiece.type, ActivePiece.col, ActivePiece.row, colClicked, rowClicked );

        if( Util.isMoveInArrayList( legalMoves, move )  )
            AntiChess.mainGame.makeMove( move );

        ActivePiece.clear();
    }
}
