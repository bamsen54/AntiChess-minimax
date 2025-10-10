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

        if( IsMouseButtonPressed( 0 ) && AntiChess.programState == ProgramState.PROMOTION )
            promotion();


        keyPressed();
    }

    public static void draw() {

        Gui.drawBoard();
        Gui.highlightLegalMoves();               // using info from ActivePiece
        Gui.displayPieces( AntiChess.mainGame );
        Gui.displayActivePiece();
        Gui.displayPromotionChoices();
    }

    public static void pickUpPiece() {

        if( AntiChess.programState != ProgramState.PLAY )
            return;

        final int[] mouse = Util.getMouseCoordinates();
        int colClicked    = mouse[0];
        int rowClicked    = mouse[1];

        if( AntiChess.isFlipped ) {

            colClicked = 7 - colClicked;
            rowClicked = 7 - rowClicked;
        }

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
        int colClicked    = mouse[0];
        int rowClicked    = mouse[1];

        if( AntiChess.isFlipped ) {

            colClicked = 7 - colClicked;
            rowClicked = 7 - rowClicked;
        }

        if( !Util.isOnBoard( colClicked, rowClicked ) ) {

            ActivePiece.clear();
            return;
        }

        final char thisPiece = AntiChess.mainGame.board[ActivePiece.row][ActivePiece.col];

        ArrayList<Move> legalMoves = Moves.getPawnMoves( AntiChess.mainGame, ActivePiece.col, ActivePiece.row );

        Move move = new Move(ActivePiece.type, ActivePiece.col, ActivePiece.row, colClicked, rowClicked);
        move.addExtraInfo( ActivePiece.type, ActivePiece.col, ActivePiece.row, colClicked, rowClicked );

        if( thisPiece == 'P' && rowClicked == 0 ) {

            AntiChess.programState = ProgramState.PROMOTION;

            AntiChess.mainGame.board[rowClicked][colClicked] = thisPiece;
            AntiChess.mainGame.board[ActivePiece.row][ActivePiece.col] = ' ';

            AntiChess.promotionMove = move;

            ActivePiece.clear();
            return;
        }

        else if( thisPiece == 'p' && rowClicked == 7 ) {

            AntiChess.programState = ProgramState.PROMOTION;

            AntiChess.mainGame.board[rowClicked][colClicked] = thisPiece;
            AntiChess.mainGame.board[ActivePiece.row][ActivePiece.col] = ' ';

            AntiChess.promotionMove = move;

            ActivePiece.clear();
            return;
        }

        if( Util.isMoveInArrayList( legalMoves, move ) )
            AntiChess.mainGame.makeMove( move );

        ActivePiece.clear();
    }

    public static void promotion() {

        final int[] mouse = Util.getMouseCoordinates();
        int colClicked    = mouse[0];
        int rowClicked    = mouse[1];

        if( AntiChess.isFlipped ) {

            colClicked = 7 - colClicked;
            rowClicked = 7 - rowClicked;
        }

        if( colClicked != AntiChess.promotionMove.toCol )
            return;

        if( AntiChess.promotionMove.toRow == 0 ) {

            switch ( rowClicked ) {

                case 0 -> AntiChess.promotionMove.promoteTo = 'K';
                case 1 -> AntiChess.promotionMove.promoteTo = 'Q';
                case 2 -> AntiChess.promotionMove.promoteTo = 'R';
                case 3 -> AntiChess.promotionMove.promoteTo = 'B';
                case 4 -> AntiChess.promotionMove.promoteTo = 'N';
            }

            if( AntiChess.promotionMove.promoteTo == ' ' )
                return;

            System.out.println( AntiChess.promotionMove );

            AntiChess.mainGame.makeMove( AntiChess.promotionMove );

            AntiChess.programState = ProgramState.PLAY;
        }

        else {

            switch ( rowClicked ) {

                case 7 -> AntiChess.promotionMove.promoteTo = 'k';
                case 6 -> AntiChess.promotionMove.promoteTo = 'q';
                case 5 -> AntiChess.promotionMove.promoteTo = 'r';
                case 4 -> AntiChess.promotionMove.promoteTo = 'q';
                case 3 -> AntiChess.promotionMove.promoteTo = 'k';
            }

            if( AntiChess.promotionMove.promoteTo == ' ' )
                return;

            System.out.println( AntiChess.promotionMove );

            AntiChess.mainGame.makeMove( AntiChess.promotionMove );

            AntiChess.programState = ProgramState.PLAY;
        }

    }

    public static void keyPressed() {

        if( IsKeyDown( KEY_LEFT_CONTROL ) && IsKeyPressed( KEY_F ) )
            AntiChess.isFlipped = !AntiChess.isFlipped;
    }
}
