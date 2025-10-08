package com.AntiChess;

import java.util.ArrayList;

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

    // for five-fold repetition stores fen
    public ArrayList<String> history = new ArrayList<>();

    public void makeMove(Move move) {

        final int fromCol    = move.fromCol;
        final int fromRow    = move.fromRow;
        final int toCol      = move.toCol;
        final int toRow      = move.toRow;
        final char thisPiece = board[fromRow][fromCol];

        this.board[fromRow][fromCol] = ' ';
        this.board[toRow][toCol]     = thisPiece;

        this.switchTurn();
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
