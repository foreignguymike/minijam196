package com.distraction.minijam196.screens;

import static com.distraction.minijam196.Constants.HEIGHT;
import static com.distraction.minijam196.Constants.WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.distraction.minijam196.Context;
import com.distraction.minijam196.entity.Snake;
import com.distraction.minijam196.entity.TextButton;

public class PauseScreen extends Screen {

    private final Snake.SnakeType snakeType;
    private float dim;

    private final TextButton restartButton;
    private final TextButton backButton;

    public PauseScreen(Context context, Snake.SnakeType snakeType) {
        super(context);
        this.snakeType = snakeType;

        restartButton = new TextButton(context, "Restart", WIDTH / 2f, HEIGHT / 2f + 20f);
        backButton = new TextButton(context, "Back", WIDTH / 2f, HEIGHT / 2f - 20f);

        transparent = true;

        Vector2 start = new Vector2(WIDTH / 2f, -HEIGHT / 2f);
        Vector2 end = new Vector2(WIDTH / 2f, HEIGHT / 2f);
        ignoreInput = true;
        in = new Transition(
            context,
            Transition.Type.PAN, cam,
            start,
            end,
            0.2f,
            () -> ignoreInput = false
        );
        in.start();
        out = new Transition(
            context,
            Transition.Type.PAN, cam,
            end,
            start,
            0.2f,
            () -> {
                context.sm.pop();
                context.sm.peek().ignoreInput = false;
            }
        );
        cam.position.x = start.x;
        cam.position.y = start.y;
        cam.update();
    }

    @Override
    public void input() {
        if (ignoreInput) return;

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            ignoreInput = true;
            out.start();
        }

        m.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        unproject();
        restartButton.setHover(restartButton.contains(m.x, m.y));
        backButton.setHover(backButton.contains(m.x, m.y));
        if (Gdx.input.justTouched()) {
            if (restartButton.contains(m.x, m.y)) {
                ignoreInput = true;
                out.setCallback(() -> {
                    context.sm.pop();
                    context.sm.replace(new PlayScreen(context, snakeType));
                });
                out.start();
            } else if (backButton.contains(m.x, m.y)) {
                ignoreInput = true;
                out.setTransition(Transition.Type.FLASH_OUT);
                out.setCallback(() -> {
                    context.sm.pop();
                    context.sm.replace(new TitleScreen(context));
                });
                out.start();
            }
        }
    }

    @Override
    public void update(float dt) {
        in.update(dt);
        out.update(dt);
        if (out.started()) {
            dim -= 5 * dt;
            if (dim < 0f) dim = 0f;
        } else if (in.started()) {
            dim += 5 * dt;
            if (dim > 0.8f || in.isFinished()) dim = 0.8f;
        }
    }

    @Override
    public void render() {
        sb.begin();

        sb.setProjectionMatrix(uiCam.combined);
        sb.setColor(0, 0, 0, dim);
        sb.draw(pixel, 0, 0, WIDTH, HEIGHT);

        sb.setProjectionMatrix(cam.combined);
        sb.setColor(1, 1, 1, 1);
        restartButton.render(sb);
        backButton.render(sb);

        sb.end();
    }

}
