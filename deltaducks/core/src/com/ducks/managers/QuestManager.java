package com.ducks.managers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.entities.Player;
import com.ducks.intangibles.DifficultyControl;
import com.ducks.intangibles.Quest;
import com.ducks.screens.FinalStorylineScreen;
import com.ducks.screens.MainGameScreen;
import com.ducks.ui.Subtitle;

import java.util.Random;

import static com.ducks.managers.EntityManager.livingCollegesExist;
import static com.ducks.managers.EntityManager.livingPiratesExist;
import static com.ducks.screens.MainGameScreen.player;

public class QuestManager {

    private final Subtitle subtitle;
    private Quest currentQuest;
    private float stateTime;
    private float spawnTime = 4;
    private int finalQuestCounter = DifficultyControl.getValue(4, 6, 9);
    private boolean finalQuestCompleted = false;
    private int finishedQuests = 0;
    private boolean debug = false;

    public QuestManager(Subtitle subtitle) {
        this.subtitle = subtitle;
        this.currentQuest = null;
    }

    private Vector2 pickSpawn(Array<Vector2> spawns) {
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

    private void spawnQuest() {
        float spawnRoll = (float) Math.random();
        if (spawnRoll < 0.4f && livingCollegesExist()) {
            currentQuest = new Quest("college", EntityManager.colleges.random().getPosition(), subtitle);
        } else if (spawnRoll > 0.7f && livingPiratesExist()) {
            currentQuest = new Quest("pirate", null, subtitle);
        } else {
            currentQuest = new Quest("chest", pickSpawn(EntityManager.chestSpawns), subtitle);
        }

    }

    private void checkQuestCompletion() {
        if (currentQuest.isCompleted()) {
            finishedQuests++;
            currentQuest.dispose();
            currentQuest = null;
        }
    }

    public void checkForGameOver(MainGameScreen gameScreen) {
        // Out of time
        if (StatsManager.getWorldTimer() <= 0)
            gameScreen.gameOver("Lost");
        // Player died
        if (Player.getHealth() <= 0f)
            gameScreen.gameOver("Lost");
        // Boss quest finished
        if (finalQuestCompleted)
            gameScreen.gameOver("Won");
    }

    public void update(float deltaTime) {
        if (finishedQuests >= finalQuestCounter) {
            subtitle.setSubtitle("You've finished all quests");
            return;
        }
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
}
