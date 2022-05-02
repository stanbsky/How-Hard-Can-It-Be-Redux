package com.ducks.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.ducks.tools.Debug;

import java.util.Vector;

import static com.ducks.DeltaDucks.scl;
import static com.ducks.DeltaDucks.batch;
import static com.ducks.managers.AssetManager.atlas;

public class HealthBar extends Texture {
    private final float maxHP;
    private final boolean horizontal;
    private float hp;

    /**
     * Instantiates the health for the colleges and boss
     * @param x location
     * @param y location
     * @param width size
     * @param height size
     * @param horizontal display direction
     * @param hp amount of starting hp
     * @param ui whether or not to scale the bar before displaying
     */
    public HealthBar(float x, float y, float width, float height, boolean horizontal, float hp, boolean ui) {
        frame = atlas.findRegion("blank");
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

    /**
     * Updates the health bar
     * @param hp health to set the bar to
     */
    public void update(float hp) {
        this.hp = hp;
    }

    /**
     * Updates the health bar
     * @param hp health to set the bar to
     * @param pos position of health bar to move to
     */
    public void update(float hp, Vector2 pos) {
        this.hp = hp;
        this.x = pos.x - width/2;
        this.y = pos.y + width/2;
    }

    /**
     * Renders the health bar to screen
     */
    public void render() {
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
