package com.distraction.minijam196.screens;

import static com.distraction.minijam196.Constants.HEIGHT;
import static com.distraction.minijam196.Constants.NUM_COLS;
import static com.distraction.minijam196.Constants.NUM_ROWS;
import static com.distraction.minijam196.Constants.TILE_SIZE;
import static com.distraction.minijam196.Constants.WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.minijam196.Context;
import com.distraction.minijam196.Utils;
import com.distraction.minijam196.entity.Bomb;
import com.distraction.minijam196.entity.Direction;
import com.distraction.minijam196.entity.ImageButton;
import com.distraction.minijam196.entity.Menu;
import com.distraction.minijam196.entity.Particle;
import com.distraction.minijam196.entity.Snake;
import com.distraction.minijam196.entity.SnakeEntity;
import com.distraction.minijam196.entity.TextButton;
import com.distraction.minijam196.entity.TextEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PlayScreen extends Screen {

    enum TurnPhase {
        BOMBS,
        ACTION,
        FINISH
    }

    private final Snake.SnakeType snakeType;

    private final TextureRegion tile;
    private final TextureRegion rangeImage;

    private Snake player;
    private final List<Snake> snakes;
    private final List<Bomb> bombs;
    private final Menu menu;

    private final List<Particle> particles;

    private final ImageButton cursor;

    private int currentTurnIndex = 0;
    private TurnPhase turnPhase = TurnPhase.BOMBS;

    private float time;

    private float dim;
    private final TextEntity finishText;
    private final TextButton restartButton;

    public PlayScreen(Context context, Snake.SnakeType snakeType) {
        super(context);
        this.snakeType = snakeType;
        tile = context.getImage("tile");
        rangeImage = context.getImage("range");

        ignoreInput = true;
        in.start();

        particles = new ArrayList<>();

        snakes = new ArrayList<>();
        bombs = new ArrayList<>();

        Snake.SnakeType[] snakeTypeList = Snake.SnakeType.values();
        List<Snake.SnakeType> snakeTypes = Arrays.asList(snakeTypeList);
        Collections.shuffle(snakeTypes);

        int playerIndex = MathUtils.random(0, snakeTypeList.length - 1);

        if (snakeTypes.get(playerIndex) != snakeType) {
            int index = 0;
            for (int i = 0; i < snakeTypes.size(); i++) {
                if (snakeTypes.get(i) == snakeType) {
                    index = i;
                    break;
                }
            }
            Utils.swap(snakeTypes, index, playerIndex);
        }

        TextureRegion head = context.getImage(snakeTypes.get(0).name).split(20, 20)[0][0];
        TextureRegion body = context.getImage(snakeTypes.get(0).name).split(20, 20)[0][1];
        List<SnakeEntity> bodies = new ArrayList<>();
        bodies.add(new SnakeEntity(head, 9, 6, Direction.RIGHT));
        bodies.add(new SnakeEntity(body, 9, 5));
        bodies.add(new SnakeEntity(body, 10, 5));
        bodies.add(new SnakeEntity(body, 10, 4));
        bodies.add(new SnakeEntity(body, 10, 3));
        bodies.add(new SnakeEntity(body, 9, 3));
        bodies.add(new SnakeEntity(body, 8, 3));
        bodies.add(new SnakeEntity(body, 8, 2));
        bodies.add(new SnakeEntity(body, 8, 1));
        bodies.add(new SnakeEntity(body, 9, 1));
        bodies.add(new SnakeEntity(body, 10, 1));
        Snake snake = new Snake(context, snakes, bodies, bombs);
        snakes.add(snake);
        if (playerIndex == 0) {
            player = snake;
            player.showRange = true;
        }

        head = context.getImage(snakeTypes.get(1).name).split(20, 20)[0][0];
        body = context.getImage(snakeTypes.get(1).name).split(20, 20)[0][1];
        bodies = new ArrayList<>();
        bodies.add(new SnakeEntity(head, 14, 12, Direction.DOWN));
        bodies.add(new SnakeEntity(body, 15, 12));
        bodies.add(new SnakeEntity(body, 15, 11));
        bodies.add(new SnakeEntity(body, 15, 10));
        bodies.add(new SnakeEntity(body, 16, 10));
        bodies.add(new SnakeEntity(body, 17, 10));
        bodies.add(new SnakeEntity(body, 17, 11));
        bodies.add(new SnakeEntity(body, 17, 12));
        bodies.add(new SnakeEntity(body, 17, 13));
        bodies.add(new SnakeEntity(body, 17, 14));
        bodies.add(new SnakeEntity(body, 17, 15));
        snake = new Snake(context, snakes, bodies, bombs);
        snakes.add(snake);
        if (playerIndex == 1) {
            player = snake;
            player.showRange = true;
        }

        head = context.getImage(snakeTypes.get(2).name).split(20, 20)[0][0];
        body = context.getImage(snakeTypes.get(2).name).split(20, 20)[0][1];
        bodies = new ArrayList<>();
        bodies.add(new SnakeEntity(head, 9, 17, Direction.LEFT));
        bodies.add(new SnakeEntity(body, 9, 18));
        bodies.add(new SnakeEntity(body, 10, 18));
        bodies.add(new SnakeEntity(body, 10, 19));
        bodies.add(new SnakeEntity(body, 10, 20));
        bodies.add(new SnakeEntity(body, 9, 20));
        bodies.add(new SnakeEntity(body, 8, 20));
        bodies.add(new SnakeEntity(body, 8, 21));
        bodies.add(new SnakeEntity(body, 8, 22));
        bodies.add(new SnakeEntity(body, 9, 22));
        bodies.add(new SnakeEntity(body, 10, 22));
        snake = new Snake(context, snakes, bodies, bombs);
        snakes.add(snake);
        if (playerIndex == 2) {
            player = snake;
            player.showRange = true;
        }

        head = context.getImage(snakeTypes.get(3).name).split(20, 20)[0][0];
        body = context.getImage(snakeTypes.get(3).name).split(20, 20)[0][1];
        bodies = new ArrayList<>();
        bodies.add(new SnakeEntity(head, 3, 12, Direction.UP));
        bodies.add(new SnakeEntity(body, 2, 12));
        bodies.add(new SnakeEntity(body, 2, 11));
        bodies.add(new SnakeEntity(body, 2, 10));
        bodies.add(new SnakeEntity(body, 1, 10));
        bodies.add(new SnakeEntity(body, 0, 10));
        bodies.add(new SnakeEntity(body, 0, 11));
        bodies.add(new SnakeEntity(body, 0, 12));
        bodies.add(new SnakeEntity(body, 0, 13));
        bodies.add(new SnakeEntity(body, 0, 14));
        bodies.add(new SnakeEntity(body, 0, 15));
        snake = new Snake(context, snakes, bodies, bombs);
        snakes.add(snake);
        if (playerIndex == 3) {
            player = snake;
            player.showRange = true;
        }

        menu = new Menu(context, player, this::nextTurn);

        cursor = new ImageButton(context.getImage("cursor"));
        cursor.x = cursor.y = -100;

        float fieldCenter = NUM_COLS * TILE_SIZE / 2f;
        finishText = new TextEntity(context.getFont(Context.VCR20), "", fieldCenter, HEIGHT / 2f + 30, TextEntity.Alignment.CENTER);
        restartButton = new TextButton(context, "Restart", fieldCenter, HEIGHT / 2f - 30);
    }

    private void nextTurn() {
        if (isGameOver()) {
            turnPhase = TurnPhase.FINISH;
            return;
        }
        currentTurnIndex++;
        if (currentTurnIndex >= snakes.size()) currentTurnIndex = 0;

        Snake snake = snakes.get(currentTurnIndex);
        if (!snake.dead) {
            snakes.get(currentTurnIndex).startTurn();
            turnPhase = TurnPhase.BOMBS;
            for (Bomb bomb : bombs) bomb.countdown();
        }
    }

    private boolean isPlayerTurn() {
        if (currentTurnIndex < 0) currentTurnIndex = 0;
        if (currentTurnIndex >= snakes.size()) currentTurnIndex = snakes.size() - 1;
        return snakes.get(currentTurnIndex) == player;
    }

    private int getAliveCount() {
        int count = 0;
        for (Snake s : snakes) {
            if (!s.dead) count++;
        }
        return count;
    }

    private void explode(Bomb b) {
        int range = b.type.range;
        for (int row = -range; row <= range; row++) {
            for (int col = -range; col <= range; col++) {
                if (Math.abs(col) + Math.abs(row) <= range) {
                    Particle p = new Particle(context.getImage("explosion").split(20, 20)[0]);
                    p.x = (b.col + col) * TILE_SIZE;
                    p.y = (b.row + row) * TILE_SIZE;
                    particles.add(p);
                }
            }
        }
    }

    private boolean isGameOver() {
        int alive = getAliveCount();
        return player.dead || alive <= 1;
    }

    @Override
    public void input() {
        if (ignoreInput) return;

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            ignoreInput = true;
            context.sm.push(new PauseScreen(context, snakeType));
            return;
        }

        if (!isGameOver() && !isPlayerTurn()) return;

        if (turnPhase == TurnPhase.ACTION) {
            menu.input();
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) player.move(1, 0);
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) player.move(-1, 0);
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) player.move(0, -1);
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) player.move(0, 1);


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
                    player.bomb((int) (cursor.y / TILE_SIZE), (int) (cursor.x / TILE_SIZE));
                }
            }
        } else if (turnPhase == TurnPhase.FINISH) {
            m.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            unproject();
            if (Gdx.input.justTouched()) {
                if (restartButton.contains(m.x, m.y)) {
                    ignoreInput = true;
                    out.setCallback(() -> context.sm.replace(new PlayScreen(context, snakeType)));
                    out.start();
                }
            }
            restartButton.setHover(restartButton.contains(m.x, m.y));
        }

    }

    @Override
    public void update(float dt) {
        in.update(dt);
        out.update(dt);

        if (isGameOver()) {
            cam.position.set(WIDTH / 2f, HEIGHT / 2f, 0);
            cam.update();
            turnPhase = TurnPhase.FINISH;
            if (player.dead) finishText.setText("You lose!");
            else finishText.setText("Victory!");
        }

        menu.update(dt);
        menu.waiting = !isPlayerTurn();

        if (turnPhase == TurnPhase.FINISH) {
            dim += dt;
            if (dim > 0.8f) dim = 0.8f;
        }
        if (turnPhase == TurnPhase.BOMBS) {
            List<Bomb> explodingBombs = new ArrayList<>();
            for (int i = bombs.size() - 1; i >= 0; i--) {
                Bomb bomb = bombs.get(i);
                if (bomb.countdown == 0) {
                    bombs.remove(i);
                    explodingBombs.add(bomb);
                }
            }
            if (!explodingBombs.isEmpty()) {
                time = 0.5f;
                for (Snake s : snakes) {
                    s.checkHit(explodingBombs);
                }
                for (Bomb b : explodingBombs) {
                    explode(b);
                }
            }
            time -= dt;
            if (time < 0) {
                turnPhase = TurnPhase.ACTION;
                cam.position.set(WIDTH / 2f, HEIGHT / 2f, 0);
                cam.update();
            } else {
                cam.position.set(WIDTH / 2f + MathUtils.random(-5, 5), HEIGHT / 2f + MathUtils.random(-5, 5), 0);
                cam.update();
            }
        } else if (turnPhase == TurnPhase.ACTION) {
            if (!isPlayerTurn()) {
                Snake bot = snakes.get(currentTurnIndex);
                if (bot.ready) {
                    if (!bot.next()) {
                        nextTurn();
                    }
                }
            }
        }

        for (int i = snakes.size() - 1; i >= 0; i--) {
            Snake s = snakes.get(i);
            s.update(dt);
        }
        for (int i = particles.size() - 1; i >= 0; i--) {
            Particle p = particles.get(i);
            p.update(dt);
            if (p.remove) particles.remove(i);
        }
    }

    @Override
    public void render() {
        sb.begin();

        sb.setProjectionMatrix(cam.combined);

        sb.setColor(1, 1, 1, 1);
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                sb.draw(tile, col * TILE_SIZE, row * TILE_SIZE);
            }
        }
        cursor.render(sb);

        if (!player.dead && player.showRange) {
            sb.setColor(1, 1, 1, 0.1f);
            Utils.drawCentered(sb, rangeImage, player.getHeadCol() * TILE_SIZE + TILE_SIZE / 2f, player.getHeadRow() * TILE_SIZE + TILE_SIZE / 2f);
        }
        sb.setColor(1, 1, 1, 1);
        for (Snake s : snakes) s.render(sb);
        for (Bomb b : bombs) b.render(sb);
        for (Particle p : particles) p.render(sb);

        menu.render(sb);

        sb.setProjectionMatrix(cam.combined);

        if (turnPhase == TurnPhase.FINISH) {
            sb.setColor(0, 0, 0, dim);
            sb.draw(pixel, 0, 0, WIDTH, HEIGHT);
            finishText.render(sb);
            restartButton.render(sb);
        }

        sb.setColor(1, 1, 1, 1);
        in.render(sb);
        out.render(sb);

        sb.end();
    }
}
