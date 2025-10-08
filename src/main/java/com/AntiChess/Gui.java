package com.AntiChess;

import java.util.HashMap;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

import com.rayu.RayU;

import com.rayu.RayU;

public class Gui {

    public static int boardPositionX;
    public static int boardPositionY;
    public static int squareSize;
    public static int marginAll;

    public static int screenWidth;
    public static int screenHeight;

    public static Color lightSquareColor = RayU.color( 241, 242, 249 );
    public static Color darkSquareColor  = RayU.color( 53,   53,  64 );

    public static HashMap<Character, Texture> pieceIcons = new HashMap<Character, Texture>();

    public static void init() {

        screenWidth  = GetScreenWidth();
        screenHeight = GetScreenHeight();

        final int smallestDimension = Math.min( screenWidth, screenHeight );

        marginAll = Math.max( (int) ( 0.025 * GetScreenWidth() ), (int) ( 0.025 * GetScreenHeight() ) ); // 2.5% of width

        squareSize     = (int) ( ( smallestDimension - 2 * marginAll ) / 8.0);
        boardPositionY = marginAll;
        boardPositionX = screenWidth / 2 - 4 * squareSize;

        loadTextures();
    }

    public static void loadTextures() {

        final int s = squareSize;

        pieceIcons.put( 'K', RayU.loadTexture( "icons/white-king.png",   s, s ) );
        pieceIcons.put( 'k', RayU.loadTexture( "icons/black-king.png",   s, s ) );
        pieceIcons.put( 'Q', RayU.loadTexture( "icons/white-queen.png",  s, s ) );
        pieceIcons.put( 'q', RayU.loadTexture( "icons/black-queen.png",  s, s ) );
        pieceIcons.put( 'R', RayU.loadTexture( "icons/white-rook.png",   s, s ) );
        pieceIcons.put( 'r', RayU.loadTexture( "icons/black-rook.png",   s, s ) );
        pieceIcons.put( 'B', RayU.loadTexture( "icons/white-bishop.png", s, s ) );
        pieceIcons.put( 'b', RayU.loadTexture( "icons/black-bishop.png", s, s ) );
        pieceIcons.put( 'N', RayU.loadTexture( "icons/white-knight.png", s, s ) );
        pieceIcons.put( 'n', RayU.loadTexture( "icons/black-knight.png", s, s ) );
        pieceIcons.put( 'P', RayU.loadTexture( "icons/white-pawn.png",   s, s ) );
        pieceIcons.put( 'p', RayU.loadTexture( "icons/black-pawn.png",   s, s ) );
    }

    public static void drawBoard() {

        for( int row = 0; row < 8; row++ ) {

            for( int col = 0; col < 8; col++ ) {

                int x = col;
                int y = row;

                if (AntiChess.isFlipped) {

                    x = 7 - col;
                    y = 7 - row;
                }

                x = boardPositionX + x * squareSize;
                y = boardPositionY + y * squareSize;

                if ((col + row) % 2 == 0)
                    DrawRectangle(x, y, squareSize, squareSize, lightSquareColor);

                else
                    DrawRectangle(x, y, squareSize, squareSize, darkSquareColor);
            }
        }
    }

    public static void displayPieces(Game game) {

        for( int row = 0; row < 8; row++ ) {

            for( int col = 0; col < 8; col++ ) {

                int x = col;
                int y = row;

                if (AntiChess.isFlipped) {

                    x = 7 - col;
                    y = 7 - row;
                }

                x = boardPositionX + x * squareSize;
                y = boardPositionY + y * squareSize;

                char type = game.board[row][col];

                if( type == ' ' )
                    continue;

                if( !ActivePiece.isNull() ) {

                    if (row == ActivePiece.row && col == ActivePiece.col)
                        continue;
                }

                Texture pieceTexture = pieceIcons.get( type );

                DrawTexture( pieceTexture, x, y, WHITE );
            }
        }
    }

    public static void displayActivePiece() {

        if( ActivePiece.isNull() )
            return;

        final int x = (int) GetMousePosition().x() - squareSize / 2;
        final int y = (int) GetMousePosition().y() - squareSize / 2;

        final char type      = ActivePiece.type;
        Texture pieceTexture = pieceIcons.get( type );

        DrawTexture( pieceTexture, x, y, WHITE );
    }
}
