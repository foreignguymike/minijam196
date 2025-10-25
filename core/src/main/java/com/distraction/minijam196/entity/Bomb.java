package com.distraction.minijam196.entity;

import static com.distraction.minijam196.Constants.TILE_SIZE;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.minijam196.Context;

public class Bomb extends GridEntity {

    public enum BombType {
        BOMB("bomb", 4),
        CROSS("crossbomb", 4),
        DCROSS("dcrossbomb", 4);

        public String name;
        public int delay;

        BombType(String name, int delay) {
            this.name = name;
            this.delay = delay;
        }

    }

    private static final float SPEED = 200;

    private final TextureRegion image;

    public final BombType type;
    public int countdown;

    private final int startRow;
    private final int startCol;

    private final TextEntity countdownText;

    public Bomb(Context context, BombType type, int startRow, int startCol) {
        this.type = type;
        this.countdown = type.delay;
        this.startRow = startRow;
        this.startCol = startCol;
        x = startCol * TILE_SIZE;
        y = startRow * TILE_SIZE;

        image = context.getImage(type.name);
        countdownText = new TextEntity(context.getFont(Context.M5X716), "" + countdown, x, y, TextEntity.Alignment.CENTER);
    }

    @Override
    public void setDest(int row, int col) {
        super.setDest(row, col);
        dx = col * TILE_SIZE - x;
        dy = row * TILE_SIZE - y;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);
        dx *= SPEED / dist;
        dy *= SPEED / dist;
        dx = Math.abs(dx);
        dy = Math.abs(dy);
    }

    public void countdown() {
        countdown--;
        countdownText.setText("" + countdown);
    }

    @Override
    public void update(float dt) {
        moveToDestination(dt);
        countdownText.x = x + 9;
        countdownText.y = y + 9;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(1, 1, 1, 1);
        sb.draw(image, x, y);
        countdownText.render(sb);
    }
}
