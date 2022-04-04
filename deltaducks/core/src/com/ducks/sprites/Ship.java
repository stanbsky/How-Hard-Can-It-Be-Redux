package com.ducks.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.ducks.DeltaDucks;
import com.ducks.components.ShipAnimation;
import com.ducks.screens.MainGameScreen;

/***
 * Ship (or Player) Class for Box2D Body and Sprite
 */
public class Ship {
    public World world;
    public Body b2body;
    private ShipAnimation animation;
    private float stateTime;
    private int direction;
    private boolean moving;
    private float width;
    private float height;
    private float x;
    private float y;
    private float radius = 128/2.5f / DeltaDucks.PIXEL_PER_METER;
    private AtlasRegion frame;

    private final int SHIP_WIDTH = 128;
    private final int SHIP_HEIGHT = 128;

//    private final int SHIP_SPAWN_X = 29;
//    private final int SHIP_SPAWN_Y = 52;
//    private final int SHIP_SPAWN_X = 1370;
//    private final int SHIP_SPAWN_Y = 1340;
    private final int SHIP_SPAWN_X = 3358;
    private final int SHIP_SPAWN_Y = 5563;

    private final float SHIP_FRAME_DURATION = 0.5f;

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

        defineShip();
    }

    /**
     * Update the player ship every delta time interval
     * @param deltaTime of the game
     */
    public void update(float deltaTime) {
        stateTime += deltaTime;
//        System.out.printf("%.2f , %.2f",b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
//        System.out.printf("%.2f",b2body.getPosition().x - getWidth() / 2);
//        System.out.println(b2body.getPosition());
        frame = animation.getFrame(stateTime, getDirection(), moving);
        x = b2body.getPosition().x - width/2;
        y = b2body.getPosition().y - height/2;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(frame, x, y, width, height);
    }

    /**
     * Get the direction of the ship corresponding to its movement
     * @return direction represented as the corresponding numerical numpad value
     */
    public int getDirection() {
        //TODO: change to getting direction via linear velocity
        int direction = 5;
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))
            direction += 3;
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))
            direction -= 3;
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
            direction -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            direction += 1;

        if (direction == 5) {
            direction = this.direction;
            moving = !b2body.getLinearVelocity().isZero(0.05f);
        } else {
            this.direction = direction;
            moving = true;
        }

        return direction;
    }

    /**
     * Define the Box2D body and fixture and map it onto the Box2D world
     */
    public void defineShip() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(SHIP_SPAWN_X / DeltaDucks.PIXEL_PER_METER, SHIP_SPAWN_Y / DeltaDucks.PIXEL_PER_METER);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearDamping = 1.2f;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        fdef.shape = shape;
        fdef.filter.categoryBits = DeltaDucks.BIT_PLAYER;
        fdef.filter.maskBits = DeltaDucks.BIT_LAND | DeltaDucks.BIT_PIRATES | DeltaDucks.BIT_MONSTERS | DeltaDucks.BIT_BOUNDARY;
        fdef.restitution = 0.2f;
        b2body.createFixture(fdef).setUserData("Player");

        PolygonShape polyShape = new PolygonShape();
        polyShape.setAsBox(2 / DeltaDucks.PIXEL_PER_METER, 2 / DeltaDucks.PIXEL_PER_METER, new Vector2(0, -5 / DeltaDucks.PIXEL_PER_METER), 0);
        fdef.shape = polyShape;
        fdef.filter.categoryBits = DeltaDucks.BIT_PLAYER;
        fdef.filter.maskBits = DeltaDucks.BIT_LAND | DeltaDucks.BIT_PIRATES | DeltaDucks.BIT_MONSTERS | DeltaDucks.BIT_BOUNDARY;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("Sensor");

    }
}
