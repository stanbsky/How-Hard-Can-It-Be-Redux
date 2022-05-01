package com.ducks.intangibles;

import com.badlogic.gdx.math.Vector2;
import com.ducks.entities.*;
import com.ducks.managers.EntityManager;
import com.ducks.managers.SaveManager;
import com.ducks.managers.StatsManager;
import com.ducks.ui.Indicator;
import com.ducks.ui.Hud;

import java.util.Objects;

import static com.ducks.managers.EntityManager.*;
import static com.ducks.ui.Hud.subtitle;


public class Quest {

    private boolean isCompleted = false;
    private Entity objective;
    private String description;
    private Indicator indicator;
    public String type;

    public Quest (String type) {
        this(type, null, "");
    }

    public Quest (String type, Vector2 location, String collageName) {
        this.type = type;
        switch (type) {
            case "chest":
                objective = new Chest(location);
                registerEntity(objective);
                Hud.subtitle.setQuestNotice("Open the ", "chest", " chest");
                registerIndicator(type);
                break;
            case "pirate":
                objective = pirates.random();
                ((Pirate) objective).setAngry(true);
                Hud.subtitle.setQuestNotice("Defeat the ", "warning", " angry pirate!");
                registerIndicator("warning");
                break;
            case "college":
                objective = colleges.random();
                Hud.subtitle.setQuestNotice("Destroy the ", ((College)objective).name, " marked college!");
                indicator = ((College) objective).getIndicator();
                indicator.setAngry(true);
                break;
            case "boss":
                Vector2 spawn = null;
                if(!SaveManager.LoadSave) {
                    spawn = pirateSpawns.random();
                }
                if(location != null) {
                    spawn = location.cpy();
                }
                objective = new Boss(Objects.equals(collageName, "") ? collegeNames.random() : collageName,
                        spawn);
                registerEntity(objective);
                Hud.subtitle.setQuestNotice("Defeat the ", "warning", " Pirate Boss!");
                registerIndicator("warning");
                indicator.setAngry(true);
                break;
        }
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public String getCollage() {
        try {
            return ((Boss) objective).collegeName;
        }
        catch (Exception e) {
            return "";
        }
    }
    public void update(float deltaTime) {
        if (!objective.isAlive()) {
            isCompleted = true;
            return;
        }
        // Note: if a pirate or boss quest is spawned, they will have
        //  twice the update rolls. This is a feature.
        objective.update(deltaTime);
    }

    private void registerIndicator(String texture) {
        indicator = new Indicator(objective, texture, 15f);
        registerEntity(indicator);
    }

    public void dispose() {
        indicator.dispose();
//        objective.dispose(); This stalls the game indefinitely
        subtitle.setNotice("Well done!");
        StatsManager.addGold(500);
        StatsManager.addScore(1000);
    }

    public Vector2 getPosition(){
        return objective.getPosition();
    }

    public void setBossHealth(int health) {
        if(type == "Boss") {
            ((Boss) objective).setHealth(health);
        }
    }
}
