package com.ducks.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.ducks.DeltaDucks;
import com.ducks.components.BodyType;
import com.ducks.components.HealthBar;
import com.ducks.components.RigidBody;
import com.ducks.components.Texture;
import com.ducks.components.BodyType;
import com.ducks.entities.ListOfCannons;
import com.ducks.scenes.Hud;

import static com.ducks.DeltaDucks.scl;

/***
 * College Class for Box2D Body and Sprite
 */
public class College {
    public World world;

    private final int PIXEL_COLLEGE_WIDTH = 2560;
    public static final int PIXEL_COLLEGE_HEIGHT = 2560;

    private final float COLLEGE_WIDTH = PIXEL_COLLEGE_WIDTH * .1f;
    public static final float COLLEGE_HEIGHT = PIXEL_COLLEGE_HEIGHT * .1f;
    private final float SCALE = 1f;
    private final float radius = 100f;

    public final float OUTER_RADIUS = 4f;

    float stateTime;

    public Body collegeBody;
    private ListOfCannons cannons;

    public String name;

    public float health;
    public boolean destroyed;
    private Texture texture;
    private HealthBar hpBar;
    private Vector2 position;
    private RigidBody rigidBody;

    /**
     * Constructor
     * @param spawn_x X coordinate of the college
     * @param spawn_y Y coordinate of the college
     * @param collegeName Name of the College
     * @param cannons Cannons class to spawn and add Cannon round
     */
    public College(float spawn_x, float spawn_y, String collegeName, ListOfCannons cannons, World world) {
        name = collegeName;
        this.world = world;
        this.cannons = cannons;
        System.out.println(spawn_x + "," + spawn_y + "," + radius);
        health = 1f;
        hpBar = new HealthBar(spawn_x - radius, spawn_y + radius,
                radius*2, 10f, true, health, false);

        this.position = new Vector2(spawn_x, spawn_y);
        this.texture = new Texture(collegeName, this.position, scl(radius));

        defineCollege(spawn_x, spawn_y, radius);
    }

    /**
     * Update the bullet every delta time interval
     * @param deltaTime of the game
     */
    public void update(float deltaTime) {
        stateTime += deltaTime;
        hpBar.update(health);
        if(destroyed) {
            this.texture = new Texture("destroyed", this.position, SCALE);
            this.texture.update(deltaTime, rigidBody.getBody().getPosition());
        } else {
            this.texture.update(deltaTime, rigidBody.getBody().getPosition());
            if(rigidBody.getBody().getFixtureList().get(1).getUserData().toString().contains("Attack")) {
                cannons.spawnCannon(this);
            }
            if(rigidBody.getBody().getFixtureList().get(0).getUserData().toString().contains("Damage")) {
                rigidBody.setData("College");
                health-=.2f;
            }
        }
    }

    public Vector2 getPosition() {
        return rigidBody.getBody().getPosition();
    }

    /**
     * draw the sprite of college and health bar on the game screen
     * @param batch to draw on the screen
     */
    public void extendedDraw(SpriteBatch batch) {
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
        float side = scl(radius * OUTER_RADIUS);
        polyShape.setAsBox(side, side, new Vector2(0, -5 / DeltaDucks.PIXEL_PER_METER), 0);
        fixture.shape = polyShape;
        fixture.filter.categoryBits = DeltaDucks.BIT_COLLEGES;
        fixture.filter.maskBits = DeltaDucks.BIT_PLAYER;
        rigidBody.addSensor(fixture, "College Sensor");

//        BodyDef bdef = new BodyDef();
//        bdef.position.set(x / DeltaDucks.PIXEL_PER_METER, y / DeltaDucks.PIXEL_PER_METER);
//        bdef.type = BodyDef.BodyType.StaticBody;
//        bdef.linearDamping = 1f;
//        collegeBody = world.createBody(bdef);
//
//        FixtureDef fdef = new FixtureDef();
//        CircleShape shape = new CircleShape();
//        shape.setRadius(radius / DeltaDucks.PIXEL_PER_METER);
//
//        fdef.shape = shape;
//        fdef.filter.categoryBits = DeltaDucks.BIT_COLLEGES;
//        fdef.filter.maskBits = DeltaDucks.BIT_PLAYER | DeltaDucks.BIT_BULLETS;
//        fdef.restitution = 0.2f;
//        collegeBody.createFixture(fdef).setUserData("College");

//        PolygonShape polyShape = new PolygonShape();
//        polyShape.setAsBox(radius * OUTER_RADIUS / DeltaDucks.PIXEL_PER_METER, radius * OUTER_RADIUS / DeltaDucks.PIXEL_PER_METER, new Vector2(0, -5 / DeltaDucks.PIXEL_PER_METER), 0);
//        fdef.shape = polyShape;
//        fdef.filter.categoryBits = DeltaDucks.BIT_COLLEGES;
//        fdef.filter.maskBits = DeltaDucks.BIT_PLAYER;
//        fdef.isSensor = true;
//        collegeBody.createFixture(fdef).setUserData("College Sensor");
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
