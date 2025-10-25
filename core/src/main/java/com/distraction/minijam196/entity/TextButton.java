package com.distraction.minijam196.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.minijam196.Constants;
import com.distraction.minijam196.Context;
import com.distraction.minijam196.Utils;

public class TextButton extends Entity {

    private final Context context;

    public final TextEntity text;

    private boolean hover;
    private boolean selected;

    private TextureRegion image;

    public TextButton(Context context) {
        this.context = context;
        text = new TextEntity(context.getFont(Context.VCR20), "", x, y, TextEntity.Alignment.CENTER);
        text.setColor(Constants.WHITE);
        updateImage();
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        text.x = x;
        text.y = y - 1;
    }

    public void setHover(boolean hover) {
        this.hover = hover;
        updateImage();
    }

    public void toggleSelected() {
        selected = !selected;
        updateImage();
    }

    private void updateImage() {
        if (selected) image = context.getImage("menuitems");
        else if (hover) image = context.getImage("menuitemh");
        else image = context.getImage("menuitem");
        w = image.getRegionWidth();
        h = image.getRegionHeight();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(1, 1, 1, 1);
        Utils.drawCentered(sb, image, x, y);
        text.render(sb);
    }
}
