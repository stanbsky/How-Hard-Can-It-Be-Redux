package com.ducks.managers;

import com.badlogic.gdx.utils.ArrayMap;
import com.ducks.tools.Triplet;
import com.ducks.ui.Hud;

public class ShopManager {

    private static final ArrayMap<String, Triplet<Integer, String, String>> items = new ArrayMap<>();

    static {
        addItem("quickfire", 500, "Quick shot", "");
        addItem("shield", 300, "Shield", "");
        addItem("spray", 350, "Triple Shot", "");
        addItem("supersize", 350, "Super Size", "");
        addItem("bullet_hotshot", 400, "Hot Shot", "");
    }

    public static void addItem (String item, Integer price, String name, String description) {
        items.insert(items.size, item, new Triplet<> (price, name, description));
    }

    public static Triplet<Integer,String,String> getItem (String item) {
        return items.get(item);
    }

    public static void buyItem(String item) {
        int itemPrice = items.get(item).x;
        if (Hud.getGold() > itemPrice) {
            Hud.addGold(-itemPrice);
            PowerupManager.newPowerup(item);
        }
    }
}
