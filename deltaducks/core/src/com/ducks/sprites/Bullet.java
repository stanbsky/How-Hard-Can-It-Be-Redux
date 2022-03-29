package com.ducks.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.ducks.DeltaDucks;
import com.ducks.components.BodyType;
import com.ducks.components.RigidBody;
import com.ducks.components.Texture;

public class Bullet {
    private final float BULLET_SPAWN_DURATION = 2f;
    protected RigidBody rigidBody;
    float stateTime;
    float spawnTimer;
    float radius = 10 / DeltaDucks.PIXEL_PER_METER;
    short category;
    short mask;
    Texture texture;

    public Bullet() {
    }

    /**
     * Update the bullet every delta time interval
     *
     * @param deltaTime of the game
     */
    public void update(float deltaTime) {
        Body bulletBody = this.getBody();
        stateTime += deltaTime;
        spawnTimer += deltaTime;
        this.texture.update(deltaTime, bulletBody.getPosition());
        if (spawnTimer > BULLET_SPAWN_DURATION) {
            this.rigidBody.setData("Bullet Dead");
        }
    }

    /**
     * Define the Box2D body and fixture and map it onto the Box2D world
     */
    public void defineBullet(Vector2 position) {
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        this.rigidBody = new RigidBody(shape, position, category, mask, BodyType.Dynamic, 0.5f);
    }

    public void draw(SpriteBatch batch) {
        this.texture.render(batch);
    }

    /**
     * Dispose the unwanted bullet
     */
    public void dispose() {
        this.rigidBody.dispose();
    }

    public Body getBody() {
        return this.rigidBody.getBody();
    }
}
