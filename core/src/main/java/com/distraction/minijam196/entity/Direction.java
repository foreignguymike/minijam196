package com.distraction.minijam196.entity;

import com.badlogic.gdx.math.MathUtils;

public enum Direction {
    UP(0),
    DOWN(MathUtils.PI),
    LEFT(MathUtils.PI / 2),
    RIGHT(-MathUtils.PI / 2);

    public final float rad;

    Direction(float rad) {
        this.rad = rad;
    }
}
