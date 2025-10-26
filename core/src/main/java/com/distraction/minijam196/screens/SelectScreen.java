package com.distraction.minijam196.screens;

import static com.distraction.minijam196.Constants.HEIGHT;
import static com.distraction.minijam196.Constants.SELECT_BG;
import static com.distraction.minijam196.Constants.WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.minijam196.Context;
import com.distraction.minijam196.entity.Snake;
import com.distraction.minijam196.entity.SnakeSelect;
import com.distraction.minijam196.entity.TextEntity;

import java.util.ArrayList;
import java.util.List;

public class SelectScreen extends Screen {

    private final TextureRegion pixel;
    private final TextEntity titleText;
    private final TextureRegion bomb;
    private float offset;

    private final List<SnakeSelect> snakeSelects;

    public SelectScreen(Context context) {
        super(context);
        pixel = context.getPixel();
        titleText = new TextEntity(context.getFont(Context.VCR20), "Choose snake", WIDTH / 2f, HEIGHT / 2f + 80, TextEntity.Alignment.CENTER);
        bomb = context.getImage("bombempty");

        snakeSelects = new ArrayList<>();
        Snake.SnakeType[] snakeTypes = Snake.SnakeType.values();
        float w = 110;
        float tw = w * snakeTypes.length;
        for (int i = 0; i < snakeTypes.length; i++) {
            Snake.SnakeType type = snakeTypes[i];
            SnakeSelect select = new SnakeSelect(context, type);
            select.x = WIDTH / 2f - tw / 2f + w / 2f + w * i;
            select.y = HEIGHT / 2f - 20;
            snakeSelects.add(select);
        }

        in.start();
    }

    @Override
    public void input() {
        if (ignoreInput) return;

        m.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        unproject();
        for (SnakeSelect select : snakeSelects) {
            select.setHover(select.contains(m.x, m.y));
            if (Gdx.input.justTouched() && select.contains(m.x, m.y)) {
                ignoreInput = true;
                out.setCallback(() -> context.sm.replace(new PlayScreen(context, select.type)));
                out.start();
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            ignoreInput = true;
            out.setTransition(Transition.Type.FLASH_OUT);
            out.setCallback(() -> context.sm.replace(new TitleScreen(context)));
            out.start();
        }
    }

    @Override
    public void update(float dt) {
        in.update(dt);
        out.update(dt);

        for (SnakeSelect select : snakeSelects) select.update(dt);

        offset += 10 * dt;
        if (offset > 40) {
            offset -= 40;
        }
    }

    @Override
    public void render() {
        sb.begin();

        sb.setProjectionMatrix(cam.combined);

        sb.setColor(SELECT_BG);
        sb.draw(pixel, 0, 0, WIDTH, HEIGHT);

        sb.setColor(1, 1, 1, 0.4f);
        for (int row = -1; row < HEIGHT / 40; row++) {
            for (int col = -1; col < WIDTH / 40; col++) {
                if ((row + col) % 2 == 0) {
                    sb.draw(bomb, col * 40 + offset, row * 40 + offset);
                }
            }
        }

        sb.setColor(1, 1, 1, 1);
        titleText.render(sb);

        for (SnakeSelect select : snakeSelects) select.render(sb);

        in.render(sb);
        out.render(sb);

        sb.end();
    }

}
