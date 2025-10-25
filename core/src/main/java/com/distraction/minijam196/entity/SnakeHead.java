package com.distraction.minijam196.entity;

import com.distraction.minijam196.Context;

public class SnakeHead extends SnakeEntity {

    public SnakeHead(Context context, int row, int col, Direction direction) {
        super(context, row, col);
        this.direction = direction;
        image = context.getImage("greensnakehead");
    }
}
