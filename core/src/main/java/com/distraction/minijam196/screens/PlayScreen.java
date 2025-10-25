package com.distraction.minijam196.screens;

import static com.distraction.minijam196.Constants.HEIGHT;
import static com.distraction.minijam196.Constants.NUM_COLS;
import static com.distraction.minijam196.Constants.NUM_ROWS;
import static com.distraction.minijam196.Constants.TILE_SIZE;
import static com.distraction.minijam196.Constants.WIDTH;

import com.badlogic.gdx.Gdx;
import com.distraction.minijam196.Constants;
import com.distraction.minijam196.Context;
import com.distraction.minijam196.entity.Bomb;
import com.distraction.minijam196.entity.Direction;
import com.distraction.minijam196.entity.ImageButton;
import com.distraction.minijam196.entity.Menu;
import com.distraction.minijam196.entity.Snake;
import com.distraction.minijam196.entity.SnakeEntity;
import com.distraction.minijam196.entity.SnakeHead;

import java.util.ArrayList;
import java.util.List;

public class PlayScreen extends Screen {

    enum TurnPhase {
        BOMBS,
        ACTION
    }

    private final Snake player;
    private final List<Snake> snakes;
    private final List<Bomb> bombs;
    private final Menu menu;

    private final ImageButton cursor;

    private int currentTurnIndex = 0;
    private TurnPhase turnPhase = TurnPhase.BOMBS;
    private float timer = 0;

    public PlayScreen(Context context) {
        super(context);

        if (!context.loaded) {
            context.loaded = true;
            ignoreInput = true;
            in.start();
        }

        List<SnakeEntity> bodies = new ArrayList<>();
        bodies.add(new SnakeHead(context, 10, 7, Direction.RIGHT));
        bodies.add(new SnakeEntity(context, 10, 6));
        bodies.add(new SnakeEntity(context, 10, 5));
        bodies.add(new SnakeEntity(context, 10, 4));
        bodies.add(new SnakeEntity(context, 10, 3));
        bodies.add(new SnakeEntity(context, 10, 2));
        bodies.add(new SnakeEntity(context, 10, 1));
        bodies.add(new SnakeEntity(context, 10, 0));
        bodies.add(new SnakeEntity(context, 11, 0));
        bodies.add(new SnakeEntity(context, 12, 0));
        bodies.add(new SnakeEntity(context, 13, 0));
        player = new Snake(context, bodies);

        bodies = new ArrayList<>();
        bodies.add(new SnakeHead(context, 10, 17, Direction.LEFT));
        bodies.add(new SnakeEntity(context, 10, 18));
        bodies.add(new SnakeEntity(context, 10, 19));
        bodies.add(new SnakeEntity(context, 10, 20));
        bodies.add(new SnakeEntity(context, 10, 21));
        bodies.add(new SnakeEntity(context, 10, 22));
        bodies.add(new SnakeEntity(context, 10, 23));
        bodies.add(new SnakeEntity(context, 11, 23));
        bodies.add(new SnakeEntity(context, 12, 23));
        bodies.add(new SnakeEntity(context, 13, 23));
        bodies.add(new SnakeEntity(context, 14, 23));

        snakes = new ArrayList<>();
        snakes.add(player);
        snakes.add(new Snake(context, bodies));

        bombs = new ArrayList<>();

        menu = new Menu(context, player, snakes, () -> {
            snakes.get(currentTurnIndex).canBomb = true;
            currentTurnIndex++;
            if (currentTurnIndex == snakes.size()) currentTurnIndex = 0;
            for (Bomb bomb : bombs) bomb.countdown();
        });

        cursor = new ImageButton(context.getImage("cursor"));
    }

    @Override
    public void input() {
        if (ignoreInput) return;

        if (snakes.get(currentTurnIndex) != player) return;

        menu.input();

        m.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        unproject();

        if (m.x >= 0 && m.x <= NUM_COLS * TILE_SIZE && m.y >= 0 && m.y <= NUM_ROWS * TILE_SIZE) {
            cursor.x = (int) (m.x / TILE_SIZE) * TILE_SIZE + TILE_SIZE / 2f;
            cursor.y = (int) (m.y / TILE_SIZE) * TILE_SIZE + TILE_SIZE / 2f;
        } else {
            cursor.x = cursor.y = -100;
        }

        if (Gdx.input.justTouched()) {
            if (cursor.x >= 0) {
                player.bomb(bombs, (int) (cursor.y / TILE_SIZE), (int) (cursor.x / TILE_SIZE));
            }
        }

    }

    @Override
    public void update(float dt) {
        in.update(dt);
        out.update(dt);

        menu.update(dt);
        menu.waiting = snakes.get(currentTurnIndex) != player;

        if (turnPhase == TurnPhase.BOMBS) {
            for (int i = bombs.size() - 1; i >= 0; i--) {
                Bomb bomb = bombs.get(i);
                if (bomb.countdown == 0) bombs.remove(i);
            }
        } else {

        }

        player.update(dt);
        for (Snake s : snakes) s.update(dt);
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
        cursor.render(sb);

        // snake
        player.render(sb);
        for (Snake s : snakes) s.render(sb);

        menu.render(sb);

        sb.setProjectionMatrix(cam.combined);

        in.render(sb);
        out.render(sb);

        sb.end();
    }
}
