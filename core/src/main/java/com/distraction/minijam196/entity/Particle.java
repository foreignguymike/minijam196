package com.distraction.minijam196.entity;

import static com.distraction.minijam196.Constants.TILE_SIZE;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.minijam196.Animation;
import com.distraction.minijam196.Utils;

public class Particle extends GridEntity {

    private final Animation animation;
    public boolean remove;

    public Particle(TextureRegion[] images) {
        animation = new Animation(images, 0.016f);
        w = images[0].getRegionWidth();
        h = images[0].getRegionHeight();
    }

    @Override
    public void update(float dt) {
        animation.update(dt);
        if (animation.getFinishCount() > 0) remove = true;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(1, 1, 1, 1);
        Utils.drawCentered(sb, animation.getImage(), x + TILE_SIZE / 2f, y + TILE_SIZE / 2f);
    }
}
