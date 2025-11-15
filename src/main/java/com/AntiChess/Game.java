package com.AntiChess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {


    public char[][] board = { {'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r'},
                              {'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'},
                              {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                              {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                              {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                              {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '},
                              {'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
                              {'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'} };

    char turn    = 'w';

    // if en passant is possible, this is the en passant square.
    // en passant square is the square that pawn "captures" but there is no piece there
    public int[] enPassantSquare = {};

    public int halfMoveClock  = 0;
    public int fullMoveNumber = 1;

    public List<Integer> halfMoveClockHistory;
    public List<int[]> enPassantSquareHistory = new ArrayList<>();

    // for five-fold repetition stores fen
    public ArrayList<String> history = new ArrayList<>();

    public Game() {

        halfMoveClockHistory = new ArrayList<>();
        halfMoveClockHistory.add( 0 );
    }

    public void makeMove(Move move) {

        final int fromCol    = move.fromCol;
        final int fromRow    = move.fromRow;
        final int toCol      = move.toCol;
        final int toRow      = move.toRow;

        // en pasant capture
        if( move.pieceMoved == 'P' && move.capturedPiece == 'p' && this.board[toRow][toCol] == ' ') {

            this.board[toRow + 1][toCol] = ' ';
        }

        if( move.pieceMoved == 'p' && move.capturedPiece == 'P' && this.board[toRow][toCol] == ' ') {

            this.board[toRow - 1][toCol] = ' ';
        }

        this.board[fromRow][fromCol] = ' ';
        this.board[toRow][toCol]     = move.pieceMoved;

        if( move.promoteTo != ' ' )
            this.board[toRow][toCol] = move.promoteTo;

        // if a pawn moves, check if pawn moved two squares, did this create a en passant square
        this.enPassantSquare = new int[] {}; // when player does not do en passant it is removed
        if( move.pieceMoved == 'P' ) {

            try {
                if (Util.isOnBoard(toCol, toRow + 1)) {

                    if (Math.abs(toRow - fromRow) == 2 && this.board[toRow][toCol - 1] == 'p')
                        this.enPassantSquare = new int[]{toCol, toRow + 1};

                    if (Math.abs(toRow - fromRow) == 2 && this.board[toRow][toCol + 1] == 'p')
                        this.enPassantSquare = new int[]{toCol, toRow + 1};
                }
            }

            catch ( RuntimeException e ) {
                IO.println("something went wrong");
            }
        }

        else if( move.pieceMoved == 'p' ) {

            if( Util.isOnBoard( toCol, toRow - 1 ) ) {

                if (Math.abs(toRow - fromRow) == 2 && this.board[toRow][toCol - 1] == 'P')
                    this.enPassantSquare = new int[]{toCol, toRow - 1};

                if (Math.abs(toRow - fromRow) == 2 && this.board[toRow][toCol + 1] == 'P')
                    this.enPassantSquare = new int[]{toCol, toRow - 1};
            }
        }

        int newHalfMoveNumber = 0;

        // counter goes to 0
        if( move.pieceMoved == 'p' || move.pieceMoved == 'P' || move.capturedPiece != ' ' ) {
            newHalfMoveNumber = 0;
        }

        else
            newHalfMoveNumber = this.halfMoveClock + 1;

        IO.println( newHalfMoveNumber );
        this.halfMoveClockHistory.add( newHalfMoveNumber );
        this.halfMoveClock = newHalfMoveNumber;

        if( enPassantSquare.length == 2 )
            enPassantSquareHistory.add( enPassantSquare );

        else
            enPassantSquareHistory .add( new int[] {} );

        this.switchTurn();
    }

    public void undoMove(Move move) {

        try {

            final int fromCol = move.fromCol;
            final int fromRow = move.fromRow;
            final int toCol = move.toCol;
            final int toRow = move.toRow;

            this.board[toRow][toCol] = ' ';
            this.board[fromRow][fromCol] = move.pieceMoved;

            if (this.halfMoveClockHistory.size() > 0)
                this.halfMoveClock = this.halfMoveClockHistory.remove(halfMoveClockHistory.size() - 2);

            else
                this.halfMoveClock = 0;

            if (this.enPassantSquareHistory.get(this.enPassantSquareHistory.size() - 2).length == 2)
                this.enPassantSquare = this.enPassantSquareHistory.get(this.enPassantSquareHistory.size() - 2);

            else
                this.enPassantSquare = new int[]{};

            this.enPassantSquareHistory.removeLast();

            if( move.enPassant ) {

                if( Util.colorOfPiece( move.pieceMoved ) == 'w' )
                    this.board[toRow + 1][toCol] = 'p';

                else
                    this.board[toRow - 1][toCol] = 'P';
            }

            this.switchTurn();
        }

        catch ( RuntimeException e ) {}
    }

    private void switchTurn() {

        if( this.turn == 'w' )
            this.turn = 'b';

        else
            this.turn = 'w';
    }

    public String toString() {

        StringBuilder builder = new StringBuilder();

        builder.append("\n");
        builder.append("Turn: ").append(this.turn).append("\n");

        for( int row = 0; row < 8; row++ ) {

            for( int col = 0; col < 8; col++ ) {

                if( this.board[row][col] != ' ')
                    builder.append(this.board[row][col]);

                else
                    builder.append("+");
            }

            builder.append("\n");
        }

        return builder.toString();
    }
}
