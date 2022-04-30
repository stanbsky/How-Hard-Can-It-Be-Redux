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

    public static Vector2 getDirection(IShooter a, Entity b) {
        //TODO: vector subtraction is not the same as subtracting x and y values??
        //return a.getPosition().sub(b.getPosition()).nor().scl(-1);
        Vector2 body = a.getPosition();
        Vector2 target = b.getPosition();
        return new Vector2(body.x - target.x, body.y - target.y).nor().scl(-1);
    }
}
