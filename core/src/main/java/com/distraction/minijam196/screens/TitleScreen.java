package com.distraction.minijam196.screens;

import static com.distraction.minijam196.Constants.HEIGHT;
import static com.distraction.minijam196.Constants.TITLE_BG;
import static com.distraction.minijam196.Constants.TITLE_STRIPE;
import static com.distraction.minijam196.Constants.WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.minijam196.Context;
import com.distraction.minijam196.Utils;
import com.distraction.minijam196.entity.TextButton;
import com.distraction.minijam196.entity.TextEntity;

public class TitleScreen extends Screen {

    private final TextureRegion pixel;
    private final TextureRegion title;

    private final TextButton playButton;

    private final TextEntity jamText;

    private float time;
    private float scale = 1;

    public TitleScreen(Context context) {
        super(context);
        pixel = context.getPixel();
        title = context.getImage("title");
        playButton = new TextButton(context, "Play", WIDTH / 2f, 50);

        jamText = new TextEntity(context.getFont(Context.M5X716), "Mini Jam 196", WIDTH - 10, 10, TextEntity.Alignment.RIGHT);

        ignoreInput = true;
        in = new Transition(context, Transition.Type.FLASH_IN, 0.5f, () -> ignoreInput = false);
        in.start();
        context.audio.stopMusic();
    }

    @Override
    public void input() {
        if (ignoreInput) return;

        m.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        unproject();
        playButton.setHover(playButton.contains(m.x, m.y));
        if (Gdx.input.justTouched() && playButton.contains(m.x, m.y)) {
            ignoreInput = true;
            out.setCallback(() -> context.sm.replace(new SelectScreen(context)));
            out.start();
            context.audio.playSound("click");
        }

    }

    @Override
    public void update(float dt) {
        in.update(dt);
        out.update(dt);

        time += dt;
        scale = 1 + MathUtils.sin(3 * time) * 0.1f;
    }

    @Override
    public void render() {
        sb.begin();

        sb.setProjectionMatrix(cam.combined);
        sb.setColor(TITLE_BG);
        sb.draw(pixel, 0, 0, WIDTH, HEIGHT);
        sb.setColor(TITLE_STRIPE);
        sb.draw(pixel, 0, 152, WIDTH, 56);

        sb.setColor(1, 1, 1, 1);
        Utils.drawCenteredScaled(sb, title, WIDTH / 2f, HEIGHT / 2f, 1f);

        playButton.render(sb);

        jamText.render(sb);

        in.render(sb);
        out.render(sb);

        sb.end();
    }

}
