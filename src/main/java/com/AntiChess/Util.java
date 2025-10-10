package com.AntiChess;

import java.util.ArrayList;

import static com.raylib.Raylib.*;

public class Util {

    public static float mapInterval( float x, float a, float b, float alpha, float beta ) {

        return alpha + ( beta - alpha ) * ( x - a ) / ( b - a );
    }

    public static boolean isOnBoard( int col, int row ) {

        return col >= 0 && col < 8 && row >= 0  && row < 8;
    }

    public static char colorOfPiece(char piece) {

        if( piece == ' ' )
            return ' ';

        if( Character.isUpperCase( piece ) )
            return 'w';

        else if( Character.isLowerCase( piece ) )
            return 'b';

        return ' ';
    }

    public static boolean isMoveInArrayList( ArrayList<Move> moves, Move move ) {

        for( Move m : moves ) {

            if( m.equals( move ) )
                return true;
        }

        return false;
    }

    public static boolean isSquareEmpty(Game game, int col, int row) {

        if( !Util.isOnBoard( col, row ) )
            return false;

        return game.board[row][col] == ' ';
    }

    public static int[] getMouseCoordinates() {

        final int x0 = Gui.boardPositionX;
        final int y0 = Gui.boardPositionY;
        final int bw = 8 * Gui.squareSize;

        Vector2 mouse        = GetMousePosition();
        final int colClicked = (int ) Math.floor( Util.mapInterval( mouse.x(), x0, x0 + bw, 0, 8 ) );
        final int rowClicked = (int ) Math.floor( Util.mapInterval( mouse.y(), y0, y0 + bw, 0, 8 ) );

        return new int[] {colClicked, rowClicked};
    }

    public static ArrayList<Move> getMoveListWithAllPromotions(Move move, char color) {

        Move promoteToKing   = move.getCopy();
        Move promoteToQueen  = move.getCopy();
        Move promoteToRook   = move.getCopy();
        Move promoteToBishop = move.getCopy();
        Move promoteToKnight = move.getCopy();

        if( color == 'w' ) {

            promoteToKing.promoteTo   = 'K';
            promoteToQueen.promoteTo  = 'Q';
            promoteToRook.promoteTo   = 'R';
            promoteToBishop.promoteTo = 'B';
            promoteToKnight.promoteTo = 'N';
        }

        else {

            promoteToKing.promoteTo   = 'k';
            promoteToQueen.promoteTo  = 'q';
            promoteToRook.promoteTo   = 'r';
            promoteToBishop.promoteTo = 'b';
            promoteToKnight.promoteTo = 'n';
        }

        ArrayList<Move> all_promotions = new ArrayList<>();

        all_promotions.add( promoteToKing );
        all_promotions.add( promoteToQueen );
        all_promotions.add( promoteToRook );
        all_promotions.add( promoteToBishop );
        all_promotions.add( promoteToKnight );

        return all_promotions;
    }
}
