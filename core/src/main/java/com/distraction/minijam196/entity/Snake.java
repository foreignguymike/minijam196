package com.distraction.minijam196.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.minijam196.Context;

import java.util.ArrayList;
import java.util.List;

public class Snake extends GridEntity {

    private final int maxEnergy = 5;
    public int energy = 5;
    private final List<SnakeEntity> bodies;

    public Snake(Context context) {
        bodies = new ArrayList<>();
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
        updateDirections();

    }

    public void move(int dr, int dc) {
        moveBodies();
        SnakeEntity head = bodies.get(0);
        Direction direction = getDirection(head.row, head.col, head.row + dr, head.col + dc);
        head.setDest(head.row + dr, head.col + dc, direction);
        updateDirections();
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
        for (SnakeEntity body : bodies) body.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        for (int i = bodies.size() - 1; i >= 0; i--) {
            bodies.get(i).render(sb);
        }
    }
}
