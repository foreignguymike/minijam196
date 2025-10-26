package com.distraction.minijam196.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.minijam196.Context;
import com.distraction.minijam196.Utils;

public class SnakeSelect extends Entity {

    private final Context context;
    public Snake.SnakeType type;

    private TextureRegion bg;
    private final TextureRegion snakes[];
    private boolean hover;

    private float scale;

    public SnakeSelect(Context context, Snake.SnakeType type) {
        this.context = context;
        this.type = type;

        snakes = context.getImage(type.name).split(20, 20)[0];

        updateImage();
    }

    public void setHover(boolean hover) {
        this.hover = hover;
        updateImage();
    }

    private void updateImage() {
        if (hover) bg = context.getImage("selecth");
        else bg = context.getImage("select");
        w = bg.getRegionWidth();
        h = bg.getRegionHeight();
    }

    @Override
    public void update(float dt) {
        if (hover) scale += dt;
        else scale -= dt;
        scale = MathUtils.clamp(scale, 1f, 1.1f);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(1, 1, 1, 1);
        Utils.drawCenteredScaled(sb, bg, x, y, scale);
        Utils.drawCentered(sb, snakes[0], x, y + 30);
        Utils.drawCentered(sb, snakes[1], x, y + 10);
        Utils.drawCentered(sb, snakes[1], x, y - 10);
        Utils.drawCentered(sb, snakes[1], x, y - 30);
    }

}
