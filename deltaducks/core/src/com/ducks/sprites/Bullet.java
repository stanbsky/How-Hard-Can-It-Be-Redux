package com.ducks.sprites;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.components.RigidBody;
import com.ducks.screens.MainGameScreen;
import com.ducks.components.BodyType;

/***
 * Bullet Class for Box2D Body and Sprite
 */
public class Bullet extends Sprite {

    private Animation<TextureRegion> bulletIdle;

    private final int PIXEL_BULLET_WIDTH = 256;
    private final int PIXEL_BULLET_HEIGHT = 256;

    private final float BULLET_WIDTH = PIXEL_BULLET_WIDTH * .2f;
    private final float BULLET_HEIGHT = PIXEL_BULLET_HEIGHT * .2f;

    float stateTime;
    float spawnTimer;

    private final float BULLET_SPEED = 200f;
    private final float BULLET_SPAWN_DURATION = 2f;
    OrthographicCamera gameCam;
    private RigidBody rigidBody;

    /**
     * Constructor
     */
    public Bullet(Vector2 position, Vector2 direction) {
        super(MainGameScreen.resources.getTexture("mehnat"));

        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(getTexture(), 0 * PIXEL_BULLET_WIDTH, 0 * PIXEL_BULLET_HEIGHT, PIXEL_BULLET_WIDTH, PIXEL_BULLET_HEIGHT));
        bulletIdle = new Animation(0.1f, frames);
        frames.clear();
        setBounds(position.x, position.y, BULLET_WIDTH / DeltaDucks.PIXEL_PER_METER, BULLET_HEIGHT / DeltaDucks.PIXEL_PER_METER);
        setRegion(bulletIdle.getKeyFrame(stateTime, true));

        defineBullet(position);
        this.rigidBody.setData("Bullet Alive");
        this.rigidBody.applyForce(direction, BULLET_SPEED);
    }

    /**
     * Update the bullet every delta time interval
     * @param deltaTime of the game
     */
    public void update(float deltaTime) {
        Body bulletBody = this.getBody();
        stateTime += deltaTime;
        spawnTimer += deltaTime;
        setPosition(bulletBody.getPosition().x - getWidth()/2, bulletBody.getPosition().y - getHeight()/2);
        if(spawnTimer > BULLET_SPAWN_DURATION) {
            this.rigidBody.setData("Bullet Dead");
        }
        /*
         Why is this even a thing? Killing bullet once it's outside FOV
        if(!gameCam.frustum.pointInFrustum(new Vector3(bulletBody.getPosition().x, bulletBody.getPosition().y, 0))) {
            bulletBody.getFixtureList().get(0).setUserData("Bullet Dead");
        }
        */
    }

    /**
     * Define the Box2D body and fixture and map it onto the Box2D world
     */
    public void defineBullet(Vector2 position) {
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / DeltaDucks.PIXEL_PER_METER);
        short mask = DeltaDucks.BIT_PIRATES | DeltaDucks.BIT_LAND | DeltaDucks.BIT_BOUNDARY;
        this.rigidBody = new RigidBody(shape, position, DeltaDucks.BIT_BULLETS,
                mask, BodyType.Dynamic, 0.5f);
    }

    /**
     * Dispose the unwanted bullet
     */
    public void dispose() {
        this.rigidBody.dispose();
    }

    public Body getBody() {
        return this.rigidBody.getBody();
    }

}
