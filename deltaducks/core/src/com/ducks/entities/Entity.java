package com.ducks.entities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.ducks.components.RigidBody;
import com.ducks.components.Texture;
import com.ducks.intangibles.EntityData;
import com.ducks.tools.IDrawable;

public abstract class Entity implements IDrawable {
    protected float width;
    protected float height;
    protected float x;
    protected float y;
    protected RigidBody rigidBody;
    protected short mask;
    protected short category;
    protected Texture texture;
    protected float radius;
    protected float scale = 1f;
    protected EntityData data;
    protected boolean isAlive = true;

    public Entity() {
    }

    protected void handleContact(Fixture contactor) { }

    protected void handleSensorContact(Fixture contactor) { }

    /**
     * Updates entity
     * @param deltaTime of game
     */
    public void update(float deltaTime) {
        while (rigidBody.hasContacts())
            handleContact(rigidBody.getContact());
        while (rigidBody.hasSensorContacts())
            handleSensorContact(rigidBody.getSensorContact());
    }

    public void draw() {
        texture.render();
    }

    public Vector2 getPosition() {
        return rigidBody.getBody().getPosition();
    }

    public float getRadius() {
        return radius;
    }

    public Vector2 getVelocity() {
        return rigidBody.getBody().getLinearVelocity();
    }

    public Body getBody() {
        return rigidBody.getBody();
    }

    public void setData(EntityData data) {
        rigidBody.setData(data);
    }

    public EntityData getSensorData() { return rigidBody.getSensorData(); }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean cleanup() { return !isAlive(); }

    public void dispose() {}
}
