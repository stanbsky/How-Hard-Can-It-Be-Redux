package com.ducks.managers;

import com.ducks.tools.Saving.ISaveData;
import com.ducks.tools.Saving.StatsSaveData;
import com.ducks.ui.PauseMenu;

public final class StatsManager {

    private static int worldTimer = 300;
    private static float timeProgress = 0;
    private static int score = 0;
    private static int gold = 0;
    private static int goldEarned = 0;

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

    public static int getTotalEarnedGold() { return goldEarned; }

    public static void addGold(int gold) {
        StatsManager.gold += gold;
        StatsManager.goldEarned += gold;
        PauseMenu.updateGold();
    }

    public static void spendGold(int gold) {
        StatsManager.gold -= gold;
        PauseMenu.updateGold();
    }

    public static void reset() {
        worldTimer = 300;
        timeProgress = score = gold = goldEarned = 0;
    }

    public static void update(float deltaTime) {
        timeProgress += deltaTime;
        updateWorldTimer();
    }

    public static ISaveData Save() {
        StatsSaveData save = new StatsSaveData();
        save.gold = gold;
        save.xp = score;
        save.time = worldTimer;
        return save;
    }

    public static void Load(ISaveData data) {
        StatsSaveData save = (StatsSaveData) data;
        setGold(save.gold);
        setScore(save.xp);
        setWorldTimer(save.time);
    }

    public static void setGold(int gold) {
        StatsManager.gold = gold;
    }
    public static void setScore(int score) {
        StatsManager.score = score;
    }
    public static void setWorldTimer(int time) {
        StatsManager.worldTimer = time;
    }
}
