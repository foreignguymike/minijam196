package com.distraction.minijam196.entity;

abstract class GridEntity extends Entity {
    public int row;
    public int col;
    public void setDest(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
