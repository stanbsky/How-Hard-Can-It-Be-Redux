package com.ducks.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.ducks.DeltaDucks;
import com.ducks.components.BodyType;
import com.ducks.components.HealthBar;
import com.ducks.components.RigidBody;
import com.ducks.components.Texture;
import com.ducks.entities.ListOfCannons;
import com.ducks.scenes.Hud;
import com.ducks.screens.MainGameScreen;

import static com.ducks.DeltaDucks.scl;

/***
 * College Class for Box2D Body and Sprite
 */
public class College extends Entity {

    public final float SENSOR_SCALE = 4f;

    float stateTime;

    private ListOfCannons cannons;

    public String name;

    public float health;
    private Texture texture;
    private HealthBar hpBar;
    private Vector2 position;

    public College(float spawn_x, float spawn_y, String collegeName, ListOfCannons cannons) {
        this(spawn_x, spawn_y, collegeName, cannons, MainGameScreen.getAtlas());
    }
    /**
     * Constructor
     * @param spawn_x X coordinate of the college
     * @param spawn_y Y coordinate of the college
     * @param collegeName Name of the College
     * @param cannons Cannons class to spawn and add Cannon round
     */
    public College(float spawn_x, float spawn_y, String collegeName, ListOfCannons cannons, TextureAtlas atlas) {
        this.atlas = atlas;
        name = collegeName;
        this.cannons = cannons;
        radius = 100f;
        scale = 1.2f;
        health = 1f;
        hpBar = new HealthBar(spawn_x - radius, spawn_y + radius,
                radius*2, 10f, true, health, false, atlas);

        this.position = new Vector2(spawn_x, spawn_y);
        this.texture = new Texture(collegeName, this.position, scl(radius*scale), atlas);

        defineCollege(spawn_x, spawn_y, radius);
    }

    /**
     * Update the bullet every delta time interval
     * @param deltaTime of the game
     */
    public void update(float deltaTime) {
        stateTime += deltaTime;
        hpBar.update(health);
        if((health <= 0.001f) && (health > -100f)) {
            this.texture = new Texture("destroyed", this.position, scl(radius*scale), atlas);
            this.texture.update(deltaTime, rigidBody.getBody().getPosition());
            health = -100f;
        } else {
            this.texture.update(deltaTime, rigidBody.getBody().getPosition());
            if(rigidBody.getSensorData().contains("Attack")) {
                cannons.spawnCannon(this);
            }
            if(rigidBody.getData().contains("Damage")) {
                rigidBody.setData("College");
                health-=.2f;
            }
        }
    }

    /**
     * draw the sprite of college and health bar on the game screen
     * @param batch to draw on the screen
     */
    public void draw(SpriteBatch batch) {
        this.texture.render(batch);
        this.hpBar.render(batch);
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
        fixture.filter.categoryBits = DeltaDucks.BIT_COLLEGES;
        fixture.filter.maskBits = DeltaDucks.BIT_PLAYER | DeltaDucks.BIT_BULLETS;
        rigidBody.addFixture(fixture);
        rigidBody.setData("College");

        PolygonShape polyShape = new PolygonShape();
        float side = scl(radius * SENSOR_SCALE);
        polyShape.setAsBox(side, side, new Vector2(0, -5 / DeltaDucks.PIXEL_PER_METER), 0);
        fixture.shape = polyShape;
        fixture.filter.categoryBits = DeltaDucks.BIT_COLLEGES;
        fixture.filter.maskBits = DeltaDucks.BIT_PLAYER;
        rigidBody.addSensor(fixture, "College Sensor");
    }

    /**
     * Gain gold and EXP if colleges get destroyed
     */
    public void dispose() {
        Hud.addGold(1000);
        Hud.addScore(10000);
    }


}
