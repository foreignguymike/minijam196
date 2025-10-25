package com.distraction.minijam196.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.minijam196.Context;
import com.distraction.minijam196.Utils;

public class Bomb extends GridEntity {

    public enum BombType {
        BOMB("bomb", 3),
        CROSS("crossbomb", 3),
        DCROSS("dcrossbomb", 3);

        public String name;
        public int delay;
        BombType(String name, int delay) {
            this.name = name;
            this.delay = delay;
        }

    }

    private final TextureRegion image;

    public final BombType type;
    private int countdown;

    public Bomb(Context context, BombType type, int startRow, int startCol) {
        this.type = type;
        this.countdown = type.delay;

        image = context.getImage(type.name);
    }

    public boolean countdown() {
        countdown--;
        return countdown == 0;
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(1, 1, 1, 1);
        Utils.drawCentered(sb, image, x, y);
    }
}
