package com.ducks.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.screens.MainGameScreen;

public class Bullet extends Sprite {
    private World world;
    private Ship player;

    private Animation<TextureRegion> wormIdle;

    private final int PIXEL_WORM_WIDTH = 256;
    private final int PIXEL_WORM_HEIGHT = 256;

    private final float WORM_WIDTH = PIXEL_WORM_WIDTH * .2f;
    private final float WORM_HEIGHT = PIXEL_WORM_HEIGHT * .2f;

    float stateTime;
    public Body bulletBody;


    public Bullet(World world, Ship player) {
        super(MainGameScreen.resources.getTexture("mehnat"));
        this.world = world;
        this.player = player;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(getTexture(), 0 * PIXEL_WORM_WIDTH, 0 * PIXEL_WORM_HEIGHT, PIXEL_WORM_WIDTH, PIXEL_WORM_HEIGHT));
        wormIdle = new Animation(0.1f, frames);
        frames.clear();
        setBounds(player.b2body.getPosition().x, player.b2body.getPosition().y, WORM_WIDTH / DeltaDucks.PIXEL_PER_METER, WORM_HEIGHT / DeltaDucks.PIXEL_PER_METER);
        setRegion(wormIdle.getKeyFrame(stateTime, true));

        defineBullet();
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;

//        if (getX() < 9 && getY() < 4.5) {
//            setPosition(getX() + deltaTime, getY() + deltaTime);
//        }
        setPosition(bulletBody.getPosition().x -getWidth()/2, bulletBody.getPosition().y -getHeight()/2);
//
//        setPosition(Crosshair.getCrosshairX(), Crosshair.getCrosshairY());
//        System.out.println(Crosshair.getCrosshairX() + " "+ Crosshair.getCrosshairY());
    }

    public void defineBullet() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(2, 2);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearDamping = 1f;
        bulletBody = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / DeltaDucks.PIXEL_PER_METER);

        fdef.shape = shape;
        fdef.filter.categoryBits = DeltaDucks.BIT_PIRATES;
        fdef.filter.maskBits = DeltaDucks.BIT_PLAYER | DeltaDucks.BIT_PIRATES | DeltaDucks.BIT_LAND;
        fdef.restitution = 0.2f;
        bulletBody.createFixture(fdef).setUserData("Bullet");
    }
}
