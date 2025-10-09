package com.AntiChess;

import java.util.ArrayList;

public class Moves {

    // only checks weather toSquare is empty or have a piece of opposite color. It does not enforce capture
    // also assumes that there is a piece on square fromCol, fromCol
    public static boolean canMoveToSquare(Game game, char type, int fromCol, int fromRow, int toCol, int toRow) {

        if( !Util.isOnBoard( toCol, toRow ) )
            return false;

        final char typeAtMoveSquare = game.board[toRow][toCol];

        if( typeAtMoveSquare == ' ' )
            return true;

        final char turn              = game.turn;
        final char colorAtMoveSquare = Util.colorOfPiece( typeAtMoveSquare );

        if( turn == colorAtMoveSquare )
            return false;

        return true;
    }

    public static ArrayList<Move> getKingMoves(Game game, int col, int row) {

        ArrayList<Move> moves = new ArrayList<>();

        final char thisType = game.board[row][col];

        int[][] offsets = { {- 1, - 1}, {- 1, 0}, {- 1, 1}, {0, - 1}, {0, 1}, {1, - 1}, {1, 0}, {1, 1} };

        for( int[] offset : offsets ) {

            final int newCol = col + offset[0];
            final int newRow = row + offset[1];

            final char typeAtMoveSquare = game.board[newRow][newCol];

            if( canMoveToSquare( game,  typeAtMoveSquare, col, row, newCol, newRow ) )
                moves.add( new Move( thisType, col, row, newCol, newRow, typeAtMoveSquare,  false, ' '  ) );
        }

        return moves;
    }
}
