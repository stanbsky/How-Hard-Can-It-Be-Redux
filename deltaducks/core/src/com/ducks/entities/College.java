package com.ducks.entities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.ducks.DeltaDucks;
import com.ducks.components.Shooter;
import com.ducks.intangibles.EntityData;
import com.ducks.tools.BodyType;
import com.ducks.components.HealthBar;
import com.ducks.components.RigidBody;
import com.ducks.components.Texture;
import com.ducks.managers.BulletManager;
import com.ducks.tools.IShooter;
import com.ducks.ui.Hud;
import com.ducks.screens.MainGameScreen;

import static com.ducks.DeltaDucks.scl;
import static com.ducks.screens.MainGameScreen.atlas;
import static com.ducks.tools.FixtureFilter.*;

/***
 * College Class for Box2D Body and Sprite
 */
public class College extends Entity implements IShooter {

    public final float SENSOR_SCALE = 4f;

    float stateTime;

    public String name;

    public float health;
    public boolean destroyed;
    private Texture texture;
    private HealthBar hpBar;
    private Shooter shooter;
    private Vector2 position;

    private boolean playerInRange = false;

    /**
     * Constructor
     * @param spawn_x X coordinate of the college
     * @param spawn_y Y coordinate of the college
     * @param collegeName Name of the College
     */
    public College(float spawn_x, float spawn_y, String collegeName) {
        name = collegeName;
        shooter = new Shooter(1f);
        radius = 100f;
        scale = 1.2f;
        health = 1f;
        hpBar = new HealthBar(spawn_x - radius, spawn_y + radius,
                radius*2, 10f, true, health, false);

        this.position = new Vector2(spawn_x, spawn_y);
        this.texture = new Texture(collegeName, this.position, scl(radius*scale));
        category = ENEMY;
        mask = MASK_ALL - ENEMY_BULLET;
        data = new EntityData(category);

        defineCollege(spawn_x, spawn_y, radius);
    }

    /**
     * Update the bullet every delta time interval
     * @param deltaTime of the game
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        shooter.update(deltaTime);
        stateTime += deltaTime;
        hpBar.update(health);
        if(destroyed) {
            this.texture = new Texture("destroyed", this.position, scl(radius*scale));
            this.texture.update(deltaTime, rigidBody.getBody().getPosition());
        } else {
            this.texture.update(deltaTime, rigidBody.getBody().getPosition());
            if (playerInRange)
                BulletManager.spawnBullet((IShooter) this);
        }
    }

    public boolean ready() {
        return shooter.ready();
    }

    public void resetShootTimer() {
        shooter.resetShootTimer();
    }

    @Override
    protected void handleContact(Fixture contactor) {
        if (EntityData.equals(contactor, PLAYER_BULLET))
            health -= 0.2f;
    }

    @Override
    protected void handleSensorContact(Fixture contactor) {
        if (EntityData.equals(contactor, PLAYER))
            playerInRange = !playerInRange;
    }

    /**
     * draw the sprite of college and health bar on the game screen
     */
    public void draw() {
        this.texture.render();
        this.hpBar.render();
    }

    /**
     * Define the Box2D body and fixture and map it onto the Box2D world
     * @param x coordinate of Box2D body
     * @param y coordinate of Box2D body
     * @param radius radius of Box2D body
     */
    public void defineCollege(float x, float y, float radius) {
        rigidBody = new RigidBody(new Vector2(scl(x), scl(y)), BodyType.Static, 1f);

        CircleShape shape = new CircleShape();
        shape.setRadius(scl(radius));
        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;
        fixture.filter.categoryBits = category;
        fixture.filter.maskBits = mask;
        rigidBody.addFixture(fixture);
        rigidBody.setData(data);

        PolygonShape polyShape = new PolygonShape();
        float side = scl(radius * SENSOR_SCALE);
        polyShape.setAsBox(side, side, new Vector2(0, -5 / DeltaDucks.PIXEL_PER_METER), 0);
        fixture.shape = polyShape;
        fixture.filter.categoryBits = ENEMY;
        fixture.filter.maskBits = PLAYER;
        rigidBody.addSensor(fixture, ENEMY, "College Sensor");
    }

    /**
     * Gain gold and EXP if colleges get destroyed
     */
    public void dispose() {
        destroyed = true;
        Hud.addGold(1000);
        Hud.addScore(10000);
    }


}
