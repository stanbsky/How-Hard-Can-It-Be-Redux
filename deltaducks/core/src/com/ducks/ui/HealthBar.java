package com.ducks.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.ducks.entities.Player;

import static com.ducks.managers.AssetManager.button_up;
import static com.ducks.managers.AssetManager.ui;

public class HealthBar extends Table {
    private Image health;
    private final int FULL_WIDTH = 30;
    private final int FULL_HEIGHT = 284;
    public HealthBar() {
        setBackground(button_up);
//        this.setDebug(true);
        health = new Image(ui.newDrawable("blank", new Color(0,1,0,0.3f)));
        this.add(health).width(FULL_WIDTH).height(FULL_HEIGHT);
        // Sizing as a percentage of table size, left here for reference
//                .width(Value.percentWidth(1f, this))
//                .height(Value.percentHeight(1f,this))
    }

    public void draw(Batch batch, float parentAlpha) {
        updateHPBarSize();
        super.draw(batch, parentAlpha);
    }

    private void updateHPBarSize() {
        health.setHeight(FULL_HEIGHT * Player.getHealthPercentage());
    }
}
