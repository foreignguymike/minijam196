package com.distraction.minijam196.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.minijam196.Context;

import java.util.ArrayList;
import java.util.List;

public class Snake extends GridEntity {

    private final Context context;

    private final int maxEnergy = 8;
    public int energy = maxEnergy;
    private final List<SnakeEntity> bodies = new ArrayList<>();

    public boolean ready;
    private Bomb bomb;
    public boolean canBomb = true;

    public Snake(Context context, List<SnakeEntity> bodies) {
        this.context = context;
        this.bodies.addAll(bodies);
        updateDirections();
        ready = true;
    }

    public void next() {

    }

    public void move(List<Snake> snakes, int dr, int dc) {
        if (energy <= 0) return;
        SnakeEntity head = bodies.get(0);
        for (Snake snake : snakes) {
            for (SnakeEntity body : snake.bodies) {
                if (body.row == head.row + dr && body.col == head.col + dc) return;
            }
        }
        moveBodies();
        Direction direction = getDirection(head.row, head.col, head.row + dr, head.col + dc);
        head.setDest(head.row + dr, head.col + dc, direction);
        updateDirections();
        energy--;
    }

    public void bomb(List<Bomb> bombs) {
        if (canBomb) {
            canBomb = false;
            bomb = new Bomb(context, Bomb.BombType.BOMB, bodies.get(0).row, bodies.get(0).col);
            bombs.add(bomb);
        }
    }

    public void bomb(List<Bomb> bombs, int row, int col) {
        if (canBomb) {
            canBomb = false;
            bomb = new Bomb(context, Bomb.BombType.BOMB, bodies.get(0).row, bodies.get(0).col);
            bomb.setDest(row, col);
            bombs.add(bomb);
        }
    }

    private void moveBodies() {
        for (int i = bodies.size() - 2; i >= 0; i--) {
            SnakeEntity next = bodies.get(i);
            SnakeEntity curr = bodies.get(i + 1);
            curr.setDest(next.row, next.col);
        }
    }

    private void updateDirections() {
        for (int i = 0; i < bodies.size() - 1; i++) {
            SnakeEntity next = bodies.get(i);
            SnakeEntity curr = bodies.get(i + 1);
            curr.direction = getDirection(curr.row, curr.col, next.row, next.col);
        }
    }

    private Direction getDirection(int currRow, int currCol, int nextRow, int nextCol) {
        if (currRow < nextRow) return Direction.UP;
        if (currRow > nextRow) return Direction.DOWN;
        if (currCol < nextCol) return Direction.RIGHT;
        return Direction.LEFT;
    }

    @Override
    public void update(float dt) {
        boolean moving = false;
        for (SnakeEntity body : bodies) {
            body.update(dt);
            if (!body.atDestination()) {
                moving = true;
            }
        }

        if (bomb != null) {
            bomb.update(dt);
//            if (bomb.atDestination()) bomb = null;
        }

        ready = !moving && bomb == null;
    }

    @Override
    public void render(SpriteBatch sb) {
        for (int i = bodies.size() - 1; i >= 0; i--) {
            bodies.get(i).render(sb);
        }
        if (bomb != null) bomb.render(sb);
    }
}
