package com.distraction.minijam196.screens;

import static com.distraction.minijam196.Constants.HEIGHT;
import static com.distraction.minijam196.Constants.NUM_COLS;
import static com.distraction.minijam196.Constants.NUM_ROWS;
import static com.distraction.minijam196.Constants.TILE_SIZE;
import static com.distraction.minijam196.Constants.VERY_DARK_GRAY;
import static com.distraction.minijam196.Constants.WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.distraction.minijam196.Constants;
import com.distraction.minijam196.Context;
import com.distraction.minijam196.entity.Menu;
import com.distraction.minijam196.entity.Snake;

public class PlayScreen extends Screen {

    private final Snake snake;
    private final Menu menu;

    public PlayScreen(Context context) {
        super(context);

        if (!context.loaded) {
            context.loaded = true;
            ignoreInput = true;
            in.start();
        }

        snake = new Snake(context);
        menu = new Menu(context, snake);
    }

    @Override
    public void input() {
        if (ignoreInput) return;

        m.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        unproject();

        menu.onMouseMove(m.x, m.y);
        if (Gdx.input.justTouched()) {
            menu.onMouseClick(m.x, m.y);
        }

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
        sb.setColor(Constants.VERY_DARK_GREEN);
        sb.draw(pixel, 0, 0, WIDTH, HEIGHT);
        sb.setColor(Constants.DARK_GREEN);
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                if ((row + col) % 2 == 0) {
                    sb.draw(pixel, col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }
        sb.setColor(VERY_DARK_GRAY);
        sb.draw(pixel, NUM_COLS * TILE_SIZE, 0, WIDTH - NUM_COLS * TILE_SIZE, HEIGHT);

        // snake
        snake.render(sb);

        menu.render(sb);

        in.render(sb);
        out.render(sb);

        sb.end();
    }
}
