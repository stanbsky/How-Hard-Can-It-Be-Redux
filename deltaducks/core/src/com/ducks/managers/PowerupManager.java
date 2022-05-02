package com.ducks.managers;

import com.badlogic.gdx.utils.Array;

public final class PowerupManager {
    private static int shields;
    private static int multishotTime;
    private static int hotshots;
    private static int quickshotTime;
    private static int supersizeTime;

    /**
     * @return list of time left & count of powerups
     */
    public static int[] getPowerUps() {
        int[] powers = {
          shields,
          multishotTime,
          hotshots,
          quickshotTime,
          supersizeTime
        };
        return powers;
    }

    /**
     * Set time left & count of powerups
     * @param powerUps time left & count of powerups
     */
    public static void setPowerUps(int[] powerUps) {
        shields = powerUps[0];
        multishotTime = powerUps[1];
        hotshots = powerUps[2];
        quickshotTime = powerUps[3];
        supersizeTime = powerUps[4];
    }

    /**
     * Set/reset values of powerups to 0, unless loading a save
     */
    public static void Initialise () {
        if(!SaveManager.LoadSave) {
            shields = 0;
            multishotTime = 0;
            hotshots = 0;
            quickshotTime = 0;
            supersizeTime = 0;
        }
    }

    /**
     * Reduce values of time based powerups, if active
     */
    public static void update(float deltaTime) {
        if (quickshotTime > 0) {
            quickshotTime -= 1;
        }
        if (multishotTime > 0) {
            multishotTime -= 1;
        }
        if (supersizeTime > 0) {
            supersizeTime -= 1;
        }
    }

    /**
     * Activate a new powerup
     * @param powerup to activate
     */
    public static void newPowerup (String powerup) {
        switch (powerup) {
            case "quickfire":
                quickshotTime += 421;
                break;
            case "shield":
                shields += 1;
                break;
            case "spray":
                multishotTime += 541;
                break;
            case "supersize":
                supersizeTime += 301;
                break;
            case "bullet_hotshot":
                hotshots += 15;
                break;
        }
    }

    /**
     * Get value of 1 powerup
     * @param powerup to get value of
     * @return time left/ count of powerup
     */
    public static String powerupLeft (String powerup) {
        switch (powerup) {
            case "quickfire":
                return quickshotLeft();
            case "shield":
                return shieldLeft();
            case "spray":
                return multishotLeft();
            case "supersize":
                return supersizeLeft();
            case "bullet_hotshot":
                return hotshotLeft();
            default:
                return "";
        }
    }

    public static boolean shieldActive() { return shields > 0; }

    public static void shieldUsed () { shields -= 1; }

    public static String shieldLeft () { return shields + "x"; }

    public static boolean hotshotActive() { return hotshots > 0; }

    public static void hotshotUsed () { hotshots -= 1; }

    public static String hotshotLeft () { return hotshots + "x"; }

    public static boolean multishotActive() { return multishotTime > 0; }

    public static String multishotLeft () { return (multishotTime/60) + "s"; }

    public static boolean quickshotActive() { return quickshotTime > 0; }

    public static String quickshotLeft () { return (quickshotTime/60) + "s"; }

    public static boolean supersizeActive() { return supersizeTime > 0; }

    public static String supersizeLeft () { return (supersizeTime/60) + "s"; }
}
