package com.ducks.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.ducks.DeltaDucks;
import com.ducks.components.BodyType;
import com.ducks.components.RigidBody;
import com.ducks.components.ShipAnimation;

import static com.ducks.DeltaDucks.scl;

/***
 * Ship (or Player) Class for Box2D Body and Sprite
 */
public class Ship extends Entity {
    public World world;
    protected ShipAnimation animation;
    protected int direction;
    protected boolean moving;

    protected float acceleration;
    protected float max_velocity;
    private float force_x;
    private float force_y;

    protected short mask;
    protected short category;
    protected String data;

//    public Ship() {
//
//    }
    /**
     * Constructor
     */
    public Ship() {
        super();
//        this.world = world;

        radius = scl(128 / 2.5f);
        scale = 1.5f;
        width = height = radius*2f*scale;
    }

    /**
     * Update the player ship every delta time interval
     * @param deltaTime of the game
     */
    public void update(float deltaTime) {
        parseInput();
        applyForce();
        animation.update(deltaTime, getPosition(), direction, moving);
        x = (getPosition().x - width/2);
        y = (getPosition().y - height/2);
    }

    public void draw(SpriteBatch batch) {
        animation.render(batch);
    }

    /**
     * Get the direction of the ship corresponding to its movement
     * @return direction represented as the corresponding numerical numpad value
     */
    public void parseInput() {
        //TODO: change to getting direction via linear velocity
        int direction = 5;
        force_x = force_y = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            direction += 3;
            force_y += acceleration;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            direction -= 3;
            force_y -= acceleration;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            direction -= 1;
            force_x -= acceleration;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            direction += 1;
            force_x += acceleration;
        }

        if (direction == 5) {
            moving = !getVelocity().isZero(0.05f);
        } else {
            this.direction = direction;
            moving = true;
        }
    }

    public void applyForce() {
        if (getVelocity().x > max_velocity)
            force_x = 0;
        if (getVelocity().y > max_velocity)
            force_y = 0;
        rigidBody.getBody().applyForceToCenter(new Vector2(force_x, force_y), true);
    }

    /**
     * Define the Box2D body and fixture and map it onto the Box2D world
     */
    public void defineShip() {
        this.rigidBody = new RigidBody(new Vector2(scl(x), scl(y)), BodyType.Dynamic, 1.2f);
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;
        fixture.filter.categoryBits = category;
        fixture.filter.maskBits = mask;
        rigidBody.addFixture(fixture);
        rigidBody.setData(data);
    }
}
