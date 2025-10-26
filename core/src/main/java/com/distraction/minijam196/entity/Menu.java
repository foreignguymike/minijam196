package com.distraction.minijam196.entity;

import static com.distraction.minijam196.Constants.HEIGHT;
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
    private final TextureRegion bg;

    private final TextEntity energyText;
    private final TextEntity moveText;
    private final TextureRegion keys;
    private final TextEntity bombText;
    private final TextureRegion mouse;

    private final TextButton endTurnButton;

    private final SimpleCallback endTurn;

    private final TextEntity waitingText;

    private final TextureRegion bombFill;
    private final TextureRegion bombEmpty;

    public boolean waiting;

    public Menu(Context context, Snake player, SimpleCallback endTurn) {
        this.player = player;
        this.endTurn = endTurn;

        x = WIDTH / 2f;
        y = HEIGHT / 2f;

        bg = context.getImage("menubg");

        viewport = new MyViewport(Constants.WIDTH, Constants.HEIGHT);
        cam = viewport.getCamera();
        m = new Vector3();

        float menuCenter = WIDTH - (WIDTH - NUM_COLS * TILE_SIZE) / 2f;
        energyText = new TextEntity(context.getFont(Context.VCR20), "Energy" + player.energy, menuCenter, 320, TextEntity.Alignment.CENTER);
        moveText = new TextEntity(context.getFont(Context.VCR20), "Move", menuCenter, 264, TextEntity.Alignment.CENTER);
        keys = context.getImage("keys");
        bombText = new TextEntity(context.getFont(Context.VCR20), "Bomb", menuCenter, 160, TextEntity.Alignment.CENTER);
        mouse = context.getImage("mouse");

        endTurnButton = new TextButton(context, "End Turn", menuCenter, 29);

        waitingText = new TextEntity(context.getFont(Context.VCR20), "Waiting...", menuCenter, 320, TextEntity.Alignment.CENTER);

        bombFill = context.getImage("bombfill");
        bombEmpty = context.getImage("bombempty");
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
        energyText.setText("Energy " + player.energy);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);

        sb.draw(bg, NUM_COLS * TILE_SIZE, 0);

        sb.setColor(1, 1, 1, 1);
        if (waiting) {
            waitingText.render(sb);
        } else {
            energyText.render(sb);
            moveText.render(sb);
            Utils.drawCentered(sb, keys, moveText.x, moveText.y - 33);
            bombText.render(sb);
            Utils.drawCentered(sb, mouse, bombText.x, bombText.y - 38);
            float bombWidth = player.maxBombs * 32;
            for (int i = 0; i < player.maxBombs; i++) {
                if (i < player.bombsRemaining) Utils.drawCentered(sb, bombFill, moveText.x - bombWidth / 2f + 16 + i * 32, 80);
                else Utils.drawCentered(sb, bombEmpty, moveText.x - bombWidth / 2f + 16 + i * 32, 80);
            }

            endTurnButton.render(sb);
        }
    }
}
