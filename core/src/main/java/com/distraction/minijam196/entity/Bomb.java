package com.distraction.minijam196.entity;

import static com.distraction.minijam196.Constants.TILE_SIZE;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.minijam196.Animation;
import com.distraction.minijam196.Context;
import com.distraction.minijam196.Utils;

public class Bomb extends GridEntity {

    public enum BombType {
        BOMB("bomb", 3);

        public String name;
        public int range;

        BombType(String name, int range) {
            this.name = name;
            this.range = range;
        }
    }

    private final Context context;
    private final Animation animation;

    public final BombType type;
    public int countdown;

    private float totalDist;
    private float jumpy;

    private final TextEntity countdownText;

    private float scale;

    public Bomb(Context context, BombType type, int delay, int startRow, int startCol) {
        this.context = context;
        this.type = type;
        this.countdown = delay;
        x = startCol * TILE_SIZE;
        y = startRow * TILE_SIZE;

        animation = new Animation(context.getImage("bomb").split(20, 25)[0], 0.033f);
        w = animation.getImage().getRegionWidth();
        h = animation.getImage().getRegionHeight();

        countdownText = new TextEntity(context.getFont(Context.M5X716), "" + countdown, x, y, TextEntity.Alignment.CENTER);
    }

    @Override
    public void setDest(int row, int col) {
        super.setDest(row, col);
        dx = col * TILE_SIZE - x;
        dy = row * TILE_SIZE - y;
        totalDist = calculateDistance(x, y, col * TILE_SIZE, row * TILE_SIZE);
        dx *= 2;
        dy *= 2;
        dx = Math.abs(dx);
        dy = Math.abs(dy);
    }

    private float calculateDistance(float x1, float y1, float x2, float y2) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public void countdown() {
        countdown--;
        countdownText.setText("" + countdown);
    }

    @Override
    public void update(float dt) {
        animation.update(dt);
        boolean atDest = atDestination();
        moveToDestination(dt);
        if (!atDest && atDestination()) {
            context.audio.playSound("bombland", 0.2f);
        }

        float distLeft = calculateDistance(x, y, col * TILE_SIZE, row * TILE_SIZE);
        float percent = distLeft / totalDist;
        jumpy = MathUtils.sin(MathUtils.PI * percent) * 70;

        scale = 1 + 1.2f * jumpy / 70;

        countdownText.x = x + 9;
        countdownText.y = y + jumpy + 9;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(1, 1, 1, 1);
//        sb.draw(image, x, y + jumpy);
        Utils.drawCenteredScaled(sb, animation.getImage(), x + w / 2, y + h / 2 + jumpy, scale);
        countdownText.render(sb);
    }
}
