package com.ducks.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.screens.MainGameScreen;

/***
 * Ship (or Player) Class for Box2D Body and Sprite
 */
public class Ship extends Sprite {
    public enum State {STANDING, MOVING, NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST};
    public World world;
    public Body b2body;
    public int[] previousState = new int[2];
    private TextureRegion shipStand;
    private Animation <TextureRegion> shipMove;
    private Animation <TextureRegion> shipAttack;

//    private Animation <TextureRegion> shipMoveFromTop;
//    private Animation <TextureRegion> shipMoveFromDown;
//
//    private Animation <TextureRegion> shipMoveToTop;
//    private Animation <TextureRegion> shipMoveToDown;

    private float stateTimer;
    private boolean movingUp;
    private boolean movingRight;

        private final int PIXEL_SHIP_WIDTH = 64;
    private final int PIXEL_SHIP_HEIGHT = 64;

    private final int SHIP_WIDTH = 128;
    private final int SHIP_HEIGHT = 128;

    private final int SHIP_SPAWN_X = 1370;
    private final int SHIP_SPAWN_Y = 1340;

    private final int SPRITE_RADIUS = 1;

    private final float SHIP_FRAME_DURATION = 0.5f;
    private int ROLL_TIMER_SWITCH_TIME = 0;
    private Animation<TextureRegion>[] rolls;
    private Animation<TextureRegion>[] idleRolls;

    int roll;
    float rollVerticalTimer;
    float rollHorizontalTimer;
    float stateTime;

//    private final int UP_INDEX = 4;
//    private final int DOWN_INDEX = 0;
//    private final int LEFT_INDEX = 2;
//    private final int RIGHT_INDEX = 6;
//    private final int FIRST_INDEX = 0;
//    private final int LAST_INDEX = 7;  *these are all useless, please do delete

    /**
     * Constructor
     * @param world Box2D world
     * @param screen Game Screen
     */
    public Ship(World world, MainGameScreen screen) {
//        super(screen.getAtlas().findRegion("boat"));
        super(MainGameScreen.resources.getTexture("boat"));
        this.world = world;
        previousState[0] = 0;
        previousState[1] = 0;
        stateTimer = 0;
        movingUp = true;
        movingRight = true;

        roll = 4;
        rolls = new Animation[8];
        idleRolls = new Animation[8];
        rollVerticalTimer = 2f;
        rollHorizontalTimer = 2f;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(int i=0; i<8; i++) {
            frames.add(new TextureRegion(getTexture(), i * PIXEL_SHIP_WIDTH, 0, PIXEL_SHIP_WIDTH, PIXEL_SHIP_HEIGHT));
            idleRolls[i] = new Animation(SHIP_FRAME_DURATION, frames.get(i));
        }

        String[] directionNames = {"south", "southwest", "west", "northwest", "north", "northeast", "east", "southeast"};
        for(int a=0; a<8; a++) {
            frames.clear();
            for (int i = 0; i < 4; i++) {
                frames.add(new TextureRegion(MainGameScreen.resources.getTexture("boat " + directionNames[a]), i * PIXEL_SHIP_WIDTH, 0, PIXEL_SHIP_WIDTH, PIXEL_SHIP_HEIGHT));
            }
            rolls[a] = new Animation(SHIP_FRAME_DURATION, frames);
        }

        frames.clear();

        shipStand = new TextureRegion(getTexture(), PIXEL_SHIP_WIDTH * 0, 0, PIXEL_SHIP_WIDTH, PIXEL_SHIP_HEIGHT);


        defineShip();
        setBounds(0, 0, SHIP_WIDTH / DeltaDucks.PIXEL_PER_METER, SHIP_HEIGHT / DeltaDucks.PIXEL_PER_METER);
        setRegion(shipStand);
    }

    /**
     * Update the player ship every delta time interval
     * @param deltaTime of the game
     */
    public void update(float deltaTime) {
        stateTime += deltaTime;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(deltaTime));
//        setRegion(shipStand);
    }

    /**
     * Get the current animation of the ship corresponding to the direction of its movement
     * @param deltaTime of the game
     * @return the frame animation
     */
    public TextureRegion getFrame(float deltaTime) {
//        currentState = getState();
//        System.out.println(currentState);
        int[] currentDirection = getDirection();
        TextureRegion region;


        switch (3*(currentDirection[0] + 1) + (currentDirection[1] + 1)) {
            case 0:
                roll = 1;
                break;
            case 1:
                roll = 2;
                break;
            case 2:
                roll = 3;
                break;
            case 3:
                roll = 0;
                break;
            case 4:
                if (ROLL_TIMER_SWITCH_TIME-- > 1) {
                    return rolls[roll].getKeyFrame(stateTime, true);
                }
                else {
                    return idleRolls[roll].getKeyFrame(stateTime, true);
                }
            case 5:
                roll = 4;
                break;
            case 6:
                roll = 7;
                break;
            case 7:
                roll = 6;
                break;
            case 8:
                roll = 5;
                break;
        }
//        region = rolls[roll].getKeyFrame(stateTime, true);

//        currentDirection = previousState;
//        region = idleRolls[roll].getKeyFrame(stateTime, true);


//        stateTimer = currentDirection == previousState ? stateTimer + deltaTime : 0;
//        previousState = currentDirection;
//        region = idleRolls[roll].getKeyFrame(stateTime, true);
        ROLL_TIMER_SWITCH_TIME = 75;
        return rolls[roll].getKeyFrame(stateTime, true);
    }

    /**
     * Get the direction of the ship corresponding to its movement
     * @return direction
     */
    public int[] getDirection() {
        int[] direction = new int[2];
        direction[0] = 0; // x
        direction[1] = 0; // y
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))
            direction[1] += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
            direction[0] -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))
            direction[1] -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            direction[0] += 1;

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
        shape.setRadius(SHIP_WIDTH/2.5f / DeltaDucks.PIXEL_PER_METER);

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
