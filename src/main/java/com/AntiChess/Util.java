package com.AntiChess;

public class Util {

    public static float mapInterval( float x, float a, float b, float alpha, float beta ) {

        return alpha + ( beta - alpha ) * ( x - a ) / ( b - a );
    }

    public static boolean isOnBoard( int col, int row ) {

        return col >= 0 && col < 8 && row >= 0  && row < 8;
    }

    public static char colorOfPiece(char piece) {

        if( piece == ' ' )
            return 'x';

        if( Character.isUpperCase( piece ) )
            return 'w';

        else if( Character.isLowerCase( piece ) )
            return 'b';
    }
}
