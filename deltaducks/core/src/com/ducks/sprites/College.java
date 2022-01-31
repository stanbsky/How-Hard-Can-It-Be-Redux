package com.ducks.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.entities.ListOfCannons;
import com.ducks.screens.MainGameScreen;

public class College extends Sprite {
    public World world;

    private Animation<TextureRegion> collegeIdle;

    private final int PIXEL_COLLEGE_WIDTH = 1024;
    public static final int PIXEL_COLLEGE_HEIGHT = 1024;

    private final float COLLEGE_WIDTH = PIXEL_COLLEGE_WIDTH * .1f;
    public static final float COLLEGE_HEIGHT = PIXEL_COLLEGE_HEIGHT * .1f;

    float stateTime;

    public Body collegeBody;
    ListOfCannons cannons;

    public enum CollegeName {DERWENT};
    private CollegeName collegeName;


    public College(World world, MainGameScreen screen, float spawn_x, float spawn_y, float spawn_radius, CollegeName collegeName, ListOfCannons cannons) {
        super(MainGameScreen.resources.getTexture("college"));
        this.world = world;
        this.collegeName = collegeName;
        this.cannons = cannons;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i=0; i<1; i++) {
            frames.add(new TextureRegion(getTexture(), i * PIXEL_COLLEGE_WIDTH, 0, PIXEL_COLLEGE_WIDTH, PIXEL_COLLEGE_HEIGHT));
        }
        collegeIdle = new Animation(0.1f, frames);
        frames.clear();

        defineCollege(spawn_x, spawn_y, spawn_radius);
        setBounds((spawn_x - COLLEGE_WIDTH / 2f) / DeltaDucks.PIXEL_PER_METER, (spawn_y - COLLEGE_HEIGHT / 2f) / DeltaDucks.PIXEL_PER_METER, COLLEGE_WIDTH / DeltaDucks.PIXEL_PER_METER, COLLEGE_HEIGHT / DeltaDucks.PIXEL_PER_METER);
        setRegion(collegeIdle.getKeyFrame(stateTime, true));
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
        setRegion(collegeIdle.getKeyFrame(stateTime, true));
        if(collegeBody.getFixtureList().get(1).getUserData().toString().contains("Attack")) {
            cannons.spawnBullet(this);
        }
    }

    public void defineCollege(float x, float y, float radius) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x / DeltaDucks.PIXEL_PER_METER, y / DeltaDucks.PIXEL_PER_METER);
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.linearDamping = 1f;
        collegeBody = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius / DeltaDucks.PIXEL_PER_METER);

        fdef.shape = shape;
        fdef.filter.categoryBits = DeltaDucks.BIT_MONSTERS;
        fdef.filter.maskBits = DeltaDucks.BIT_PLAYER;
        fdef.restitution = 0.2f;
        collegeBody.createFixture(fdef).setUserData("College");

        PolygonShape polyShape = new PolygonShape();
        polyShape.setAsBox(radius * 3 / DeltaDucks.PIXEL_PER_METER, radius * 3 / DeltaDucks.PIXEL_PER_METER, new Vector2(0, -5 / DeltaDucks.PIXEL_PER_METER), 0);
        fdef.shape = polyShape;
        fdef.filter.categoryBits = DeltaDucks.BIT_MONSTERS;
        fdef.filter.maskBits = DeltaDucks.BIT_PLAYER;
        fdef.isSensor = true;
        collegeBody.createFixture(fdef).setUserData("College Sensor");
    }

}
