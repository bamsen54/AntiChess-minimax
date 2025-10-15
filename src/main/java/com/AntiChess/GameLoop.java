package com.AntiChess;

import static com.raylib.Colors.GREEN;
import static com.raylib.Colors.YELLOW;
import static com.raylib.Raylib.*;

import java.util.ArrayList;

public class GameLoop {

    public static void update() {

        if( IsMouseButtonPressed( 0 ) && ActivePiece.isNull() )
            pickUpPiece();

        else if( IsMouseButtonReleased(0) && !ActivePiece.isNull() )
            dropPiece();

        if( IsMouseButtonPressed( 0 ) && AntiChess.programState == ProgramState.PROMOTION )
            promotionChoice();

        keyPressed();


    }

    public static void draw() {

        Gui.drawBoard();
        Gui.displayEnPassantSquare( AntiChess.mainGame );
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

        ArrayList<Move> legalMoves = Moves.getPseudoLegalMoves( AntiChess.mainGame, ActivePiece.col, ActivePiece.row );

        Move move = new Move(ActivePiece.type, ActivePiece.col, ActivePiece.row, colClicked, rowClicked);
        handlePawnPromotion( thisPiece, colClicked, rowClicked, move ) ;
        handleExtra( ActivePiece.type, ActivePiece.col, ActivePiece.row, colClicked, rowClicked, move ) ;

        for( Move m: legalMoves )
            System.out.println(m);

        if( Util.isMoveInArrayList( legalMoves, move ) )
            AntiChess.mainGame.makeMove( move );

        handlePawnPromotion( thisPiece, colClicked, rowClicked, move ) ;
        ActivePiece.clear();
    }

    private static void handleExtra(char type, int col, int row, int colClicked, int rowClicked, Move move) {

        if( AntiChess.mainGame.board[rowClicked][colClicked] != ' ' )
            move.capturedPiece =  AntiChess.mainGame.board[rowClicked][colClicked];

        // if a pawn moves diagonally but doesn't capture, it is en passant
        if( ( Character.toUpperCase( type ) == 'P' ) && ( col != colClicked ) && ( move.capturedPiece == ' ' ) ) {

            move.enPassant     = true;
            move.capturedPiece = type == 'P' ? 'p' : 'P';

        }
    }

    public static void handlePawnPromotion(char thisPiece, int colClicked, int rowClicked, Move move) {

        if( ( rowClicked == 0 && thisPiece == 'P') || ( rowClicked == 7 && thisPiece == 'p' ) ) {

            AntiChess.programState = ProgramState.PROMOTION;

            AntiChess.mainGame.board[rowClicked][colClicked]           = thisPiece;
            AntiChess.mainGame.board[ActivePiece.row][ActivePiece.col] = ' ';

            AntiChess.promotionMove = move;
        }
    }

    public static void promotionChoice() {

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
                case 4 -> AntiChess.promotionMove.promoteTo = 'b';
                case 3 -> AntiChess.promotionMove.promoteTo = 'n';
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
