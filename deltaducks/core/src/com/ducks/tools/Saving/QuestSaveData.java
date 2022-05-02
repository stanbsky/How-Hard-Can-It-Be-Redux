package com.ducks.tools.Saving;

import com.badlogic.gdx.math.Vector2;

/**
 * The data saved for QuestManager
 */
public class QuestSaveData implements ISaveData {
    public int finishedQuests;
    public boolean hasBoss;
    // boss pos or chest pos
    public Vector2 position;
    public int bossHealth;
    public String bossCollege;

}
