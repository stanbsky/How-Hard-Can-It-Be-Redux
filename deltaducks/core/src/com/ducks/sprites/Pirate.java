package com.ducks.sprites;

import com.badlogic.gdx.math.Vector2;
import com.ducks.DeltaDucks;
import com.ducks.components.ShipAnimation;
import com.ducks.entities.ListOfEnemyBullets;
import com.ducks.scenes.Hud;
import com.ducks.tools.InputParser;

public class Pirate extends Ship {

    private final float inputStickinessThreshold = 0.03f;
    private final float inputDurationThreshold = 0.7f;
    private float inputDurationRoll = 0f;

    private ListOfEnemyBullets enemyBullets;

    public final float SHOOT_WAIT_TIME = 1f;
    public float shootTimer ;

    public Pirate(String college, float spawn_x, float spawn_y, ListOfEnemyBullets enemyBullets) {
        super();
        mask = DeltaDucks.BIT_PLAYER | DeltaDucks.BIT_PIRATES | DeltaDucks.BIT_LAND | DeltaDucks.BIT_BOUNDARY;
        category = DeltaDucks.BIT_PIRATES;
        this.enemyBullets = enemyBullets;

        x = spawn_x - width / 2;
        y = spawn_y - height / 2;
        acceleration = 2f;
        max_velocity = 8f;

        // Set up ShipAnimation
        direction = 6;
        moving = false;
        animation = new ShipAnimation(college, new Vector2(x, y), radius*scale, 0.5f);
        data = "Pirate";

        defineShip();
    }

    public void update(float deltaTime) {
        shootTimer += deltaTime;
        // Roll on whether we need to pull a new random input direction
        if (Math.random() < inputStickinessThreshold)
            parseDirection(InputParser.fakeInput(0.3f));
        // Roll on whether we keep applying force in the given direction
        if (inputDurationRoll + Math.random() * 0.1 < inputDurationThreshold)
            applyForce();
        else
            inputDurationRoll = 0f;
        enemyBullets.spawnCannon(this);
        animation.update(deltaTime, getPosition(), direction, false);
        super.update();
    }

    public void dispose() {
        rigidBody.dispose();
        Hud.addGold(100);
        Hud.addScore(1000);
    }
}
