package com.distraction.minijam196.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

abstract class Entity {

    public float x;
    public float y;
    public float dx;
    public float dy;

    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);

}
