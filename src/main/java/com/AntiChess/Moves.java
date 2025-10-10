package com.AntiChess;

import java.util.ArrayList;

public class Moves {

    // only checks wather toSquare is empty or have a piece of opposite color. It does not enforce capture
    // also assumes that there is a piece on square fromCol, fromCol
    public static boolean canMoveToSquare(Game game, char type, int fromCol, int fromRow, int toCol, int toRow) {

        if( !Util.isOnBoard( toCol, toRow ) )
            return false;

        final char typeAtMoveSquare = game.board[toRow][toCol];

        if( typeAtMoveSquare == ' ' )
            return true;

        final char colorAtMoveSquare = Util.colorOfPiece( typeAtMoveSquare );

        return Util.colorOfPiece(type) != colorAtMoveSquare;
    }

    public static ArrayList<Move> getKingMoves(Game game, int col, int row) {

        ArrayList<Move> moves = new ArrayList<>();

        final char thisType = game.board[row][col];

        int[][] offsets = { {- 1, - 1}, {- 1, 0}, {- 1, 1}, {0, - 1}, {0, 1}, {1, - 1}, {1, 0}, {1, 1} };

        for( int[] offset : offsets ) {

            final int newCol = col + offset[0];
            final int newRow = row + offset[1];

            if( !Util.isOnBoard( newCol, newRow ) )
                continue;

            final char typeAtMoveSquare = game.board[newRow][newCol];

            if( canMoveToSquare( game, thisType, col, row, newCol, newRow ) )
                moves.add( new Move( thisType, col, row, newCol, newRow, typeAtMoveSquare,  false, ' '  ) );
        }

        return moves;
    }

    public static ArrayList<Move> getPawnMoves(Game game, int col, int row) {

        ArrayList<Move> moves = new ArrayList<>();

        final char thisType      = game.board[row][col];
        final char colorThisType = Util.colorOfPiece( thisType );

        final int moveDirection       = colorThisType == 'b' ? 1 : - 1;
        final int startRank           = colorThisType == 'b' ? 1 : 6;
        boolean canMoveOneStepForward = false;
        boolean isOnStarRank          = false;

        // normal move, one step forward in moveDirection
        if( Util.isSquareEmpty( game, col, row + moveDirection )) {

            moves.add(new Move(thisType, col, row, col, row + moveDirection, ' ', false, ' '));

            canMoveOneStepForward = true;
        }

        if( row == startRank )
            isOnStarRank = true;

        final boolean canDoubleMove = isOnStarRank && canMoveOneStepForward;

        // if on start square, can move forward two steps in moveDirection
        if( canDoubleMove &&  Util.isSquareEmpty( game, col, row + 2 * moveDirection ) )
            moves.add(new Move(thisType, col, row, col, row + 2 * moveDirection, ' ', false, ' '));

        return moves;
    }
}
