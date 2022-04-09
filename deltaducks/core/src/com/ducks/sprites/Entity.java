package com.ducks.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ducks.DeltaDucks;
import com.ducks.components.RigidBody;
import com.ducks.components.Texture;

import static com.ducks.DeltaDucks.scl;

public class Entity {
    protected float width;
    protected float height;
    protected float x;
    protected float y;
    protected RigidBody rigidBody;
    protected Texture texture;
    protected float radius;
    protected float scale = 1f;

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
}
