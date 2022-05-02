package com.ducks.tools.Saving;

/**
 * The data to be serialized into json and saved
 */
public class SaveData implements ISaveData {
    public PlayerSaveData player;
    public StatsSaveData stats;
    public int difficulty;
    public int[] powerUps;
    public QuestSaveData quests;
    public EntitiesSaveData entityManager;
    public SaveData() {
        difficulty = 0;
        stats = new StatsSaveData();
        player = new PlayerSaveData();
        powerUps = new int[]{0, 0, 0, 0, 0};
        quests = new QuestSaveData();
    }

}
