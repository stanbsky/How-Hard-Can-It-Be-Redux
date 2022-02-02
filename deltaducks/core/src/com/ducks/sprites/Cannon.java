package com.ducks.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.scenes.Hud;
import com.ducks.screens.MainGameScreen;

/***
 * Cannon Class for Box2D Body and Sprite
 */
public class Cannon extends Sprite {
    private World world;
    private Ship player;
    private College college;

    private Animation<TextureRegion> cannonIdle;

    private final int PIXEL_BULLET_WIDTH = 256;
    private final int PIXEL_BULLET_HEIGHT = 256;

    private final float BULLET_WIDTH = PIXEL_BULLET_WIDTH * .2f;
    private final float BULLET_HEIGHT = PIXEL_BULLET_HEIGHT * .2f;

    float stateTime;
    float spawnTimer;
    public Body cannonBody;

    private final float BULLET_SPEED = 130f;
    private final float BULLET_SPAWN_DURATION = 2f;

    /**
     * Constructor
     * @param world Box2D world
     * @param college Box2D object of college who is attacking
     * @param player Box2D object of player
     */
    public Cannon(World world, College college, Ship player) {
        super(MainGameScreen.resources.getTexture("mehnat"));
        this.world = world;
        this.college = college;
        this.player = player;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(getTexture(), 1 * PIXEL_BULLET_WIDTH, 0 * PIXEL_BULLET_HEIGHT, PIXEL_BULLET_WIDTH, PIXEL_BULLET_HEIGHT));
        cannonIdle = new Animation(0.1f, frames);
        frames.clear();
        setBounds(college.collegeBody.getPosition().x - college.collegeBody.getFixtureList().get(0).getShape().getRadius() /2, college.collegeBody.getPosition().y - college.collegeBody.getFixtureList().get(0).getShape().getRadius(), BULLET_WIDTH / DeltaDucks.PIXEL_PER_METER, BULLET_HEIGHT / DeltaDucks.PIXEL_PER_METER);
        setRegion(cannonIdle.getKeyFrame(stateTime, true));
        defineCannon();
        cannonBody.applyForceToCenter(Crosshair.getDireciton(college.collegeBody.getPosition(), player.b2body.getPosition()).scl(BULLET_SPEED), true);
    }

    /**
     * Update the cannon every delta time interval
     * @param deltaTime of the game
     */
    public void update(float deltaTime) {
        stateTime += deltaTime;
        spawnTimer += deltaTime;
        setPosition(cannonBody.getPosition().x - getWidth()/2, cannonBody.getPosition().y - getHeight()/2);
        if(spawnTimer > BULLET_SPAWN_DURATION) {
            cannonBody.getFixtureList().get(0).setUserData("Cannon Dead");
        }
    }

    /**
     * Define the Box2D body and fixture and map it onto the Box2D world
     */
    public void defineCannon() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX() + getWidth(), getY() + getHeight());
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearDamping = .7f;
        cannonBody = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / DeltaDucks.PIXEL_PER_METER);

        fdef.shape = shape;
        fdef.filter.categoryBits = DeltaDucks.BIT_CANNONS;
        fdef.filter.maskBits = DeltaDucks.BIT_LAND | DeltaDucks.BIT_PLAYER; // BIT_BOUNDARIES won't work here
        fdef.restitution = 0.2f;
        cannonBody.createFixture(fdef).setUserData("Cannon Alive");
    }

    /**
     * Dispose the unwanted cannon and gain player 10 EXP
     */
    public void dispose() {
        world.destroyBody(cannonBody);
        Hud.addScore(10);
    }
}
