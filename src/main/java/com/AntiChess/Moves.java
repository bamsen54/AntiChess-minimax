package com.AntiChess;

import java.util.ArrayList;

public class Moves {

    public static ArrayList<Move> getPseudoLegalMoves(Game game, int col, int row) {

        final char thisPiece = game.board[row][col];

        if( thisPiece == ' ' )
            return new ArrayList<Move>();

        return switch( Character.toUpperCase( thisPiece ) ) {

            case 'K' -> getKingMoves(   game, col, row );
            case 'P' -> getPawnMoves(   game, col, row );
            case 'Q' -> getQueenMoves(  game, col, row );
            case 'R' -> getRookMoves(   game, col, row );
            case 'B' -> getBishopMoves( game, col, row );
            case 'N' -> getKnightMoves( game, col, row );

            default -> new ArrayList<>();
        };
    }

    public static ArrayList<Move> getLegalMoves(Game game, int col, int row) {

        ArrayList<Move> legalMoves        = new ArrayList<>();
        ArrayList<Move> pseudoLegalMoves = getPseudoLegalMoves( game, col, row );

        boolean canCapture = Util.isCapturePossible( game, game.turn );
        if( !canCapture || !AntiChess.mandatoryCapture )
            return pseudoLegalMoves; // in this case pseudo legal moves are the same as the legal moves

        for( Move move : pseudoLegalMoves ) {

            if( move.capturedPiece != ' ' )
                legalMoves.add( move );
        }

        return legalMoves;
    }

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

            Move move = new Move( thisType, col, row, col, row + moveDirection, ' ', false, ' ' );

            if( ( row + moveDirection ) == 0 || ( row + moveDirection ) == 7 )
                moves.addAll( Util.getMoveListWithAllPromotions( move, colorThisType) );

            else
                moves.add( move );

            canMoveOneStepForward = true;
        }

        if( row == startRank )
            isOnStarRank = true;

        final boolean canDoubleMove = isOnStarRank && canMoveOneStepForward;

        // if on start square, can move forward two steps in moveDirection
        if( canDoubleMove &&  Util.isSquareEmpty( game, col, row + 2 * moveDirection ) )
            moves.add(new Move(thisType, col, row, col, row + 2 * moveDirection, ' ', false, ' '));

        try {

            // capture left
            final boolean leftIsNotEmpty = !Util.isSquareEmpty(game, col - 1, row + moveDirection);
            final boolean leftIsCapture = canMoveToSquare(game, thisType, col, row, col - 1, row + moveDirection);
            final char leftCapturedPiece = game.board[row + moveDirection][col - 1];

            if (leftIsNotEmpty && leftIsCapture) {

                Move move = new Move( thisType, col, row, col - 1, row + moveDirection, leftCapturedPiece, false, ' ' );

                if( ( row + moveDirection ) == 0 || ( row + moveDirection ) == 7 )
                    moves.addAll( Util.getMoveListWithAllPromotions( move, colorThisType) );

                else
                    moves.add( move );
            }

            // capture right
            final boolean rightIsNotEmpty = !Util.isSquareEmpty(game, col + 1, row + moveDirection);
            final boolean rightIsCapture = canMoveToSquare(game, thisType, col, row, col + 1, row + moveDirection);
            final char rightCapturedPiece = game.board[row + moveDirection][col + 1];

            if (rightIsNotEmpty && rightIsCapture) {

                Move move = new Move( thisType, col, row, col + 1, row + moveDirection, rightCapturedPiece, false, ' ' );

                if( ( row + moveDirection ) == 0 || ( row + moveDirection ) == 7 )
                    moves.addAll( Util.getMoveListWithAllPromotions( move, colorThisType) );

                else
                    moves.add( move );
            }
        }

        catch ( ArrayIndexOutOfBoundsException e ) {
            // do not add those moves
        }

        if( game.enPassantSquare.length != 2 )
            return moves;

        if( Character.toUpperCase( thisType) != 'P' )
            return moves;

        final int enPassantCol  = game.enPassantSquare[0];
        final int  enPassantRow = game.enPassantSquare[1];

        final char capturedPiece = thisType == 'P' ? 'p' : 'P'; // pawn but other color

        // essentially if pawn can "capture" en passant square, then it can move to that square
        if( enPassantCol == col - 1 && enPassantRow == row + moveDirection )
            moves.add( new Move( thisType, col, row, col - 1, row + moveDirection, capturedPiece, true, ' ' ) );

        if( enPassantCol == col + 1 && enPassantRow == row + moveDirection )
            moves.add( new Move( thisType, col, row, col + 1, row + moveDirection, capturedPiece, true, ' ' ) );

        return moves;
    }

    public static ArrayList<Move> getQueenMoves(Game game, int col, int row) {

        ArrayList<Move> pseudoLegalMoves = new ArrayList<>();

        pseudoLegalMoves.addAll( getPossibleMovesStraight( game, col, row) );
        pseudoLegalMoves.addAll( getPossibleMovesDiagonal( game, col, row) );

        return pseudoLegalMoves;
    }

    public static ArrayList<Move> getRookMoves(Game game, int col, int row) {

        return getPossibleMovesStraight( game, col, row );
    }

    public static ArrayList<Move> getBishopMoves(Game game, int col, int row) {

        return getPossibleMovesDiagonal( game, col, row );
    }

    public static ArrayList<Move> getKnightMoves(Game game, int col, int row) {

        ArrayList<Move> pseudoLegalMoves = new ArrayList<>();

        final char thisPiece        = game.board[row][col];
        final char colorOfThisPiece = Util.colorOfPiece( thisPiece );

        int[] steps = { - 2, - 1, 1, 2 };

        for( int rowDiff: steps ) {

            for( int colDiff: steps ) {

                if( Math.abs( rowDiff ) == Math.abs( colDiff ) )
                    continue;

                final int newCol = col + colDiff;
                final int newRow = row + rowDiff;

                if( !Util.isOnBoard( newCol, newRow ) )
                    continue;

                final char pieceAt        = game.board[newRow][newCol];
                final char colorOfPieceAt = Util.colorOfPiece(  pieceAt );

                if(  colorOfThisPiece == colorOfPieceAt  )
                    continue;

                pseudoLegalMoves.add( new Move( thisPiece, col, row, newCol, newRow, pieceAt, false, ' ' ) );
            }
        }

        return pseudoLegalMoves;
    }

    // for a piece on (col, row) how many steps can it move in the direction (colDirection, rowDirection)
    // used for queen, rook and bishop.
    public static ArrayList<Move> getPossibleMovesInDirection(Game game, int col, int row, int colDirection, int rowDirection  ) {

        ArrayList<Move> pseudoLegalMoves = new ArrayList<>();

        final char thisPiece        = game.board[row][col];
        final char colorOfThisPiece = Util.colorOfPiece( thisPiece );

        // amount of steps from (col, row) in the direction (colDirection, rowDirection)
        // (newCol, newRow) = step * (colDirection, rowDirection) in mathematical vector notation
        for( int step = 1; step < 8; step++ ) {

            final int newCol = col + step * colDirection;
            final int newRow = row + step * rowDirection;

            if( !Util.isOnBoard( newCol, newRow ) )
                return pseudoLegalMoves;

            final char pieceAt        = game.board[newRow][newCol];
            final char colorOfPieceAt = Util.colorOfPiece( game.board[newRow][newCol] );

            if( pieceAt == ' ' )
                pseudoLegalMoves.add( new Move( thisPiece, col, row, newCol, newRow ) );

            else if( colorOfPieceAt != colorOfThisPiece) {
                pseudoLegalMoves.add( new Move(thisPiece, col, row, newCol, newRow, pieceAt, false, ' '));
                return pseudoLegalMoves;
            }

            else return pseudoLegalMoves;
        }

        return pseudoLegalMoves;
    }

    public static ArrayList<Move> getPossibleMovesStraight(Game game, int col, int row) {

        ArrayList<Move> pseudoLegalMoves = new ArrayList<>();

        pseudoLegalMoves.addAll( getPossibleMovesInDirection( game, col, row,   0,   - 1 ) ); // N
        pseudoLegalMoves.addAll( getPossibleMovesInDirection( game, col, row,   1,     0 ) ); // E
        pseudoLegalMoves.addAll( getPossibleMovesInDirection( game, col, row,   0,     1 ) ); // S;
        pseudoLegalMoves.addAll( getPossibleMovesInDirection( game, col, row, - 1,     0 ) ); // W

        return pseudoLegalMoves;
    }

    public static ArrayList<Move> getPossibleMovesDiagonal(Game game, int col, int row) {

        ArrayList<Move> pseudoLegalMoves = new ArrayList<>();

        pseudoLegalMoves.addAll( getPossibleMovesInDirection( game, col, row,   1,   - 1 ) ); // NE
        pseudoLegalMoves.addAll( getPossibleMovesInDirection( game, col, row,   1,     1 ) ); // SE
        pseudoLegalMoves.addAll( getPossibleMovesInDirection( game, col, row, - 1,     1 ) ); // SW;
        pseudoLegalMoves.addAll( getPossibleMovesInDirection( game, col, row, - 1,   - 1 ) ); // NW

        return pseudoLegalMoves;
    }
}
