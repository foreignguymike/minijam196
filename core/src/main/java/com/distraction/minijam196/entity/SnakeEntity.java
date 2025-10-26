package com.distraction.minijam196.entity;

import static com.distraction.minijam196.Constants.TILE_SIZE;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.minijam196.Utils;

public class SnakeEntity extends GridEntity {

    private static final float SPEED = 150;

    protected TextureRegion image;

    protected Direction direction;

    public SnakeEntity(TextureRegion image, int row, int col) {
        this.row = row;
        this.col = col;
        x = col * TILE_SIZE;
        y = row * TILE_SIZE;
        dx = SPEED;
        dy = SPEED;
        direction = Direction.RIGHT;

        this.image = image;
    }

    public SnakeEntity(TextureRegion image, int row, int col, Direction direction) {
        this.row = row;
        this.col = col;
        x = col * TILE_SIZE;
        y = row * TILE_SIZE;
        dx = SPEED;
        dy = SPEED;
        this.direction = direction;

        this.image = image;
    }

    public void setDest(int row, int col, Direction direction) {
        this.row = row;
        this.col = col;
        this.direction = direction;
    }

    @Override
    public void update(float dt) {
        moveToDestination(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(1, 1, 1, 1);
        Utils.drawRotated(sb, image, x + TILE_SIZE / 2f, y + TILE_SIZE / 2f, direction.rad);
    }
}
