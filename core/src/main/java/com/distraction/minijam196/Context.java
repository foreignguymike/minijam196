package com.distraction.minijam196;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.minijam196.screens.PlayScreen;
import com.distraction.minijam196.screens.ScreenManager;

public class Context {

    private static final String ATLAS = "minijam196.atlas";
    public static final String M5X716 = "fonts/m5x716.fnt";
    public static final String M3X616 = "fonts/m3x616.fnt";

    public AssetManager assets;

    public ScreenManager sm;
    public SpriteBatch sb;

    public boolean loaded;

    public Context() {
        assets = new AssetManager();
        assets.load(ATLAS, TextureAtlas.class);
        assets.finishLoading();

        sb = new SpriteBatch();

        sm = new ScreenManager(new PlayScreen(this));
    }

    public TextureRegion getImage(String key) {
        TextureRegion region = assets.get(ATLAS, TextureAtlas.class).findRegion(key);
        if (region == null) throw new IllegalArgumentException("image " + key + " not found");
        return region;
    }

    public TextureRegion getPixel() {
        return getImage("pixel");
    }

    public void dispose() {
        sb.dispose();
    }

}
