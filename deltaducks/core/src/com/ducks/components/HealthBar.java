package com.ducks.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ducks.screens.MainGameScreen;

import static com.ducks.DeltaDucks.scl;

public class HealthBar extends Texture {
    private final float maxHP;
    private final boolean horizontal;
    private float hp;

    public HealthBar(float x, float y, float width, float height, boolean horizontal, float hp, boolean ui) {
        frame = MainGameScreen.getAtlas().findRegion("blank");
        this.maxHP = this.hp = hp;
        this.horizontal = horizontal;
        if (!ui) {
            x = scl(x);
            y = scl(y);
            width = scl(width);
            height = scl(height);
        }
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void update(float hp) {
        this.hp = hp;
    }

    public void render(SpriteBatch batch) {
        if (this.hp <= 0f)
            return;

        if (hp / maxHP > .6f)
            batch.setColor(Color.GREEN);
        else if (hp / maxHP > .4f)
            batch.setColor(Color.ORANGE);
        else
            batch.setColor(Color.RED);
        if (horizontal)
            batch.draw(frame, x, y, width*(hp/maxHP), height);
        else
            batch.draw(frame, x, y, width, height*(hp/maxHP));
        batch.setColor(Color.WHITE);
    }
}
