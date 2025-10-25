package com.distraction.minijam196.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.minijam196.Context;
import com.distraction.minijam196.Utils;

public class SnakeEntity extends GridEntity {

    protected TextureRegion image;

    protected Direction direction;

    public SnakeEntity(Context context, int row, int col) {
        this.row = row;
        this.col = col;
        x = col * 20;
        y = row * 20;
        dx = 300;
        dy = 300;
        direction = Direction.RIGHT;

        image = context.getImage("greensnakebody");
    }

    public void setDest(int row, int col, Direction direction) {
        this.row = row;
        this.col = col;
        this.direction = direction;
    }

    @Override
    public void update(float dt) {
        float destx = col * 20;
        float desty = row * 20;
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

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        Utils.drawRotated(sb, image, x + 10, y + 10, direction.rad);
    }
}
