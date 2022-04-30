package com.ducks.managers;

public final class PowerupManager {
    private static int shields;
    private static int multishotTime;
    private static int hotshots;
    private static int quickshotTime;
    private static int supersizeTime;

    public PowerupManager () {
        shields = 0;
        multishotTime = 0;
        hotshots = 0;
        quickshotTime = 0;
        supersizeTime = 0;
    }

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
