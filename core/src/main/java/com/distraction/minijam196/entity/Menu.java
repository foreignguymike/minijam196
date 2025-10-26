package com.distraction.minijam196.entity;

import static com.distraction.minijam196.Constants.DARK_BLUE;
import static com.distraction.minijam196.Constants.HEIGHT;
import static com.distraction.minijam196.Constants.MENU_STRIPE_1;
import static com.distraction.minijam196.Constants.MENU_STRIPE_2;
import static com.distraction.minijam196.Constants.NUM_COLS;
import static com.distraction.minijam196.Constants.TILE_SIZE;
import static com.distraction.minijam196.Constants.WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.distraction.minijam196.Constants;
import com.distraction.minijam196.Context;
import com.distraction.minijam196.MyViewport;
import com.distraction.minijam196.SimpleCallback;
import com.distraction.minijam196.Utils;

import java.util.List;

public class Menu extends Entity {

    protected Viewport viewport;
    protected Camera cam;
    protected final Vector3 m;

    private final Snake player;
    private final List<Snake> snakes;

    private final TextureRegion pixel;

    private final TextEntity energyText;
    private final TextEntity moveText;
    private final TextureRegion keys;

    private final TextButton endTurnButton;

    private final SimpleCallback endTurn;

    private final TextEntity waitingText;

    public boolean waiting;

    public Menu(Context context, Snake player, List<Snake> snakes, SimpleCallback endTurn) {
        this.player = player;
        this.snakes = snakes;
        this.endTurn = endTurn;

        x = WIDTH / 2f;
        y = HEIGHT / 2f;

        pixel = context.getPixel();

        viewport = new MyViewport(Constants.WIDTH, Constants.HEIGHT);
        cam = viewport.getCamera();
        m = new Vector3();

        float menuCenter = WIDTH - (WIDTH - NUM_COLS * TILE_SIZE) / 2f;
        energyText = new TextEntity(context.getFont(Context.VCR20), "Energy: " + player.energy, menuCenter, 300, TextEntity.Alignment.CENTER);
        moveText = new TextEntity(context.getFont(Context.VCR20), "Move", menuCenter, 220, TextEntity.Alignment.CENTER);
        keys = context.getImage("keys");

        endTurnButton = new TextButton(context);
        endTurnButton.text.setText("End Turn");
        endTurnButton.setPosition(menuCenter, 50);

        waitingText = new TextEntity(context.getFont(Context.VCR20), "Waiting...", menuCenter, HEIGHT / 2f, TextEntity.Alignment.CENTER);
    }

    public void input() {
        m.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(m);

        endTurnButton.setHover(endTurnButton.contains(m.x, m.y) && !waiting);
        if (Gdx.input.justTouched()) {
            if (endTurnButton.contains(m.x, m.y)) endTurn.callback();
        }
    }

    @Override
    public void update(float dt) {
        energyText.setText("Energy: " + player.energy);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);

        sb.setColor(MENU_STRIPE_1);
        sb.draw(pixel, NUM_COLS * TILE_SIZE, 0, WIDTH - NUM_COLS * TILE_SIZE, HEIGHT);
        sb.setColor(MENU_STRIPE_2);
        sb.draw(pixel, NUM_COLS * TILE_SIZE + 32, 0, 32, HEIGHT);
        sb.draw(pixel, NUM_COLS * TILE_SIZE + 96, 0, 32, HEIGHT);

        sb.setColor(1, 1, 1, 1);
        if (waiting) {
            waitingText.render(sb);
        } else {
            energyText.render(sb);
            moveText.render(sb);
            Utils.drawCentered(sb, keys, moveText.x, moveText.y - 50);

            endTurnButton.render(sb);
        }
    }
}
