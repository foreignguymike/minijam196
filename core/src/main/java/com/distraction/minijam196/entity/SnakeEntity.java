package com.distraction.minijam196.entity;

import static com.distraction.minijam196.Constants.TILE_SIZE;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.minijam196.Context;
import com.distraction.minijam196.Utils;

public class SnakeEntity extends GridEntity {

    private static final float SPEED = 300;

    protected TextureRegion image;

    protected Direction direction;

    public SnakeEntity(Context context, int row, int col) {
        this.row = row;
        this.col = col;
        x = col * TILE_SIZE;
        y = row * TILE_SIZE;
        dx = SPEED;
        dy = SPEED;
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

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(1, 1, 1, 1);
        Utils.drawRotated(sb, image, x + TILE_SIZE / 2f, y + TILE_SIZE / 2f, direction.rad);
    }
}
