package com.ducks.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.ducks.screens.MainGameScreen;

public class HealthBar extends Texture {
    private final float maxHP;
    private float hp;

    public HealthBar(float x, float y, float width, float height, boolean horizontal, float hp) {
        frame = MainGameScreen.getAtlas().findRegion("blank");
        this.maxHP = this.hp = hp;
        this.x = x;
        this.y = y;
        if (!horizontal) {
            float temp = width;
            width = height;
            height = temp;
        }
        this.width = width;
        this.height = height;
    }

    public void update(float hp) {
        this.hp = hp;
    }

    public void render(SpriteBatch batch) {
        if (this.hp <= 0f)
            return;

//        batch.begin();
        if (hp > .6f)
            batch.setColor(Color.GREEN);
        else if (hp > .2f)
            batch.setColor(Color.ORANGE);
        else
            batch.setColor(Color.RED);
        batch.draw(frame, x, y, width*(hp/maxHP), height);
//        System.out.println(x + "," + y + "," + width*(hp/maxHP) + "," + height);
        batch.setColor(Color.WHITE);
//        batch.end();
    }
}
