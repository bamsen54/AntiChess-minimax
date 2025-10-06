package com.RayU;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class RayU {

    private final static byte MAX_RGB_VALUE = (byte) 255;

    public static Color color(int r, int g, int b, int a) {

        Color color = new Color();

        color.r( (byte) r ).g( (byte) g ).b( (byte) b ).a( (byte) a );

        return color;
    }

    public static Color color(int r, int g, int b) {

        return color( r, g, b, MAX_RGB_VALUE );
    }

    public static Color color(int colour, int a) {

        return color( colour, colour, colour, a );
    }

    public static Color color(int colour) {

        return color( colour, colour, colour, MAX_RGB_VALUE );
    }

    public static int[] getMousePosition() {

        Vector2 mouse = GetMousePosition();

        final int x = (int) mouse.x();
        final int y = (int) mouse.y();

        return new int[] {x, y};
    }

    public static void image(Image img, int x, int y, int w, int h) {

        ImageResize( img, w, h );

        Texture texture = LoadTextureFromImage( img );

        DrawTexture( texture, x, y, WHITE );
    }
}
