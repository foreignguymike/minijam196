package com.distraction.minijam196.screens;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.distraction.minijam196.Constants;
import com.distraction.minijam196.Context;
import com.distraction.minijam196.MyViewport;

public abstract class Screen {

    protected Context context;

    protected final TextureRegion pixel;

    public boolean transparent = false;

    protected Viewport viewport;
    protected Camera cam;
    protected final Vector3 m;

    protected SpriteBatch sb;

    protected boolean ignoreInput;

    public Transition in;
    public Transition out;

    protected Screen(Context context) {
        this.context = context;
        this.sb = context.sb;

        pixel = context.getPixel();

        viewport = new MyViewport(Constants.WIDTH, Constants.HEIGHT);
        cam = viewport.getCamera();

        m = new Vector3();

        in = new Transition(context, Transition.Type.CHECKERED_IN, 0.5f, () -> ignoreInput = false);
        out = new Transition(context, Transition.Type.CHECKERED_OUT, 0.5f);
    }

    public void unproject() {
        cam.unproject(m);
    }

    public void resume() {
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public abstract void input();

    public abstract void update(float dt);

    public abstract void render();

}
