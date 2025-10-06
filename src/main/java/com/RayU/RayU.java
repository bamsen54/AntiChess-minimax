package com.RayU;

import static com.raylib.Raylib.*;

public class RayU {

    public static int[] getMousePosition() {

        Vector2 mouse = GetMousePosition();

        final int x = (int) mouse.x();
        final int y = (int) mouse.y();

        return new int[] {x, y};
    }
}
