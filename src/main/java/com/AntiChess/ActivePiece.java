package com.AntiChess;

// active piece is the piece that user is dragging and dropping
public class ActivePiece {

    public static Character type  = null;
    public static Integer col     = null;
    public static Integer row     = null;
    public static Character color = null;

    public static void set(Character _type, Integer _col, Integer _row, Character _color) {

        type  = _type;
        col   = _col;
        row   = _row;
        color = _color;
    }

    public static void clear() {

        type  = null;
        col   = null;
        row   = null;
        color = null;
    }

    public static boolean isNull() {

        return type == null || col == null || row == null || color == null;
    }
}
