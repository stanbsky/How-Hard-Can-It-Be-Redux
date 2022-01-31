package com.ducks.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.screens.MainGameScreen;

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
    public Body bulletBody;

    private final float BULLET_SPEED = 100f;
    private final float BULLET_SPAWN_DURATION = 2f;

    public Cannon(World world, College college, Ship player) {
        super(MainGameScreen.resources.getTexture("mehnat"));
        this.world = world;
        this.college = college;
        this.player = player;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(getTexture(), 1 * PIXEL_BULLET_WIDTH, 0 * PIXEL_BULLET_HEIGHT, PIXEL_BULLET_WIDTH, PIXEL_BULLET_HEIGHT));
        cannonIdle = new Animation(0.1f, frames);
        frames.clear();
//        System.out.println("Yikes");
        setBounds(college.collegeBody.getPosition().x - college.collegeBody.getFixtureList().get(0).getShape().getRadius(), college.collegeBody.getPosition().y - college.collegeBody.getFixtureList().get(0).getShape().getRadius(), BULLET_WIDTH / DeltaDucks.PIXEL_PER_METER, BULLET_HEIGHT / DeltaDucks.PIXEL_PER_METER);
//        setBounds(2, 2, BULLET_WIDTH / DeltaDucks.PIXEL_PER_METER, BULLET_HEIGHT / DeltaDucks.PIXEL_PER_METER);
        setRegion(cannonIdle.getKeyFrame(stateTime, true));
        defineCannon();
//        System.out.println(college.collegeBody.localVector + " " + college.collegeBody.getPosition());
        bulletBody.applyForceToCenter(Crosshair.getDireciton(college.collegeBody.getPosition(), player.b2body.getPosition()).scl(BULLET_SPEED), true);
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
        spawnTimer += deltaTime;
        setPosition(bulletBody.getPosition().x - getWidth()/2, bulletBody.getPosition().y - getHeight()/2);
        if(spawnTimer > BULLET_SPAWN_DURATION) {
            bulletBody.getFixtureList().get(0).setUserData("Cannon Dead");
        }
    }

    public void defineCannon() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX() + getWidth(), getY() + getHeight());
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearDamping = 1f;
        bulletBody = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / DeltaDucks.PIXEL_PER_METER);

        fdef.shape = shape;
        fdef.filter.categoryBits = DeltaDucks.BIT_CANNONS;
        fdef.filter.maskBits = DeltaDucks.BIT_LAND | DeltaDucks.BIT_PLAYER; // BIT_BOUNDARIES won't work here
        fdef.restitution = 0.2f;
        bulletBody.createFixture(fdef).setUserData("Cannon Alive");
    }

    public void dispose() {
        world.destroyBody(bulletBody);
    }
}
