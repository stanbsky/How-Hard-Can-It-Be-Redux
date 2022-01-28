package com.ducks.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.screens.MainGameScreen;

public class Pirates extends Sprite {

    public World world;
    public Array <Body> pirateBodies;

    private Animation<TextureRegion> pirateIdle;

    private final int PIXEL_PIRATE_WIDTH = 716;
    private final int PIXEL_PIRATE_HEIGHT = 811;

    private final float PIRATE_WIDTH = 32*2f;
    private final float PIRATE_HEIGHT = 32*2f;

    private final int NUMBER_OF_PIRATES = 10;

    float stateTime;
    Body pirateBody;

    public Pirates(World world, MainGameScreen screen, int x, int y, int r) {
        super(MainGameScreen.resources.getTexture("pirate"));
        this.world = world;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i=0; i<1; i++) {
            frames.add(new TextureRegion(getTexture(), i * PIXEL_PIRATE_WIDTH, 0, PIXEL_PIRATE_WIDTH, PIXEL_PIRATE_HEIGHT));
        }

        pirateIdle  = new Animation(0.1f, frames);
        frames.clear();


        pirateBodies = new Array<Body>();

        definePirates(x, y, r);
        setBounds(0, 0, PIRATE_WIDTH / DeltaDucks.PIXEL_PER_METER, PIRATE_HEIGHT / DeltaDucks.PIXEL_PER_METER);
        setRegion(pirateIdle.getKeyFrame(stateTime, true));

//        spawnPirates();
    }

//    public void spawnPirates() {
//        for(int i = 0; i < NUMBER_OF_PIRATES; i++) {
//            pirateBodies.add(definePirates(32*i, 32*i, 6*2));
//            Hud.addScore(200);
//        }
//    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
        setPosition(pirateBody.getPosition().x - getWidth() / 2, pirateBody.getPosition().y - getHeight() / 2);
        setRegion(pirateIdle.getKeyFrame(stateTime, true));
    }

    public void definePirates(int x, int y, int radius) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x / DeltaDucks.PIXEL_PER_METER, y / DeltaDucks.PIXEL_PER_METER);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearDamping = 1f;
        pirateBody = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius / DeltaDucks.PIXEL_PER_METER);

        fdef.shape = shape;
        fdef.filter.categoryBits = DeltaDucks.BIT_PIRATES;
        fdef.filter.maskBits = DeltaDucks.BIT_PLAYER | DeltaDucks.BIT_PIRATES | DeltaDucks.BIT_LAND;
        fdef.restitution = 0.2f;
        pirateBody.createFixture(fdef).setUserData("Pirates");
    }
}
