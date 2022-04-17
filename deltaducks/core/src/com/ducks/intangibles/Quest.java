package com.ducks.intangibles;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.ducks.entities.Chest;
import com.ducks.managers.RenderingManager;
import com.ducks.ui.Hud;
import com.ducks.ui.Subtitle;

public class Quest {

    private boolean isCompleted = false;
    private Subtitle subtitle;
    private Chest objective;

    public Quest (String type, Vector2 location, Subtitle subtitle, TextureAtlas atlas) {
        this.subtitle = subtitle;
        objective = new Chest(location, atlas);
        RenderingManager.registerEntity(objective);
        subtitle.setSubtitle("Find the chest at " + location);
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void update(float deltaTime) {
        objective.update(deltaTime);
        if (objective.isCompleted())
            isCompleted = true;
    }

    public void dispose() {
        subtitle.setSubtitle("Well done!");
        Hud.addGold(500);
        Hud.addScore(1000);
    }
}
