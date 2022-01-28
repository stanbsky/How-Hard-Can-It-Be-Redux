package com.ducks.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.screens.MainGameScreen;

public class Monsters extends Sprite {

    public World world;
    private Animation <TextureRegion> wormIdle;

    private final int PIXEL_WORM_WIDTH = 90;
    private final int PIXEL_WORM_HEIGHT = 90;

    private final int WORM_WIDTH = 90;
    private final int WORM_HEIGHT = 90;

    private final int SPAWN_X = 200;
    private final int SPAWN_Y = 400;
    private final int SPAWN_RADIUS = 6*2;

    float stateTime;

    public Monsters(World world, MainGameScreen screen) {
        super(MainGameScreen.resources.getTexture("worm"));
        this.world = world;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i=0; i<9; i++) {
            frames.add(new TextureRegion(getTexture(), i * PIXEL_WORM_WIDTH, 0, PIXEL_WORM_WIDTH, PIXEL_WORM_HEIGHT));
        }
        wormIdle = new Animation(0.1f, frames);
        frames.clear();

        defineMonster(SPAWN_X, SPAWN_Y, SPAWN_RADIUS);
        setBounds((SPAWN_X - WORM_WIDTH / 2f) / DeltaDucks.PIXEL_PER_METER, (SPAWN_Y - WORM_HEIGHT / 2f) / DeltaDucks.PIXEL_PER_METER, WORM_WIDTH / DeltaDucks.PIXEL_PER_METER, WORM_HEIGHT / DeltaDucks.PIXEL_PER_METER);
        setRegion(wormIdle.getKeyFrame(stateTime, true));
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
        setRegion(wormIdle.getKeyFrame(stateTime, true));
    }

    public void defineMonster(int x, int y, int radius) {
        Body monsterBody;
        BodyDef bdef = new BodyDef();
        bdef.position.set(x / DeltaDucks.PIXEL_PER_METER, y / DeltaDucks.PIXEL_PER_METER);
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.linearDamping = 1f;
        monsterBody = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius / DeltaDucks.PIXEL_PER_METER);

        fdef.shape = shape;
        fdef.filter.categoryBits = DeltaDucks.BIT_MONSTERS;
        fdef.filter.maskBits = DeltaDucks.BIT_PLAYER;
        fdef.restitution = 0.2f;
        monsterBody.createFixture(fdef).setUserData("Monster");

        PolygonShape polyShape = new PolygonShape();
        polyShape.setAsBox(radius * 4 / DeltaDucks.PIXEL_PER_METER, radius * 4 / DeltaDucks.PIXEL_PER_METER, new Vector2(0, -5 / DeltaDucks.PIXEL_PER_METER), 0);
        fdef.shape = polyShape;
        fdef.filter.categoryBits = DeltaDucks.BIT_MONSTERS;
        fdef.filter.maskBits = DeltaDucks.BIT_PLAYER;
        fdef.isSensor = true;
        monsterBody.createFixture(fdef).setUserData("Monster Sensor");
    }
}
