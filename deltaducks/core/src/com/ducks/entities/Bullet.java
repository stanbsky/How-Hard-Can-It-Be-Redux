package com.ducks.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.ducks.intangibles.DifficultyControl;
import com.ducks.tools.BodyType;
import com.ducks.components.RigidBody;
import com.ducks.components.Texture;

import static com.ducks.DeltaDucks.scl;

public class Bullet extends Entity {
    float stateTime;
    float spawnTimer;
    float radius = scl(10);
    short category;
    short mask;
    Texture texture;

    public Bullet() {
    }

    /**
     * Update the bullet every delta time interval
     * @param deltaTime of the game
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        stateTime += deltaTime;
        spawnTimer += deltaTime;
        this.texture.update(deltaTime, getPosition());
        float BULLET_SPAWN_DURATION = 3f;
        if (spawnTimer > BULLET_SPAWN_DURATION)
            isAlive = false;
    }

    /**
     * Kills the bullet upon collision
     * @param contactor to bullet
     */
    @Override
    protected void handleContact(Fixture contactor) {
        if (!contactor.isSensor()) {
            isAlive = false;
        }

    }

    /**
     * Define the bullet
     * @param position of bullet
     */
    public void defineBullet(Vector2 position) {
        rigidBody = new RigidBody(position, BodyType.Dynamic, 0.25f);
        FixtureDef fixture = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        fixture.shape = shape;
        fixture.filter.categoryBits = category;
        fixture.filter.maskBits = mask;
        rigidBody.addFixture(fixture);
    }

    public void draw() {
        this.texture.render();
    }

    /**
     * Dispose the unwanted bullet
     */
    public void dispose() {
        this.rigidBody.dispose();
    }

    public Vector2 getPosition() {
        return rigidBody.getBody().getPosition();
    }
}
