package com.ducks.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.screens.MainGameScreen;

/***
 * Bullet Class for Box2D Body and Sprite
 */
public class PlayerBullet extends Bullet {

    private Animation<TextureRegion> bulletIdle;

    private final int PIXEL_BULLET_WIDTH = 256;
    private final int PIXEL_BULLET_HEIGHT = 256;

    private final float BULLET_WIDTH = PIXEL_BULLET_WIDTH * .2f;
    private final float BULLET_HEIGHT = PIXEL_BULLET_HEIGHT * .2f;

    private final float BULLET_SPEED = 200f;

    /**
     * Constructor
     */
    public PlayerBullet(Vector2 position, Vector2 direction, Vector2 shipMomentum) {
        super(MainGameScreen.resources.getTexture("mehnat"));
        //this.player = player;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(getTexture(), 0 * PIXEL_BULLET_WIDTH, 0 * PIXEL_BULLET_HEIGHT, PIXEL_BULLET_WIDTH, PIXEL_BULLET_HEIGHT));
        bulletIdle = new Animation(0.1f, frames);
        frames.clear();
        setBounds(position.x, position.y, BULLET_WIDTH / DeltaDucks.PIXEL_PER_METER, BULLET_HEIGHT / DeltaDucks.PIXEL_PER_METER);
        setRegion(bulletIdle.getKeyFrame(stateTime, true));

        this.mask = DeltaDucks.BIT_PIRATES | DeltaDucks.BIT_LAND | DeltaDucks.BIT_BOUNDARY;
        this.category = DeltaDucks.BIT_BULLETS;
        defineBullet(position);
        this.rigidBody.setData("Bullet Alive");
        this.rigidBody.applyForce(shipMomentum, 1f);
        this.rigidBody.applyForce(direction, BULLET_SPEED);
    }

}
