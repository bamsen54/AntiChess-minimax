package com.AntiChess;


// keeps track of a move
// whereas Moves.java makes moves and checks which moves are legal
public class Move {

    public char pieceMoved;

    public int fromCol;
    public int fromRow;
    public int toCol;
    public int toRow;

    public char captureedPiece;
    public boolean enPassant;
    public char promoteTo;

    // only used in minmax

    // if a move will result in capture for the enemy
    public boolean will_lead_to_capture = false;

    Move(char pieceMoved, int from_col, int from_row, int to_col, int to_row) {

        this.pieceMoved = pieceMoved;

        this.fromCol = from_col;
        this.fromRow = from_row;
        this.toCol   = to_col;
        this.toRow   = to_row;

        this.captureedPiece = ' ';
        this.enPassant      = false;
        this.promoteTo      = ' ';
    }

    public String toString() {

        StringBuilder tostring = new StringBuilder();

        tostring.append( "(" ).append( this.fromCol ).append( ", " ).append( this.fromRow ).append( ") -> " );
        tostring.append( "(" ).append( this.toCol   ).append( ", " ).append( this.toRow ).append( ") " );

        if( this.captureedPiece != ' ' )
            tostring.append("capture ");

        if( this.enPassant )
            tostring.append("en passant ");

        if( this.promoteTo != ' ' )
            tostring.append("promote: ").append(this.promoteTo).append(" ");

        return tostring.toString();
    }
}
