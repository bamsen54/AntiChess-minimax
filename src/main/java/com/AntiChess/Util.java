package com.AntiChess;

public class Util {

    public static float mapInterval( float x, float a, float b, float alpha, float beta ) {

        return alpha + ( beta - alpha ) * ( x - a ) / ( b - a );
    }
}
