package com.distraction.minijam196.entity;

import static com.distraction.minijam196.Constants.HEIGHT;
import static com.distraction.minijam196.Constants.WIDTH;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.minijam196.Context;

import java.util.ArrayList;
import java.util.List;

public class Menu extends Entity {

    private final List<MenuItem> menuItems;

    public Menu(Context context, Snake snake) {
        x = WIDTH / 2f;
        y = HEIGHT / 2f;

        MenuItem title = new MenuItem(context, MenuItem.MenuItemType.TITLE);
        title.text.setText("Energy 5");
        MenuItem move = new MenuItem(context, MenuItem.MenuItemType.ITEM);
        move.text.setText("Move");
        MenuItem attack = new MenuItem(context, MenuItem.MenuItemType.ITEM);
        attack.text.setText("Attack");

        menuItems = new ArrayList<>();
        menuItems.add(title);
        menuItems.add(move);
        menuItems.add(attack);

        updateMenuItems();
    }

    private void updateMenuItems() {
        float sumy = y;
        for (MenuItem item : menuItems) {
            item.setPosition(x, sumy);
            sumy -= 25;
        }
    }

    public void onMouseMove(float mx, float my) {
        for (MenuItem item : menuItems) {
            item.setHover(item.contains(mx, my));
        }
    }

    public void onMouseClick(float mx, float my) {
        for (MenuItem item : menuItems) {
            if (item.contains(mx, my)) item.toggleSelected();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(1, 1, 1, 1);
        for (MenuItem item : menuItems) item.render(sb);
    }
}
