package com.ducks.managers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.ducks.entities.Boss;
import com.ducks.entities.Player;
import com.ducks.intangibles.DifficultyControl;
import com.ducks.intangibles.Quest;
import com.ducks.screens.MainGameScreen;
import com.ducks.tools.Saving.ISaveData;
import com.ducks.tools.Saving.QuestSaveData;

import java.util.Objects;

import static com.ducks.DeltaDucks.PIXEL_PER_METER;
import static com.ducks.managers.EntityManager.livingCollegesExist;
import static com.ducks.managers.EntityManager.livingPiratesExist;
import static com.ducks.screens.MainGameScreen.player;

public final class QuestManager {

    private static Quest currentQuest;
    private static float stateTime;
    private static float spawnTime = 2;
    private static int finalQuestCounter = DifficultyControl.getValue(4, 6, 9);
    private static boolean finalQuestCompleted = false;
    private static int finishedQuests = 0;
    private static boolean debug = false;

    public static void Initialise() {
        currentQuest = null;
        if(SaveManager.LoadSave) {
            if(SaveManager.saveData.quests.hasBoss) {
                currentQuest = new Quest("boss", SaveManager.saveData.quests.position, SaveManager.saveData.quests.bossCollege);
                Load(SaveManager.saveData.quests);
            }
        }
    }

    private static Vector2 pickSpawn(Array<Vector2> spawns) {
        if (debug)
            return player.getPosition().scl(100f).add(300,-300);
        int counter = 0;
        while (true) {
            counter++;
            Vector2 spawn = spawns.random();
            // Check if too close to player
            if (spawn.dst2(player.getPosition().scl(100f)) > 500) {
                spawns.removeValue(spawn, false);
                return spawn;
            }
            // Couldn't find a far enough spawn, just return anything!
            if (counter > 100)
                return spawn;
        }
    }

    private static void spawnQuest() {
        // TODO: revert after testing
        if (finishedQuests == finalQuestCounter) {
//        if (true) {
            currentQuest = new Quest("boss", null, "");
        } else {
            float spawnRoll = (float) Math.random();
            if (spawnRoll < 0.4f && livingCollegesExist()) {
                currentQuest = new Quest("college", EntityManager.colleges.random().getPosition(), "");
            } else if (spawnRoll > 0.7f && livingPiratesExist()) {
                currentQuest = new Quest("pirate", null, "");
            } else {
                currentQuest = new Quest("chest", pickSpawn(EntityManager.chestSpawns), "");
            }
        }
    }

    private static void checkQuestCompletion() {
        if (currentQuest.isCompleted()) {
            if (currentQuest.type == "boss") {
                finalQuestCompleted = true;
                // TODO: return here stops crash on game over, see
                //  https://github.com/stanbsky/How-Hard-Can-It-Be-Redux/issues/33#issue-1218051196
                return;
            }
            finishedQuests++;
            currentQuest.dispose();
            currentQuest = null;
        }
    }

    public static void checkForGameOver(MainGameScreen gameScreen) {
        // Out of time
        if (StatsManager.getWorldTimer() <= 0)
            gameScreen.gameOver(false);
        // Player died
        if (Player.getHealth() <= 0f)
            gameScreen.gameOver(false);
        // Boss quest finished
        if (finalQuestCompleted)
            gameScreen.gameOver(true);
    }

    public static void update(float deltaTime) {
        if (currentQuest == null) {
            stateTime += deltaTime;
        } else {
            currentQuest.update(deltaTime);
            checkQuestCompletion();
        }
        if (stateTime > spawnTime) {
            spawnQuest();
            stateTime = 0;
        }
    }


    public static ISaveData Save() {
        QuestSaveData save = new QuestSaveData();
        save.finishedQuests = finishedQuests;
        if(currentQuest != null) {
            save.hasBoss = Objects.equals(currentQuest.type, "boss");
            save.position = currentQuest.getPosition().scl(PIXEL_PER_METER);
            save.bossCollege = currentQuest.getCollage();
        }
        return save;
    }

    public static void Load(ISaveData data) {
        QuestSaveData save = (QuestSaveData) data;
        finishedQuests = save.finishedQuests;
        currentQuest.setBossHealth(save.bossHealth);
    }
}
