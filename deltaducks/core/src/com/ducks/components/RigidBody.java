package com.ducks.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.ducks.intangibles.EntityData;
import com.ducks.managers.PhysicsManager;
import com.ducks.tools.BodyType;

public class RigidBody {

    private BodyDef bodyDef;
    private FixtureDef fixtureDef;
    private boolean hasSensor = false;

    private int bodyId;

    public Body getBody() {
        return PhysicsManager.box2DBodies.get(this.bodyId);
    }

    @Deprecated
    public RigidBody(Shape shape, Vector2 position, short category, short mask,
                     BodyType type, float damping) {
        this.bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = type.getType();
        bodyDef.linearDamping = damping;

        this.fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = category;
        fixtureDef.filter.maskBits = mask;
        fixtureDef.restitution = 0.2f;
        this.bodyId = PhysicsManager.createBody(bodyDef, fixtureDef);
    }

    /**
     * Creation of a RigidBody
     * @param position of body
     * @param type of body
     * @param damping slowdown when moving
     */
    public RigidBody(Vector2 position, BodyType type, float damping) {
        this.bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = type.getType();
        bodyDef.linearDamping = damping;

        this.bodyId = PhysicsManager.createBody(bodyDef);
    }

    /**
     * Adds new fixture
     * @param fixture to add
     * @return fixture that has been added
     */
    public Fixture addFixture(FixtureDef fixture) {
        fixture.restitution = 0.2f;
        return getBody().createFixture(fixture);
    }

    /**
     * Adds new sensor
     * @param fixture to use for the sensor
     * @param category of sensor
     * @param name of sensor
     */
    public void addSensor(FixtureDef fixture, short category, String name) {
        hasSensor = true;
        fixture.isSensor = true;
        getBody().createFixture(fixture).setUserData(new EntityData(category, name));
    }

    /**
     * Sets new data
     * @param data to set
     */
    public void setData(EntityData data) {
        getBody().getFixtureList().get(0).setUserData(data);
    }

    public EntityData getData() { return (EntityData) getBody().getFixtureList().get(0).getUserData(); }

    public EntityData getSensorData() { return (EntityData) getBody().getFixtureList().get(1).getUserData(); }

    public boolean hasContacts() { return getData().hasContacts(); }

    public Fixture getContact() { return getData().getContact(); }

    public boolean hasSensorContacts() { return hasSensor && getSensorData().hasContacts(); }

    public Fixture getSensorContact() { return getSensorData().getContact(); }

    public void applyForce(Vector2 direction, float speed) {
        getBody().applyForceToCenter(direction.scl(speed), true);
    }

    public void dispose() {
        PhysicsManager.destroyBody(this.bodyId);
    }
}
