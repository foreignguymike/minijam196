package com.distraction.minijam196;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import java.util.List;

public class Utils {

    public static void drawCentered(SpriteBatch sb, TextureRegion image, float x, float y) {
        float w = image.getRegionWidth();
        float h = image.getRegionHeight();
        sb.draw(image, x - w / 2f, y - h / 2f, w, h);
    }

    public static void drawCenteredScaled(SpriteBatch sb,TextureRegion image, float x, float y, float scale) {
        float w = image.getRegionWidth() * scale;
        float h = image.getRegionHeight() * scale;
        sb.draw(image, x - w / 2f, y - h / 2f, w, h);
    }

    public static void drawRotated(SpriteBatch sb,  TextureRegion image, float x, float y, float rad) {
        sb.draw(
            image,
            x - image.getRegionWidth() / 2f,
            y - image.getRegionHeight() / 2f,
            image.getRegionWidth() / 2f,
            image.getRegionHeight() / 2f,
            image.getRegionWidth(),
            image.getRegionHeight(),
            1f,
            1f,
            MathUtils.radDeg * rad
        );
    }

    public static <T> void swap(List<T> list, int index1, int index2) {
        T temp = list.get(index1);
        list.set(index1, list.get(index2));
        list.set(index2, temp);
    }

}
