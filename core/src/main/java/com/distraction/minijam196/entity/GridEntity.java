package com.distraction.minijam196.entity;

import static com.distraction.minijam196.Constants.TILE_SIZE;

abstract class GridEntity extends Entity {

    public int row;
    public int col;

    public void setDest(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean atDestination() {
        return x == col * TILE_SIZE && y == row * TILE_SIZE;
    }

    protected void moveToDestination(float dt) {
        float destx = col * TILE_SIZE;
        float desty = row * TILE_SIZE;
        if (x < destx) {
            x += dx * dt;
            if (x > destx) x = destx;
        }
        if (x > destx) {
            x -= dx * dt;
            if (x < destx) x = destx;
        }
        if (y < desty) {
            y += dy * dt;
            if (y > desty) y = desty;
        }
        if (y > desty) {
            y -= dy * dt;
            if (y < desty) y = desty;
        }
    }

}
