package com.ducks.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.ducks.components.HealthBar;
import com.ducks.components.ShipAnimation;
import com.ducks.components.Shooter;
import com.ducks.intangibles.EntityData;
import com.ducks.managers.EntityManager;
import com.ducks.tools.Debug;

import static com.ducks.tools.FixtureFilter.*;

public class Boss extends Pirate {

    private int health = 20;
    private HealthBar hpBar;
    private float bossShotTimer = 0f;
    private final float bossShotThreshold = 15f;
    public int bossShotCount = 0;

    public Boss(String college, Vector2 spawn) {
        mask = MASK_ALL - ENEMY_BULLET;
        category = ENEMY;
        shootWaitTime = 1f;
        shooter = new Shooter(shootWaitTime);
        radius = radius * 1.5f;

        x = spawn.x - width / 2;
        y = spawn.y - height / 2;
        acceleration = 4f;
        max_velocity = 16f;

        float hpRadius = radius * 100f;
//        System.out.println("Boss x " + x + "radius " + hpRadius);
        hpBar = new HealthBar(x - hpRadius, y + hpRadius,
                hpRadius*2, 10f, true, health, false);

        // Set up ShipAnimation
        direction = 6;
        moving = false;
        animation = new ShipAnimation(college, new Vector2(x, y), radius*scale, 0.5f);
        data = new EntityData(category);

        defineShip();
    }

    @Override
    public void update(float deltaTime) {
        if (playerInRange)
            bossShotTimer += deltaTime;
        super.update(deltaTime);
        hpBar.update(health, getPosition());
    }

    protected void shootBullet() {
        if (bossShotReady() && ready()) {
            bossShotCount--;
            EntityManager.spawnBossShot(this);
        } else {
            super.shootBullet();
        }
    }

    public void draw() {
        super.draw();
        this.hpBar.render();
    }

    @Override
    protected void handleContact(Fixture contactor) {
        if (EntityData.equals(contactor, PLAYER_BULLET))
            health -= 2;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    public boolean bossShotReady() {
        if (bossShotCount > 0) {
            return true;
        }
        if (bossShotTimer > bossShotThreshold) {
            bossShotTimer = 0f;
            bossShotCount = 4;
            return true;
        }
        return false;
    }

    public int getHealth(){
        return health;
    }

}
