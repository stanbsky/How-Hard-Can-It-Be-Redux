package com.ducks.managers;

import com.badlogic.gdx.utils.ArrayMap;
import com.ducks.ui.Hud;

import java.util.Arrays;

public class ShopManager {

    ArrayMap<String, Integer> items = new ArrayMap<>();

    public ShopManager() {
        addItem("quickfire", 500);
        addItem("shield", 300);
        addItem("spray", 350);
        addItem("supersize", 350);
        addItem("bullet_hotshot", 400);
    }

    public void addItem (String item, Integer price) {
        items.insert(items.size, item, price);
    }

    public void buyItem (String item) {
        if (items.containsKey(item)) {
            int itemPrice = items.get(item);
            if (Hud.getGold() > itemPrice) {
                Hud.addGold(-itemPrice);
                PowerupManager.newPowerup(item);
            }
        }
    }
}
