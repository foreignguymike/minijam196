package com.distraction.minijam196;

import com.badlogic.gdx.graphics.Color;

public class Constants {

    public static final String TITLE = "unknown";
    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;
    public static final int SCALE = 2;
    public static final int SWIDTH = WIDTH * SCALE;
    public static final int SHEIGHT = HEIGHT * SCALE;

    public static final int NUM_ROWS = 18;
    public static final int NUM_COLS = 24;
    public static final int TILE_SIZE = 20;

    public static final boolean FULLSCREEN = false;

    // Endesga 32 https://lospec.com/palette-list/endesga-32
    public static final Color[] COLORS = new Color[]{
        Color.valueOf("be4a2f"), // 0
        Color.valueOf("d77643"), // 1
        Color.valueOf("ead4aa"), // 2
        Color.valueOf("e4a672"), // 3
        Color.valueOf("b86f50"), // 4
        Color.valueOf("733e39"), // 5
        Color.valueOf("3e2731"), // 6
        Color.valueOf("a22633"), // 7
        Color.valueOf("e43b44"), // 8
        Color.valueOf("f77622"), // 9
        Color.valueOf("feae34"), // 10
        Color.valueOf("fee761"), // 11
        Color.valueOf("63c74d"), // 12
        Color.valueOf("3e8948"), // 13
        Color.valueOf("265c42"), // 14
        Color.valueOf("193c3e"), // 15
        Color.valueOf("124e89"), // 16
        Color.valueOf("0099db"), // 17
        Color.valueOf("2ce8f5"), // 18
        Color.valueOf("ffffff"), // 19
        Color.valueOf("c0cbdc"), // 20
        Color.valueOf("8b9bb4"), // 21
        Color.valueOf("5a6988"), // 22
        Color.valueOf("3a4466"), // 23
        Color.valueOf("262b44"), // 24
        Color.valueOf("181425"), // 25
        Color.valueOf("ff0044"), // 26
        Color.valueOf("68386c"), // 27
        Color.valueOf("b55088"), // 28
        Color.valueOf("f6757a"), // 29
        Color.valueOf("e8b796"), // 30
        Color.valueOf("c28569"), // 31
    };

    public static final Color DARK_GREEN = COLORS[14];
    public static final Color VERY_DARK_GREEN = COLORS[15];

    public static final Color GRAY = COLORS[22];
    public static final Color DARK_GRAY = COLORS[23];
    public static final Color VERY_DARK_GRAY = COLORS[24];
    public static final Color WHITE = COLORS[19];

    public static final Color DARK_BLUE = COLORS[16];

    public static final Color MENU_STRIPE_1 = COLORS[24];
    public static final Color MENU_STRIPE_2 = COLORS[23];

    public static final Color BLACK = new Color(0, 0, 0, 1);

}
