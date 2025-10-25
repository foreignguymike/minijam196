package com.distraction.minijam196.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.distraction.minijam196.Constants;
import com.distraction.minijam196.Context;
import com.distraction.minijam196.entity.Snake;

public class PlayScreen extends Screen {

    private final Snake snake;

    public PlayScreen(Context context) {
        super(context);

        if (!context.loaded) {
            context.loaded = true;
            ignoreInput = true;
            in.start();
        }

        snake = new Snake(context);
    }

    @Override
    public void input() {
        if (ignoreInput) return;

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) snake.move(1, 0);
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) snake.move(-1, 0);
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) snake.move(0, -1);
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) snake.move(0, 1);

    }

    @Override
    public void update(float dt) {
        in.update(dt);
        out.update(dt);

        snake.update(dt);
    }

    @Override
    public void render() {
        sb.begin();

        sb.setProjectionMatrix(cam.combined);

        // field
        sb.setColor(Constants.DARK_GRAY);
        sb.draw(pixel, 0, 0, Constants.WIDTH, Constants.HEIGHT);
        sb.setColor(Constants.GRAY);
        for (int row = 0; row < 18; row++) {
            for (int col = 0; col < 32; col++) {
                if ((row + col) % 2 == 0) {
                    sb.draw(pixel, col * 20, row * 20, 20, 20);
                }
            }
        }

        // snake
        snake.render(sb);

        in.render(sb);
        out.render(sb);

        sb.end();
    }
}
