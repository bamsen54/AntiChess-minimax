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

        if( turn != colorAtMoveSquare )
            return false;

        return true;
    }

    public static ArrayList<Moves> getKingMoves(Game game, int col, int row) {

        ArrayList<Moves> moves = new ArrayList<>();

        return moves;
    }
}
