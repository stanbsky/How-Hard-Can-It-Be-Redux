package com.ducks.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.ducks.tools.BodyType;
import com.ducks.components.RigidBody;
import com.ducks.components.Texture;

import static com.ducks.DeltaDucks.scl;
import static com.ducks.tools.FixtureFilter.PLAYER;

public class Bullet extends Entity {
    private final float BULLET_SPAWN_DURATION = 2f;
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
     *
     * @param deltaTime of the game
     */
    public void update(float deltaTime) {
        stateTime += deltaTime;
        spawnTimer += deltaTime;
        this.texture.update(deltaTime, getPosition());
        if (spawnTimer > BULLET_SPAWN_DURATION) {
            setData("Bullet Dead");
        }
    }

    /**
     * Define the Box2D body and fixture and map it onto the Box2D world
     */
    public void defineBullet(Vector2 position) {
        rigidBody = new RigidBody(position, BodyType.Dynamic, 0.5f);
        FixtureDef fixture = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        fixture.shape = shape;
        fixture.filter.categoryBits = category;
        fixture.filter.maskBits = mask;
        rigidBody.addFixture(fixture);
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

    public Vector2 getPosition() {
        return rigidBody.getBody().getPosition();
    }
}
