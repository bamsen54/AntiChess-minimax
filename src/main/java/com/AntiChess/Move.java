package com.AntiChess;


// keeps track of a move
// whereas Moves.java makes moves and checks which moves are legal
public class Move {

    public char pieceMoved;

    public int fromCol;
    public int fromRow;
    public int toCol;
    public int toRow;

    public char capturedPiece;
    public boolean enPassant;
    public char promoteTo;

    public Move(char pieceMoved, int from_col, int from_row, int to_col, int to_row) {

        this.pieceMoved = pieceMoved;

        this.fromCol = from_col;
        this.fromRow = from_row;
        this.toCol   = to_col;
        this.toRow   = to_row;

        this.capturedPiece  = ' ';
        this.promoteTo      = ' ';
        this.enPassant      = false;
    }

    public Move(char pieceMoved, int fromCol, int fromRow, int toCol, int toRow, char capturedPiece, boolean enPassant, char promoteTo) {

        this.pieceMoved    = pieceMoved;
        this.fromCol       = fromCol;
        this.fromRow       = fromRow;
        this.toCol         = toCol;
        this.toRow         = toRow;

        this.capturedPiece = capturedPiece;
        this.enPassant     = enPassant;
        this.promoteTo     = promoteTo;
    }

    @Override
    public boolean equals(Object object) {

        if ( object == null || this.getClass() != object.getClass() )
            return false;

        Move move = (Move) object;
        return
            pieceMoved     == move.pieceMoved     &&
            fromCol        == move.fromCol        &&
            fromRow        == move.fromRow        &&
            toCol          == move.toCol          &&
            toRow          == move.toRow          &&
            capturedPiece  == move.capturedPiece &&
            enPassant      == move.enPassant      &&
            promoteTo      == move.promoteTo;
    }

    public String toString() {

        StringBuilder builder = new StringBuilder();

        builder.append (pieceMoved ).append(": ");

        builder.append( "(" ).append( this.fromCol ).append( ", " ).append( this.fromRow ).append( ") ⟶ " );
        builder.append( "(" ).append( this.toCol   ).append( ", " ).append( this.toRow ).append( ") " );

        if( this.capturedPiece != ' ' )
            builder.append("capture ");

        if( this.enPassant )
            builder.append("en passant ");

        if( this.promoteTo != ' ' )
            builder.append("promote: ").append(this.promoteTo).append(" ");

        return builder.toString();
    }
}
