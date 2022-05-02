package com.ducks.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.ducks.entities.Entity;
import com.ducks.managers.EntityManager;
import com.ducks.managers.PowerupManager;
import com.ducks.tools.IShooter;

public class Shooter {

    private final float shootWaitTime;
    private float shootTimer;
    private boolean randomize = false;

    public Shooter(float shootWaitTime) {
        this.shootWaitTime = shootWaitTime;
    }

    public void setRandomizeWait() {
        randomize = true;
    }

    public boolean ready() { return shootTimer >= shootWaitTime; }

    public void resetShootTimer() { shootTimer = 0; }

    public void skipShootTimer() { shootTimer = shootWaitTime; }

    /**
     * If the shooting cooldown is over, shoots a bullet
     */
    public void playerShoots() {
        if (ready() && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            resetShootTimer();
            if (PowerupManager.hotshotActive()) { PowerupManager.hotshotUsed(); }
            EntityManager.spawnBullet();
        }
    }

    public void update(float deltaTime) {
        shootTimer += deltaTime * randomFactor();
    }

    private double randomFactor() {
        return randomize ? ((Math.random() * 0.5)+0.5) : 1d;
    }

    /**
     * Gets vector from a to b
     * @param a start
     * @param b finish
     * @return resultant normalised vector
     */
    public static Vector2 getDirection(IShooter a, Entity b) {
        Vector2 body = a.getPosition();
        Vector2 target = b.getPosition();
        return new Vector2(body.x - target.x, body.y - target.y).nor().scl(-1);
    }
}
