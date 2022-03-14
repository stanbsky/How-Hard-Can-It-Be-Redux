package com.ducks.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.ducks.DeltaDucks;
import com.ducks.components.BodyType;
import com.ducks.components.RigidBody;

public class Bullet extends Sprite {
    private final float BULLET_SPAWN_DURATION = 2f;
    protected RigidBody rigidBody;
    float stateTime;
    float spawnTimer;
    short category;
    short mask;

    public Bullet(Texture texture) {
        super(texture);
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
        setPosition(bulletBody.getPosition().x - getWidth() / 2, bulletBody.getPosition().y - getHeight() / 2);
        if (spawnTimer > BULLET_SPAWN_DURATION) {
            this.rigidBody.setData("Bullet Dead");
        }
        /*
         Why is this even a thing? Killing bullet once it's outside FOV
        if(!gameCam.frustum.pointInFrustum(new Vector3(bulletBody.getPosition().x, bulletBody.getPosition().y, 0))) {
            bulletBody.getFixtureList().get(0).setUserData("Bullet Dead");
        }
        */
    }

    /**
     * Define the Box2D body and fixture and map it onto the Box2D world
     */
    public void defineBullet(Vector2 position) {
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / DeltaDucks.PIXEL_PER_METER);
        this.rigidBody = new RigidBody(shape, position, category, mask, BodyType.Dynamic, 0.5f);
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
