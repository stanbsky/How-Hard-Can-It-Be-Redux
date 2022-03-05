package com.ducks.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.ducks.entities.PhysicsManager;

public class RigidBody {

    private BodyDef bodyDef;
    private FixtureDef fixtureDef;

    public int getBodyId() {
        return bodyId;
    }

    private int bodyId;

    public Body getBody() {
        return PhysicsManager.box2DBodies.get(this.bodyId);
    }

    public RigidBody(Shape shape, Vector2 position, short category, short mask,
                     BodyType type, float damping, String data) {
        this.bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = type.getType();
        bodyDef.linearDamping = damping;

        this.fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = category;
        fixtureDef.filter.maskBits = mask;
        fixtureDef.restitution = 0.2f;
        this.bodyId = PhysicsManager.createBody(bodyDef, fixtureDef, data);
    }

    public void dispose() {
        PhysicsManager.destroyBody(this.bodyId);
    }
}
