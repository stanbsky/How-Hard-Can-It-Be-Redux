package com.ducks.sprites;

//import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.components.HealthBar;
import com.ducks.components.Texture;
import com.ducks.entities.ListOfCannons;
import com.ducks.scenes.Hud;
import com.ducks.screens.MainGameScreen;

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
    ListOfCannons cannons;

    public String name;

    public float health;
    Texture texture;
    HealthBar hpBar;
    private Vector2 position;

    /**
     * Constructor
     * @param world Box2D world
     * @param screen Game Screen
     * @param spawn_x X coordinate of the college
     * @param spawn_y Y coordinate of the college
     * @param spawn_radius Radius of the college Box2D body
     * @param collegeName Name of the College
     * @param cannons Cannons class to spawn and add Cannon round
     */
    public College(World world, MainGameScreen screen, float spawn_x, float spawn_y, float spawn_radius, String collegeName, ListOfCannons cannons) {
        name = collegeName;
        this.world = world;
        this.cannons = cannons;
        spawn_radius = radius;
        System.out.println(spawn_x + "," + spawn_y + "," + spawn_radius);
        health = 1f;
        hpBar = new HealthBar(scl(spawn_x - spawn_radius), scl(spawn_y + spawn_radius),
                scl(spawn_radius*2), scl(10f), true, health);

        this.position = new Vector2(spawn_x, spawn_y);
        this.texture = new Texture(collegeName, this.position, scl(radius));

        defineCollege(spawn_x, spawn_y, spawn_radius);
    }

    /**
     * Update the bullet every delta time interval
     * @param deltaTime of the game
     */
    public void update(float deltaTime) {
        stateTime += deltaTime;
        hpBar.update(health);
        if((health <= 0f) && (health > -100f)) {
            this.texture = new Texture("destroyed", this.position, SCALE);
            this.texture.update(deltaTime, collegeBody.getPosition());
            health = -100f;
        } else {
            this.texture.update(deltaTime, collegeBody.getPosition());
            if(collegeBody.getFixtureList().get(1).getUserData().toString().contains("Attack")) {
                cannons.spawnCannon(this);
            }
            if(collegeBody.getFixtureList().get(0).getUserData().toString().contains("Damage")) {
                collegeBody.getFixtureList().get(0).setUserData("College");
                health-=.2f;
            }
        }
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
        BodyDef bdef = new BodyDef();
        bdef.position.set(x / DeltaDucks.PIXEL_PER_METER, y / DeltaDucks.PIXEL_PER_METER);
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.linearDamping = 1f;
        collegeBody = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius / DeltaDucks.PIXEL_PER_METER);

        fdef.shape = shape;
        fdef.filter.categoryBits = DeltaDucks.BIT_COLLEGES;
        fdef.filter.maskBits = DeltaDucks.BIT_PLAYER | DeltaDucks.BIT_BULLETS;
        fdef.restitution = 0.2f;
        collegeBody.createFixture(fdef).setUserData("College");

        PolygonShape polyShape = new PolygonShape();
        polyShape.setAsBox(radius * OUTER_RADIUS / DeltaDucks.PIXEL_PER_METER, radius * OUTER_RADIUS / DeltaDucks.PIXEL_PER_METER, new Vector2(0, -5 / DeltaDucks.PIXEL_PER_METER), 0);
        fdef.shape = polyShape;
        fdef.filter.categoryBits = DeltaDucks.BIT_COLLEGES;
        fdef.filter.maskBits = DeltaDucks.BIT_PLAYER;
        fdef.isSensor = true;
        collegeBody.createFixture(fdef).setUserData("College Sensor");
    }

    /**
     * Gain gold and EXP if colleges get destroyed
     */
    public void dispose() {
        Hud.addGold(1000);
        Hud.addScore(10000);
    }


}
