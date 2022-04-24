package com.ducks.intangibles;

import com.badlogic.gdx.math.Vector2;
import com.ducks.entities.Chest;
import com.ducks.entities.College;
import com.ducks.entities.Entity;
import com.ducks.entities.Pirate;
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
            registerIndicator(type);
        } else if (Objects.equals(type, "pirate")) {
            objective = EntityManager.pirates.random();
            ((Pirate) objective).setAngry(true);
            description = "Defeat the angry pirate!";
            registerIndicator("warning");

        } else if (Objects.equals(type, "college")) {
            objective = EntityManager.colleges.random();
            description = "Destroy the marked college!";
            indicator = ((College) objective).getIndicator();
            indicator.setAngry(true);
        }
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

    private void registerIndicator(String texture) {
        indicator = new Indicator(objective, texture, 15f);
        EntityManager.registerEntity(indicator);
    }

    public void dispose() {
        indicator.dispose();
        objective.dispose();
        subtitle.setSubtitle("Well done!");
        Hud.addGold(500);
        Hud.addScore(1000);
    }
}
