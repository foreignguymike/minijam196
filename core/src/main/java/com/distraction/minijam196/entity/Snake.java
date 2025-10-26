package com.distraction.minijam196.entity;

import static com.distraction.minijam196.Constants.NUM_COLS;
import static com.distraction.minijam196.Constants.NUM_ROWS;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.distraction.minijam196.Context;

import java.util.ArrayList;
import java.util.List;

public class Snake extends GridEntity {

    private final Context context;

    private final int maxEnergy = 7;
    public int energy = maxEnergy;
    private final List<Snake> snakes;
    private final List<SnakeEntity> bodies = new ArrayList<>();
    private final List<Bomb> bombs;

    public boolean ready; // bot only
    private Bomb bomb;
    public int maxBombs = 2;
    public int bombsRemaining = maxBombs;
    private final float range = 6;

    private float damageTime;
    public boolean dead;

    public Snake(Context context, List<Snake> snakes, List<SnakeEntity> bodies, List<Bomb> bombs) {
        this.context = context;
        this.snakes = snakes;
        this.bodies.addAll(bodies);
        this.bombs = bombs;
        updateDirections();
        ready = true;
    }

    public void startTurn() {
        energy = maxEnergy;
        bombsRemaining = maxBombs;
    }

    /**
     * @return false if end turn
     */
    public boolean next() {
        if (dead) return false;
        if (bombsRemaining == 0 && energy == 0) return false;

        SnakeEntity head = bodies.get(0);

        if (bombsRemaining > 0) {
            SnakeEntity se = getClosestSnakeEntity();
            if (se == null) return false;
            int dr = se.row - head.row;
            int dc = se.col - head.col;
            if (Math.abs(dr) + Math.abs(dc) <= range) {
                if (!tryBomb(se.row, se.col)) {
                    Bomb cb = getClosestBomb();
                    if (MathUtils.randomBoolean()) {
                        return moveAwayFrom(cb) || moveAwayFrom(se);
                    } else {
                        return moveTowards(se);
                    }
                }
            } else {
                if (!moveTowards(se)) {
                    if (!tryBomb(se.row, se.col)) {
                        Bomb cb = getClosestBomb();
                        if (MathUtils.randomBoolean()) {
                            return moveAwayFrom(cb) || moveAwayFrom(se);
                        } else {
                            return moveTowards(se);
                        }
                    }
                }
            }
        } else {
            Bomb cb = getClosestBomb();
            if (cb != null) {
                return moveAwayFrom(cb);
            } else {
                SnakeEntity se = getClosestSnakeEntity();
                return moveAwayFrom(se);
            }
        }
        return true;
    }

    private boolean moveTowards(GridEntity other) {
        SnakeEntity head = bodies.get(0);
        int dr = other.row - head.row;
        int dc = other.col - head.col;
        if (Math.abs(dr) > Math.abs(dc)) {
            if (dr > 0 && move(1, 0)) return true;
            if (dr < 0 && move(-1, 0)) return true;
            if (dc > 0 && move(0, 1)) return true;
            return dc < 0 && move(0, -1);
        } else {
            if (dc > 0 && move(0, 1)) return true;
            if (dc < 0 && move(0, -1)) return true;
            if (dr > 0 && move(1, 0)) return true;
            if (dr < 0 && move(-1, 0)) return true;
        }
        if (move(1, 0)) return true;
        if (move(-1, 0)) return true;
        if (move(0, 1)) return true;
        return move(0, -1);
    }

    public boolean moveAwayFrom(GridEntity other) {
        SnakeEntity head = bodies.get(0);
        int dr = other.row - head.row;
        int dc = other.col - head.col;
        if (Math.abs(dr) > Math.abs(dc)) {
            if (dr >= 0 && move(-1, 0)) return true;
            if (dr < 0 && move(1, 0)) return true;
            if (dc >= 0 && move(0, -1)) return true;
            return dc < 0 && move(0, 1);
        } else {
            if (dc >= 0 && move(0, -1)) return true;
            if (dc < 0 && move(0, 1)) return true;
            if (dr >= 0 && move(-1, 0)) return true;
            if (dr < 0 && move(1, 0)) return true;
        }
        if (move(1, 0)) return true;
        if (move(-1, 0)) return true;
        if (move(0, 1)) return true;
        return move(0, -1);
    }

    private Bomb getClosestBomb() {
        SnakeEntity head = bodies.get(0);
        Bomb cb = null;
        int d = 100;
        for (Bomb b : bombs) {
            int dist = Math.abs(b.row - head.row) + Math.abs(b.col - head.col);
            if (d > dist) {
                d = dist;
                cb = b;
            }
        }
        return cb;
    }

    private SnakeEntity getClosestSnakeEntity() {
        SnakeEntity head = bodies.get(0);
        SnakeEntity se = null;
        int d = 100;
        for (Snake s : snakes) {
            if (this == s) continue;
            List<SnakeEntity> bodies = s.bodies;
            for (SnakeEntity b : bodies) {
                int dist = Math.abs(b.row - head.row) + Math.abs(b.col - head.col);
                if (d > dist) {
                    d = dist;
                    se = b;
                }
            }
        }
        return se;
    }

    public boolean move(int dr, int dc) {
        if (energy <= 0) return false;
        SnakeEntity head = bodies.get(0);
        int nr = bodies.get(0).row + dr;
        int nc = bodies.get(0).col + dc;
        if (!isValid(nr, nc)) return false;
        moveBodies();
        Direction direction = getDirection(head.row, head.col, head.row + dr, head.col + dc);
        head.setDest(head.row + dr, head.col + dc, direction);
        updateDirections();
        energy--;
        return true;
    }

    public boolean tryBomb(int tr, int tc) {
        if (bombsRemaining > 0 && bomb == null) {
            SnakeEntity head = bodies.get(0);
            int dr = tr - head.row;
            int dc = tc - head.col;
            int dist = Math.abs(dr) + Math.abs(dc);
            if (dist > range) {
                float scale = range / dist;
                dr = MathUtils.round(dr * scale);
                dc = MathUtils.round(dc * scale);
                tr = head.row + dr;
                tc = head.col + dc;
            }

            if (isValid(tr, tc)) {
                bombsRemaining--;
                bomb = new Bomb(context, Bomb.BombType.BOMB, snakes.size(), head.row, head.col);
                bomb.setDest(tr, tc);
                return true;
            }

            for (int radius = 1; radius < range; radius++) {
                for (int dy = -radius; dy <= radius; dy++) {
                    int dx = radius - Math.abs(dy);
                    int[] tryRows = {tr + dy, tr + dy};
                    int[] tryCols = {tc + dx, tc - dx};
                    for (int i = 0; i < 2; i++) {
                        int r = tryRows[i];
                        int c = tryCols[i];
                        dist = Math.abs(r - head.row) + Math.abs(c - head.col);
                        if (dist > range) continue;
                        if (isValid(r, c)) {
                            bombsRemaining--;
                            bomb = new Bomb(context, Bomb.BombType.BOMB, snakes.size(), head.row, head.col);
                            bomb.setDest(r, c);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void bomb(int tr, int tc) {
        if (bombsRemaining > 0 && bomb == null) {
            SnakeEntity head = bodies.get(0);
            int dist = Math.abs(tr - head.row) + Math.abs(tc - head.col);
            if (dist > range) return;
            if (isValid(tr, tc)) {
                bombsRemaining--;
                bomb = new Bomb(context, Bomb.BombType.BOMB, snakes.size(), head.row, head.col);
                bomb.setDest(tr, tc);
            }
        }
    }

    private boolean isValid(int row, int col) {
        if (row < 0 || row >= NUM_ROWS || col < 0 || col >= NUM_COLS) {
            return false;
        }
        for (Snake s : snakes) {
            for (SnakeEntity body : s.bodies) {
                if (body.row == row && body.col == col) {
                    return false;
                }
            }
        }
        for (Bomb bomb : bombs) {
            if (bomb.row == row && bomb.col == col) {
                return false;
            }
        }
        return true;
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

    public void checkHit(List<Bomb> explodingBombs) {
        for (Bomb b : explodingBombs) {
            for (SnakeEntity se : bodies) {
                int dr = b.row - se.row;
                int dc = b.col - se.col;
                if (Math.abs(dr) + Math.abs(dc) <= b.type.range) {
                    damageTime = 1f;
                    bodies.remove(bodies.size() - 1);
                    if (bodies.size() <= 1) {
                        dead = true;
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void update(float dt) {
        if (dead) return;
        damageTime -= dt;

        boolean moving = false;
        for (SnakeEntity body : bodies) {
            body.update(dt);
            if (!body.atDestination()) {
                moving = true;
            }
        }

        if (bomb != null) {
            bomb.update(dt);
            if (bomb.atDestination()) {
                bombs.add(bomb);
                bomb = null;
            }
        }

        ready = !moving && bomb == null;
    }

    @Override
    public void render(SpriteBatch sb) {
        if (dead) return;
        if (damageTime <= 0 || damageTime % 0.2f < 0.1f) {
            for (int i = bodies.size() - 1; i >= 0; i--) {
                bodies.get(i).render(sb);
            }
        }

        if (bomb != null) bomb.render(sb);
    }
}
