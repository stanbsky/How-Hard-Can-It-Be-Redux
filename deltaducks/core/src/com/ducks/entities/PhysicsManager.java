package com.ducks.entities;

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

    public static int createBody(BodyDef bodyDef, FixtureDef fixtureDef, String data) {
        Body body = box2DWorld.createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData(data);
        box2DBodies.add(body);
        return box2DBodies.size() - 1;
    }

    public static void destroyBody(int bodyId) {
        box2DWorld.destroyBody(box2DBodies.get(bodyId));
    }
}
