package com.ducks.managers;

public final class PowerupManager {
    private static int shields;
    private static int multishotTime;
    private static int hotshots;
    private static int quickshotTime;
    private static int supersizeTime;

    public static void PowerupManager () {
        shields = 0;
        multishotTime = 0;
        hotshots = 0;
        quickshotTime = 0;
        supersizeTime = 0;
    }

    public static void update(float deltaTime) {
        if (quickshotTime > 0) {
            quickshotTime -= 1;
            System.out.println(quickshotTime);
        }
        if (multishotTime > 0) {
            multishotTime -= 1;
            System.out.println(multishotTime);
        }
        if (supersizeTime > 0) {
            supersizeTime -= 1;
            System.out.println(supersizeTime);
        }
    }

    public static void newPowerup (String powerup) {
        switch (powerup) {
            case "quickfire":
                quickshotTime += 500;
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
                hotshots += 5;
                break;
        }
    }

    public static boolean shieldAcitve () { return shields > 0; }

    public static boolean multishotAcitve () { return multishotTime > 0; }

    public static boolean hotshotAcitve () { return hotshots > 0; }

    public static boolean quickshotAcitve () { return quickshotTime > 0; }

    public static boolean supersizeAcitve () { return supersizeTime > 0; }
}
