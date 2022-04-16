package com.ducks.managers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public final class PhysicsManager {
    public static World box2DWorld;
    public static ArrayList<Body> box2DBodies;

    public static void Initialize(World world) {
        box2DWorld = world;
        box2DBodies = new ArrayList<>();
    }

    public static int createBody(BodyDef bodyDef, FixtureDef fixtureDef) {
        Body body = box2DWorld.createBody(bodyDef);
        body.createFixture(fixtureDef);
        box2DBodies.add(body);
        return box2DBodies.size() - 1;
    }

    public static int createBody(BodyDef bodyDef) {
        box2DBodies.add(box2DWorld.createBody(bodyDef));
        return box2DBodies.size() - 1;
    }

    public static void destroyBody(int bodyId) {
        box2DWorld.destroyBody(box2DBodies.get(bodyId));
    }
}
