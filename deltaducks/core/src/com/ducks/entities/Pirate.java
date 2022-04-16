package com.ducks.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.ducks.components.ShipAnimation;
import com.ducks.intangibles.EntityData;
import com.ducks.managers.ListOfEnemyBullets;
import com.ducks.ui.Hud;
import com.ducks.tools.InputParser;
import static com.ducks.tools.FixtureFilter.*;

public class Pirate extends Ship {

    private final float inputStickinessThreshold = 0.03f;
    private final float inputDurationThreshold = 0.7f;
    private float inputDurationRoll = 0f;

    public final float SENSOR_SCALE = 5f;

    private final ListOfEnemyBullets enemyBullets;

    public final float SHOOT_WAIT_TIME = 1f;
    public float shootTimer ;
    private boolean playerInRange = false;

    public Pirate(String college, float spawn_x, float spawn_y, ListOfEnemyBullets enemyBullets) {
        super();
        mask = MASK_ALL - ENEMY_BULLET;
        category = ENEMY;
        this.enemyBullets = enemyBullets;

        x = spawn_x - width / 2;
        y = spawn_y - height / 2;
        acceleration = 2f;
        max_velocity = 8f;

        // Set up ShipAnimation
        direction = 6;
        moving = false;
        animation = new ShipAnimation(college, new Vector2(x, y), radius*scale, 0.5f);
        data = new EntityData(category);

        defineShip();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        shootTimer += ((Math.random() * 0.5)+0.5) * deltaTime;
        // Roll on whether we need to pull a new random input direction
        if (Math.random() < inputStickinessThreshold)
            parseDirection(InputParser.fakeInput(0.3f));
        // Roll on whether we keep applying force in the given direction
        if (inputDurationRoll + Math.random() * 0.1 < inputDurationThreshold)
            applyForce();
        else
            inputDurationRoll = 0f;
        if (playerInRange)
            enemyBullets.spawnBullet(this);
//        if(rigidBody.getSensorData().contains("Attack")) {
//            enemyBullets.spawnBullet(this);
//        }
        animation.update(deltaTime, getPosition(), direction, false);
    }

    @Override
    protected void handleContact(Fixture contactor) {
        if (EntityData.equals(contactor, PLAYER_BULLET))
            isAlive = false;
    }

    @Override
    protected void handleSensorContact(Fixture contactor) {
        if (EntityData.equals(contactor, PLAYER))
            playerInRange = !playerInRange;
    }

    @Override
    public void defineShip() {
        super.defineShip();

        CircleShape shape = new CircleShape();
        shape.setRadius(radius * SENSOR_SCALE);
        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;
        fixture.filter.categoryBits = category;
        fixture.filter.maskBits = PLAYER;
        rigidBody.addSensor(fixture, category,"Pirate Ship Sensor");
    }

    public void dispose() {
        rigidBody.dispose();
        Hud.addGold(100);
        Hud.addScore(1000);
    }
}
