package com.distraction.minijam196.screens;

import static com.distraction.minijam196.Constants.HEIGHT;
import static com.distraction.minijam196.Constants.TITLE_BG;
import static com.distraction.minijam196.Constants.TITLE_STRIPE;
import static com.distraction.minijam196.Constants.WHITE;
import static com.distraction.minijam196.Constants.WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.minijam196.Context;
import com.distraction.minijam196.Utils;
import com.distraction.minijam196.entity.Snake;
import com.distraction.minijam196.entity.TextButton;

public class TitleScreen extends Screen {

    private final TextureRegion pixel;
    private final TextureRegion title;

    private final TextButton playButton;

    public TitleScreen(Context context) {
        super(context);
        pixel = context.getPixel();
        title = context.getImage("title");
        playButton = new TextButton(context);
        playButton.text.setText("Play");
        playButton.setPosition(WIDTH / 2f, 50);

        if (!context.loaded) {
            context.loaded = true;
            ignoreInput = true;
            in = new Transition(context, Transition.Type.FLASH_IN, 1f, () -> ignoreInput = false);
            in.start();
        }
    }

    @Override
    public void input() {
        if (ignoreInput) return;

        m.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        unproject();
        playButton.setHover(playButton.contains(m.x, m.y));
        if (Gdx.input.justTouched() && playButton.contains(m.x, m.y)) {
            out.setCallback(() -> context.sm.replace(new PlayScreen(context, Snake.SnakeType.RED)));
            out.start();
        }

    }

    @Override
    public void update(float dt) {
        in.update(dt);
        out.update(dt);
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
        Utils.drawCentered(sb, title, WIDTH / 2f, HEIGHT / 2f);

        playButton.render(sb);

        in.render(sb);
        out.render(sb);

        sb.end();
    }

}
