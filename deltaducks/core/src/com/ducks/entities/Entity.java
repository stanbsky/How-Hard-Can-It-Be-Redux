package com.ducks.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.ducks.components.RigidBody;
import com.ducks.components.Texture;
import com.ducks.intangibles.EntityData;

public class Entity {
    protected float width;
    protected float height;
    protected float x;
    protected float y;
    protected RigidBody rigidBody;
    protected Texture texture;
    protected float radius;
    protected float scale = 1f;
    protected TextureAtlas atlas;
    protected EntityData data;

    public Entity() {
    }

    public void draw(SpriteBatch batch, TextureRegion texture) {
        batch.draw(texture, x, y, width, height);
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

//    public String getData() { return rigidBody.getData(); }

    public boolean isAlive() { return rigidBody.getData().isAlive; }

    public boolean isHit() { return rigidBody.getData().isHit(); }

    public EntityData getSensorData() { return rigidBody.getSensorData(); }
}
