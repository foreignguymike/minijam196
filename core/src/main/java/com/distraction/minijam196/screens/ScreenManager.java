package com.distraction.minijam196.screens;

import java.util.Stack;

public class ScreenManager extends Stack<Screen> {

    public ScreenManager(Screen screen) {
        push(screen);
    }

    public void replace(Screen s) {
        pop();
        push(s);
    }

    @Override
    public synchronized Screen pop() {
        Screen s = super.pop();
        if (!isEmpty()) peek().resume();
        return s;
    }

    public void resize(int width, int height) {
        peek().resize(width, height);
    }

    public void input() {
        peek().input();
    }

    public void update(float dt) {
        peek().update(dt);
    }

    public void render() {
        int firstNonTransparent = 0;
        for (int i = size() - 1; i >= 0; i--) {
            if (!get(i).transparent) {
                firstNonTransparent = i;
                break;
            }
        }
        for (; firstNonTransparent < size(); firstNonTransparent++) {
            get(firstNonTransparent).render();
        }
    }

}
