package com.ducks.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
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
public class Ship {
    public World world;
    private ShipAnimation animation;
    private float stateTime;
    private int direction;
    private boolean moving;
    private float width;
    private float height;
    private float x;
    private float y;
    protected short category;
    protected short mask;
    private RigidBody rigidBody;
    private float radius = scl(128/2.5f);
    private AtlasRegion frame;

//    private final int SHIP_SPAWN_X = 29;
//    private final int SHIP_SPAWN_Y = 52;
//    private final int SHIP_SPAWN_X = 1370;
//    private final int SHIP_SPAWN_Y = 1340;
    //TODO: reset to one of the above (maybe) for production
    private final int SHIP_SPAWN_X = 3358;
    private final int SHIP_SPAWN_Y = 5563;

    private final float SHIP_FRAME_DURATION = 0.5f;

    //TODO: reset to 1 & 4 for production
    private static float ACCELERATION = 10f;
    private static float MAX_VELOCITY = 40f;
    private float force_x;
    private float force_y;

    /**
     * Constructor
     */
    public Ship(World world) {
        animation = new ShipAnimation("player", SHIP_FRAME_DURATION);
        this.world = world;
        direction = 6;
        moving = false;

        frame = animation.getFrame(0f, direction, false);
        width = height = radius*3f;

        x = SHIP_SPAWN_X - width/2;
        y = SHIP_SPAWN_Y - height/2;

        // Set up rigid body
        this.mask = DeltaDucks.BIT_LAND | DeltaDucks.BIT_PIRATES | DeltaDucks.BIT_MONSTERS | DeltaDucks.BIT_BOUNDARY;
        this.category = DeltaDucks.BIT_PLAYER;
        defineShip();
        this.rigidBody.setData("Player");
    }

    /**
     * Update the player ship every delta time interval
     * @param deltaTime of the game
     */
    public void update(float deltaTime) {
        stateTime += deltaTime;
        parseInput();
        applyForce();
        frame = animation.getFrame(stateTime, direction, moving);
        x = (getPosition().x - width/2);
        y = (getPosition().y - height/2);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(frame, x, y, width, height);
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
            force_y += ACCELERATION;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            direction -= 3;
            force_y -= ACCELERATION;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            direction -= 1;
            force_x -= ACCELERATION;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            direction += 1;
            force_x += ACCELERATION;
        }

        if (direction == 5) {
            moving = !rigidBody.getBody().getLinearVelocity().isZero(0.05f);
        } else {
            this.direction = direction;
            moving = true;
        }
    }

    public void applyForce() {
        if (getVelocity().x > MAX_VELOCITY)
            force_x = 0;
        if (getVelocity().y > MAX_VELOCITY)
            force_y = 0;
        rigidBody.getBody().applyForceToCenter(new Vector2(force_x, force_y), true);
    }

    public Vector2 getPosition() {
        return rigidBody.getBody().getPosition();
    }

    public float getRadius() {
        return radius;
    }

    public Vector2 getVelocity() {
        return rigidBody.getBody().getLinearVelocity();
    }

    public Body getBody() {
        return rigidBody.getBody();
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
    }
}
