package com.ducks.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.screens.MainGameScreen;

public class CollegeBullet extends Bullet {

    private Animation<TextureRegion> bulletIdle;

    private final int PIXEL_BULLET_WIDTH = 256;
    private final int PIXEL_BULLET_HEIGHT = 256;

    private final float BULLET_WIDTH = PIXEL_BULLET_WIDTH * .2f;
    private final float BULLET_HEIGHT = PIXEL_BULLET_HEIGHT * .2f;

    private final float BULLET_SPEED = 130f;

    /**
     * Constructor
     */
    public CollegeBullet(Vector2 position, Vector2 direction) {
        super(MainGameScreen.resources.getTexture("mehnat"));

        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(getTexture(), 1 * PIXEL_BULLET_WIDTH, 0 * PIXEL_BULLET_HEIGHT, PIXEL_BULLET_WIDTH, PIXEL_BULLET_HEIGHT));
        bulletIdle = new Animation(0.1f, frames);
        frames.clear();
        setBounds(position.x, position.y, BULLET_WIDTH / DeltaDucks.PIXEL_PER_METER, BULLET_HEIGHT / DeltaDucks.PIXEL_PER_METER);
        setRegion(bulletIdle.getKeyFrame(stateTime, true));

        this.category = DeltaDucks.BIT_CANNONS;
        this.mask = DeltaDucks.BIT_LAND | DeltaDucks.BIT_PLAYER;
        defineBullet(position);
        this.rigidBody.setData("Cannon Alive");
        this.rigidBody.applyForce(direction, BULLET_SPEED);
    }
}
