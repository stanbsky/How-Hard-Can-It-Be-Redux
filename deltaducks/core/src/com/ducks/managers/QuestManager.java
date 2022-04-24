package com.ducks.managers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.entities.Pirate;
import com.ducks.intangibles.Quest;
import com.ducks.ui.Subtitle;

import java.util.ArrayList;
import java.util.Random;

import static com.ducks.screens.MainGameScreen.map;
import static com.ducks.screens.MainGameScreen.player;

public class QuestManager {

    private final Subtitle subtitle;
    private ArrayList<Vector2> spawnLocations;
    private Quest currentQuest;
    private float stateTime;
    private float spawnTime = 4;
    private int finalQuestCounter = 5;
    private boolean debug = true;

    public QuestManager(Subtitle subtitle) {
        this.subtitle = subtitle;
        this.currentQuest = null;
        spawnLocations = new ArrayList<>();
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            spawnLocations.add(new Vector2(rect.getX() * DeltaDucks.TILEED_MAP_SCALE, rect.getY() * DeltaDucks.TILEED_MAP_SCALE));
        }
    }

    private Vector2 pickSpawn() {
        if (debug)
            return player.getPosition().scl(100f).add(300,-300);
        Vector2 spawn = null;
        for (Vector2 pos : spawnLocations) {
            // Check if too close to player
            if (pos.dst2(player.getPosition().scl(100f)) < 500)
                continue;
            if(Math.random() > .3f)
                continue;
            spawn = pos;
        }
        return spawn;
    }

    private Vector2 pickChestSpawn() {
        Array<Vector2> spawns = EntityManager.getListOfSpawns("chests");
        Random generator = new Random();
        int randomIndex = generator.nextInt(spawns.size);
        return spawns.get(randomIndex);
    }

    private void spawnQuest() {
        currentQuest = new Quest("chest", pickChestSpawn(), subtitle);
    }


    private void checkQuestCompletion() {
        if (currentQuest.isCompleted()) {
            currentQuest.dispose();
            currentQuest = null;
        }
    }

    public void update(float deltaTime) {
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
