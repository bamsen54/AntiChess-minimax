package com.AntiChess;

import java.util.HashMap;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

import com.rayu.RayU;

public class Gui {

    public static int boardPositionX;
    public static int boardPositionY;
    public static int squareSize;

    public static Color lightSquareColor = RayU.color( 241, 242, 249 );
    public static Color darkSquareColor  = RayU.color( 53,   53,  64 );

    public static int marinAll;

    public static HashMap<Character, Texture> icons = new HashMap<Character, Texture>();

    public static void init() {


    }
}
