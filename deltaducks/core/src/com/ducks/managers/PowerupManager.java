package com.ducks.managers;

import com.ducks.entities.Player;

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
                quickshotTime += 400;
                break;
            case "shield":
                shields += 1;
                break;
            case "spray":
                multishotTime += 500;
                break;
            case "supersize":
                supersizeTime += 500;
                break;
            case "bullet_hotshot":
                hotshots += 15;
                break;
        }
    }

    public static boolean shieldAcitve () { return shields > 0; }

    public static void shieldUsed () { shields -= 1; }

    public static boolean hotshotAcitve () { return hotshots > 0; }

    public static void hotshotUsed () { hotshots -= 1; }

    public static boolean multishotAcitve () { return multishotTime > 0; }

    public static boolean quickshotAcitve () { return quickshotTime > 0; }

    public static boolean supersizeAcitve () { return supersizeTime > 0; }
}
