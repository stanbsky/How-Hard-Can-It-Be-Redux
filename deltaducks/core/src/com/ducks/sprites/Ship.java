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

public class Ship extends Sprite {
    public enum State {STANDING, MOVING, NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
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

    private final int PIXEL_SHIP_WIDTH = 1280;
    private final int PIXEL_SHIP_HEIGHT = 1280;

    private final float MULTIPLIER = .07f;

    private final int SHIP_WIDTH = Math.round(PIXEL_SHIP_WIDTH * MULTIPLIER);
    private final int SHIP_HEIGHT = Math.round(PIXEL_SHIP_HEIGHT * MULTIPLIER);

    private final int SHIP_SPAWN_X = 370;
    private final int SHIP_SPAWN_Y = 340;

    private final int SPRITE_RADIUS = 1;

    private final float SHIP_FRAME_DURATION = 0.5f;
    private final float VERTICAL_ROLL_TIMER_SWITCH_TIME = 0.25f;
    private final float HORIZONTAL_ROLL_TIMER_SWITCH_TIME = 0.25f;
    private Animation<TextureRegion>[] rolls;

    int roll;
    float rollVerticalTimer;
    float rollHorizontalTimer;
    float stateTime;

    private final int UP_INDEX = 4;
    private final int DOWN_INDEX = 0;
    private final int LEFT_INDEX = 2;
    private final int RIGHT_INDEX = 6;
    private final int FIRST_INDEX = 0;
    private final int LAST_INDEX = 7;

    public Ship(World world, MainGameScreen screen) {
//        super(screen.getAtlas().findRegion("boat"));
        super(MainGameScreen.resources.getTexture("boat"));
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        movingUp = true;
        movingRight = true;

        roll = 4;
        rolls = new Animation[16];
        rollVerticalTimer = 2f;
        rollHorizontalTimer = 2f;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i=2; i<4; i++) {
            frames.add(new TextureRegion(getTexture(), i * PIXEL_SHIP_WIDTH, 0, PIXEL_SHIP_WIDTH, PIXEL_SHIP_HEIGHT));
        }
        frames.add(new TextureRegion(getTexture(), 0, 0, PIXEL_SHIP_WIDTH, PIXEL_SHIP_HEIGHT));

        shipMove = new Animation(0.1f, frames);

        frames.clear();

        for(int i=0; i<16; i++) {
            frames.add(new TextureRegion(getTexture(), i * PIXEL_SHIP_WIDTH, 0, PIXEL_SHIP_WIDTH, PIXEL_SHIP_HEIGHT));
        }

        rolls[0] = new Animation(SHIP_FRAME_DURATION, frames.get(0)); // Down
        rolls[1] = new Animation(SHIP_FRAME_DURATION, frames.get(1));
        rolls[2] = new Animation(SHIP_FRAME_DURATION, frames.get(2)); // Left
        rolls[3] = new Animation(SHIP_FRAME_DURATION, frames.get(3));
        rolls[4] = new Animation(SHIP_FRAME_DURATION, frames.get(4)); // Up
        rolls[5] = new Animation(SHIP_FRAME_DURATION, frames.get(5));
        rolls[6] = new Animation(SHIP_FRAME_DURATION, frames.get(6)); // Right
        rolls[7] = new Animation(SHIP_FRAME_DURATION, frames.get(7));


        shipStand = new TextureRegion(getTexture(), PIXEL_SHIP_WIDTH * 0, 0, PIXEL_SHIP_WIDTH, PIXEL_SHIP_HEIGHT);


        defineShip();
        setBounds(0, 0, SHIP_WIDTH / DeltaDucks.PIXEL_PER_METER, SHIP_HEIGHT / DeltaDucks.PIXEL_PER_METER);
        setRegion(shipStand);
    }

    public void update(float deltaTime) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(deltaTime));
//        setRegion(shipStand);
    }

    public TextureRegion getFrame(float deltaTime) {
        currentState = getState();
//        System.out.println(currentState);
        TextureRegion region;
        switch (currentState) {
            case MOVING:
                region = shipMove.getKeyFrame(stateTimer, true);
                break;
            case NORTH:
                if (Gdx.input.isKeyPressed(Input.Keys.UP) && !Gdx.input.isKeyPressed(Input.Keys.DOWN) && roll != UP_INDEX) {
                    rollVerticalTimer = 0;
                    if (roll > DOWN_INDEX && roll < UP_INDEX) {
                        roll++;
                    } else if (roll == FIRST_INDEX) {
                        roll = LAST_INDEX;
                    } else if (roll <= LAST_INDEX) {
                        roll--;
                    }
                }
                rollVerticalTimer += deltaTime;
                if (Math.abs(rollVerticalTimer) > VERTICAL_ROLL_TIMER_SWITCH_TIME && roll != UP_INDEX) {
                    rollVerticalTimer -= VERTICAL_ROLL_TIMER_SWITCH_TIME;
                    if (roll > DOWN_INDEX && roll < UP_INDEX) {
                        roll++;
                    } else if (roll == FIRST_INDEX) {
                        roll = LAST_INDEX;
                    } else if (roll <= LAST_INDEX) {
                        roll--;
                    }
                }
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    roll = UP_INDEX+1;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    roll = UP_INDEX-1;
                }
                region = rolls[roll].getKeyFrame(stateTime, true);
                break;
            case SOUTH:
                if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && !Gdx.input.isKeyPressed(Input.Keys.UP) && roll != DOWN_INDEX) {
                    rollVerticalTimer = 0;
                    if (roll <= UP_INDEX && roll > FIRST_INDEX) {
                        roll--;
                    } else if (roll > UP_INDEX && roll < LAST_INDEX) {
                        roll++;
                    } else if (roll == LAST_INDEX) {
                        roll = FIRST_INDEX;
                    }
                }
                rollVerticalTimer -= deltaTime;
                if (Math.abs(rollVerticalTimer) > VERTICAL_ROLL_TIMER_SWITCH_TIME && roll != DOWN_INDEX) {
                    rollVerticalTimer -= VERTICAL_ROLL_TIMER_SWITCH_TIME;
                    if (roll <= UP_INDEX && roll > FIRST_INDEX) {
                        roll--;
                    } else if (roll > UP_INDEX && roll < LAST_INDEX) {
                        roll++;
                    } else if (roll == LAST_INDEX) {
                        roll = FIRST_INDEX;
                    }
                }
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    roll = RIGHT_INDEX+1;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    roll = LEFT_INDEX-1;
                }
                region = rolls[roll].getKeyFrame(stateTime, true);
                break;
            case EAST:
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.LEFT) && roll != RIGHT_INDEX) {
                    rollHorizontalTimer = 0;
                    if (roll <= LAST_INDEX && roll > RIGHT_INDEX) {
                        roll--;
                    } else if (roll <= LEFT_INDEX && roll > FIRST_INDEX) {
                        roll--;
                    } else if (roll == FIRST_INDEX) {
                        roll = LAST_INDEX;
                    } else {
                        roll++;
                    }
                }
                rollHorizontalTimer += deltaTime;
                if (Math.abs(rollHorizontalTimer) > HORIZONTAL_ROLL_TIMER_SWITCH_TIME && roll != RIGHT_INDEX) {
                    rollHorizontalTimer -= HORIZONTAL_ROLL_TIMER_SWITCH_TIME;
                    if (roll <= LAST_INDEX && roll > RIGHT_INDEX) {
                        roll--;
                    } else if (roll <= LEFT_INDEX && roll > FIRST_INDEX) {
                        roll--;
                    } else if (roll == FIRST_INDEX) {
                        roll = LAST_INDEX;
                    } else {
                        roll++;
                    }
                }
                if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    roll = UP_INDEX+1;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    roll = RIGHT_INDEX+1;
                }
                region = rolls[roll].getKeyFrame(stateTime, true);
                break;
            case WEST:
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT) && roll != LEFT_INDEX) {
                    rollHorizontalTimer = 0;
                    if (roll >= DOWN_INDEX && roll < LEFT_INDEX) {
                        roll++;
                    } else if (roll <= RIGHT_INDEX) {
                        roll--;
                    } else if (roll == LAST_INDEX) {
                        roll = FIRST_INDEX;
                    } else {
                        roll++;
                    }
                }
                rollHorizontalTimer -= deltaTime;
                if (Math.abs(rollHorizontalTimer) > HORIZONTAL_ROLL_TIMER_SWITCH_TIME && roll != LEFT_INDEX) {
                    rollHorizontalTimer -= HORIZONTAL_ROLL_TIMER_SWITCH_TIME;
                    if (roll >= DOWN_INDEX && roll < LEFT_INDEX) {
                        roll++;
                    } else if (roll <= RIGHT_INDEX) {
                        roll--;
                    } else if (roll == LAST_INDEX) {
                        roll = FIRST_INDEX;
                    } else {
                        roll++;
                    }
                }
                if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    roll = LEFT_INDEX+1;
                }
                if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    roll = LEFT_INDEX-1;
                }
                region = rolls[roll].getKeyFrame(stateTime, true);
                break;
            default:
                currentState = previousState;
                region = rolls[roll].getKeyFrame(stateTime, true);
                break;
        }
//        System.out.println(roll);

//        if((b2body.getLinearVelocity().x < 0 || !movingRight) && !region.isFlipX()){
//            region.flip(true, false);
//            movingRight = false;
//        } else if ((b2body.getLinearVelocity().x > 0 || movingRight) && region.isFlipX()) {
//            region.flip(true, false);
//            movingRight = true;
//        }
        stateTimer = currentState == previousState ? stateTimer + deltaTime : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            return State.EAST;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            return State.NORTH;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            return State.SOUTH;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            return State.WEST;
//        else if(b2body.getLinearVelocity().x != 0 || b2body.getLinearVelocity().y != 0)
//            return State.MOVING;
        else
            return State.STANDING;
    }

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
