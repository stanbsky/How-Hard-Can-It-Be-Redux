package com.ducks.managers;

import com.ducks.ui.TablePauseMenu;

public final class StatsManager {

    private static int worldTimer = 300;
    private static float timeProgress = 0;
    private static int score = 0;
    private static int gold = 0;

    public static int getWorldTimer() {
        return worldTimer;
    }

    private static void updateWorldTimer() {
        if (timeProgress > 1) {
            timeProgress = 0;
            worldTimer--;
        }
    }

    public static int getScore() {
        return score;
    }

    public static void addScore(int score) {
        StatsManager.score += score;
    }

    public static int getGold() {
        return gold;
    }

    public static void addGold(int gold) {
        StatsManager.gold += gold;
        TablePauseMenu.updateGold();
    }

    public static void update(float deltaTime) {
        timeProgress += deltaTime;
        updateWorldTimer();
    }
}
