package com.ducks.intangibles;

import com.badlogic.gdx.math.Vector2;
import com.ducks.entities.Chest;
import com.ducks.entities.Entity;
import com.ducks.managers.EntityManager;
import com.ducks.ui.Hud;
import com.ducks.ui.Indicator;
import com.ducks.ui.Subtitle;

import java.util.Objects;

public class Quest {

    private boolean isCompleted = false;
    private Subtitle subtitle;
    private Entity objective;
    private String description;
    private Indicator indicator;

    public Quest (String type, Vector2 location, Subtitle subtitle) {
        this.subtitle = subtitle;
        if (Objects.equals(type, "chest")) {
            objective = new Chest(location);
            EntityManager.registerEntity(objective);
            description = "Open the chest";
        } else if (Objects.equals(type, "pirate")) {
            type = "warning"; // to spawn an Indicator with warning sign
            objective = EntityManager.pirates.random();
            description = "Defeat the angry pirate!";
        }
        indicator = new Indicator(objective, type, 15f);
        EntityManager.registerEntity(indicator);
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void update(float deltaTime) {
        subtitle.setSubtitle(description);
        if (!objective.isAlive()) {
            isCompleted = true;
            return;
        }
        // TODO: if a pirate quest is spawned, the pirate will have
        // TODO: twice the update rolls. This is a feature?
        objective.update(deltaTime);
    }

    public void dispose() {
        indicator.dispose();
        subtitle.setSubtitle("Well done!");
        Hud.addGold(500);
        Hud.addScore(1000);
    }
}
