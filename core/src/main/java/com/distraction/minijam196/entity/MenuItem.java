package com.distraction.minijam196.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.minijam196.Constants;
import com.distraction.minijam196.Context;
import com.distraction.minijam196.Utils;

public class MenuItem extends Entity {

    public enum MenuItemType {
        TITLE,
        ITEM
    }

    private final Context context;

    public final TextEntity text;

    public boolean enabled;
    private boolean hover;
    private boolean selected;

    private final MenuItemType type;
    private TextureRegion image;

    private float desty;

    public MenuItem(Context context, MenuItemType type) {
        this.context = context;
        this.type = type;
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
        if (type == MenuItemType.ITEM) {
            this.hover = hover;
            updateImage();
        }
    }

    public void toggleSelected() {
        if (type == MenuItemType.ITEM) {
            selected = !selected;
            updateImage();
        }
    }

    private void updateImage() {
        if (selected) image = context.getImage("menuitems");
        else if (hover) image = context.getImage("menuitemh");
        else if (type == MenuItemType.TITLE) image = context.getImage("menutitle");
        else image = context.getImage("menuitem");
        w = image.getRegionWidth();
        h = image.getRegionHeight();
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(1, 1, 1, 1);
        Utils.drawCentered(sb, image, x, y);
        text.render(sb);
    }
}
