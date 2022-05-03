package com.ducks.managers;

import com.badlogic.gdx.utils.ArrayMap;
import com.ducks.tools.Triplet;

public class ShopManager {

    private static final ArrayMap<String, Triplet<Integer, String, String>> items = new ArrayMap<>();

    static {
        addItem("quickfire", 1300, "Quick shot", "No cooldown when shooting, click as fast as you can.\n(7s)");
        addItem("shield", 1000, "Shield", "Ship will take no damage upon the next hit, this destroys 1 shield.\n(1x)");
        addItem("spray", 850, "Triple Shot", "Triple shots are fired from the boat!\n(9s)");
        addItem("supersize", 1150, "Super Size", "Ship is giant and won't take damage until it's back to normal.\n(5s)");
        addItem("bullet_hotshot", 900, "Hot Shot", "Flaming bullets fly at supersonic speeds, also traveling much further before hitting the water.\n(15x)");
    }

    /**
     * Sets up new item in the shop
     * @param item id of item
     * @param price of item
     * @param name displayed of item
     * @param description of item
     */
    public static void addItem (String item, Integer price, String name, String description) {
        items.insert(items.size, item, new Triplet<> (price, name, description));
    }

    /**
     * Gets item data
     * @param item id
     * @return item data
     */
    public static Triplet<Integer,String,String> getItem (String item) {
        return items.get(item);
    }

    /**
     * Buys an item my taking money out of coins if there is enough
     * @param item wanted
     */
    public static void buyItem(String item) {
        int itemPrice = items.get(item).x;
        if (StatsManager.getGold() > itemPrice) {
            StatsManager.spendGold(itemPrice);
            PowerupManager.newPowerup(item);
        }
    }
}
