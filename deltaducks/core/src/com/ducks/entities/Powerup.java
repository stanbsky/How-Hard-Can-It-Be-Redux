package com.ducks.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.ducks.intangibles.EntityData;
import com.ducks.managers.PowerupManager;
import com.ducks.managers.StatsManager;
import com.ducks.tools.BodyType;
import com.ducks.components.RigidBody;
import com.ducks.components.Texture;

import static com.ducks.DeltaDucks.scl;
import static com.ducks.tools.FixtureFilter.*;

/***
 * Powerup Class for Box2D Body and Sprite
 */
public class Powerup extends Entity {

    float stateTime;

    public String name;

    private Texture texture;
    private Vector2 position;

    private boolean isCollected = false;

    /**
     * Constructor
     * @param spawn coordinates of the Powerup
     * @param powerupName Name of the Powerup
     */
    public Powerup(Vector2 spawn, String powerupName) {
        this(spawn.x, spawn.y, powerupName);
    }

    /**
     * Constructor
     * @param spawn_x X coordinate of the Powerup
     * @param spawn_y Y coordinate of the Powerup
     * @param powerupName Name of the Powerup
     */
    private Powerup(float spawn_x, float spawn_y, String powerupName) {
        name = powerupName;
        radius = 20f;
        scale = 1.2f;
        isCollected = false;

        this.position = new Vector2(spawn_x, spawn_y);
        this.texture = new Texture(name, this.position, scl(radius*scale));
        category = ENEMY;
        mask = MASK_ALL - ENEMY_BULLET;
        data = new EntityData(category);

        definePowerup(spawn_x, spawn_y, radius);
    }

    /**
     * Update the bullet every delta time interval
     * @param deltaTime of the game
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        stateTime += deltaTime;
        this.texture.update(deltaTime, rigidBody.getBody().getPosition());
    }

    @Override
    protected void handleContact(Fixture contactor) {
    }

    @Override
    protected void handleSensorContact(Fixture contactor) {
        if (EntityData.equals(contactor, PLAYER))
            isCollected = true;
    }

    @Override
    public boolean isAlive() { return true; }

    @Override
    public boolean cleanup() {
        return isCollected;
    }

    /**
     * draw the sprite of Powerup on the game screen
     */
    public void draw() {
        this.texture.render();
    }

    /**
     * Define the Box2D body and fixture and map it onto the Box2D world
     * @param x coordinate of Box2D body
     * @param y coordinate of Box2D body
     * @param radius radius of Box2D body
     */
    public void definePowerup(float x, float y, float radius) {
        rigidBody = new RigidBody(new Vector2(scl(x), scl(y)), BodyType.Static, 1f);

        CircleShape shape = new CircleShape();
        shape.setRadius(scl(radius));
        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;
        fixture.filter.categoryBits = category;
        fixture.filter.maskBits = 0;
        rigidBody.addFixture(fixture);
        fixture.filter.maskBits = mask;
        rigidBody.addSensor(fixture, category,"Powerup Sensor");
        rigidBody.setData(data);
    }

    /**
     *
     */
    public void dispose() {
        StatsManager.addScore(1000);
        PowerupManager.newPowerup(name);
        rigidBody.dispose();
    }


}
