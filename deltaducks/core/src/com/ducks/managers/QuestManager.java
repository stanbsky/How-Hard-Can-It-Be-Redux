package com.ducks.managers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ducks.DeltaDucks;
import com.ducks.intangibles.Quest;
import com.ducks.ui.Subtitle;

import java.util.ArrayList;

public class QuestManager {

    private final TextureAtlas atlas;
    private final Subtitle subtitle;
    private ArrayList<Vector2> spawnLocations;
    private Vector2 playerLocation;
    private Quest currentQuest = null;
    private float stateTime;
    private float spawnTime = 4;
    private int finalQuestCounter = 5;
    private boolean debug = true;

    public QuestManager(TiledMap map, TextureAtlas atlas, Subtitle subtitle) {
        this.atlas = atlas;
        this.subtitle = subtitle;
        spawnLocations = new ArrayList<>();
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            spawnLocations.add(new Vector2(rect.getX() * DeltaDucks.TILEED_MAP_SCALE, rect.getY() * DeltaDucks.TILEED_MAP_SCALE));
        }
    }

    private Vector2 pickSpawn() {
        if (debug)
            return playerLocation.add(300,-300);
        Vector2 spawn = null;
        for (Vector2 pos : spawnLocations) {
            // Check if too close to player
            if (pos.dst2(playerLocation) < 500)
                continue;
            if(Math.random() > .3f)
                continue;
            spawn = pos;
        }
        return spawn;
    }

    private void spawnQuest() {
        currentQuest = new Quest("pirate", pickSpawn(), subtitle, atlas);
    }

    private void checkQuestCompletion() {
        if (currentQuest.isCompleted()) {
            currentQuest.dispose();
            currentQuest = null;
        }
    }

    public void update(float deltaTime, Vector2 playerLocation) {
        this.playerLocation = playerLocation.scl(100f);
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
