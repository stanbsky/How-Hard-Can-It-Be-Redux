package com.ducks.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.ducks.components.ShipAnimation;
import com.ducks.components.Shooter;
import com.ducks.intangibles.DifficultyControl;
import com.ducks.intangibles.EntityData;
import com.ducks.managers.EntityManager;
import com.ducks.managers.SaveManager;
import com.ducks.managers.StatsManager;
import com.ducks.tools.IShooter;
import com.ducks.tools.InputParser;

import static com.ducks.tools.FixtureFilter.*;

public class Pirate extends Ship {

    private float inputDurationRoll = 0f;

    public final float SENSOR_SCALE = DifficultyControl.getValue(3.5f, 4f, 6f);

    protected boolean playerInRange = false;

    public String collegeName;

    /**
     * Used for extensions of pirate
     */
    public Pirate() {
    }

    /**
     * Defines pirate using a vector for the location
     * @param college flag
     * @param spawn location
     */
    public Pirate(String college, Vector2 spawn) {
        this(college, spawn.x, spawn.y);
    }

    /**
     * Defines pirate using a x and y floats for the location
     * @param college flag
     * @param spawn_x location
     * @param spawn_y location
     */
    private Pirate(String college, float spawn_x, float spawn_y) {
        super();
        collegeName = college;
        mask = MASK_ALL - ENEMY_BULLET;
        category = ENEMY;
        shootWaitTime = 1f;
        shooter = new Shooter(shootWaitTime);
        shooter.setRandomizeWait();

        x = spawn_x - width / 2;
        y = spawn_y - height / 2;
        if(SaveManager.LoadSave) {
            x = spawn_x;
            y = spawn_y;
        }
        acceleration = 2f;
        max_velocity = 8f;

        // Set up ShipAnimation
        direction = 6;
        moving = false;
        animation = new ShipAnimation(college, new Vector2(x, y), radius*scale, 0.5f);
        data = new EntityData(category);

        defineShip();
    }

    /**
     * Updating pirate
     * Moves ship, shoots bullets, advances animation
     * @param deltaTime of game
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        shooter.update(deltaTime);
        // Roll on whether we need to pull a new random input direction
        float inputStickinessThreshold = 0.03f;
        if (Math.random() < inputStickinessThreshold)
            parseDirection(InputParser.fakeInput(0.3f));
        // Roll on whether we keep applying force in the given direction
        float inputDurationThreshold = 0.7f;
        if (inputDurationRoll + Math.random() * 0.1 < inputDurationThreshold)
            applyForce();
        else
            inputDurationRoll = 0f;
        if (playerInRange)
            shootBullet();
        animation.update(deltaTime, getPosition(), direction, false);
    }

    protected void shootBullet() { EntityManager.spawnBullet((IShooter) this); }

    /**
     * Kills pirate if collides with player bullet
     * @param contactor of pirate
     */
    @Override
    protected void handleContact(Fixture contactor) {
        if (EntityData.equals(contactor, PLAYER_BULLET))
            isAlive = false;
    }

    /**
     * Lets pirate shoot at player if in range
     * @param contactor of pirate
     */
    @Override
    protected void handleSensorContact(Fixture contactor) {
        if (EntityData.equals(contactor, PLAYER))
            playerInRange = !playerInRange;
    }

    /**
     * Activates red flashing
     */
    public void setAngry() {
        animation.setFlashingColor(Color.RED);
    }

    /**
     * Defines ship with sensor for looking at the player
     */
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
        EntityManager.killPirate(this);
        rigidBody.dispose();
        StatsManager.addGold(100);
        StatsManager.addScore(1000);

    }
}
