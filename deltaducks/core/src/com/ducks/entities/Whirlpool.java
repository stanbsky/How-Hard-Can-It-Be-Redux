package com.ducks.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.ducks.components.RigidBody;
import com.ducks.components.Texture;
import com.ducks.intangibles.DifficultyControl;
import com.ducks.intangibles.EntityData;
import com.ducks.managers.EntityManager;
import com.ducks.tools.BodyType;

import static com.ducks.DeltaDucks.scl;
import static com.ducks.tools.FixtureFilter.*;

public class Whirlpool extends Entity {

    private final Vector2 position;
    private final Array<Body> bodiesInRange;
    private int timer = 30 * 60; // 30 seconds before the whirlpool disappears
    private final float pullRadius = DifficultyControl.getValue(2f, 3f, 4f);
    private final float minForce = 1f;
    private final float maxForce = DifficultyControl.getValue(3f, 4f, 5f);

    /**
     * Defines whirlpool
     * @param position of whirlpool
     */
    public Whirlpool(Vector2 position) {
        radius = 50f;
        scale = 0.7f;
        category = SCENERY;
        mask = 0;
        this.position = position;
        texture = new Texture("whirlpool", position, scl(radius*scale), 45f, 0.15f);
        data = new EntityData(category);
        defineWhirlpool(position.scl(0.01f));

        bodiesInRange = new Array<>();
    }

    /**
     * Updates whirlpool
     * reduces timer and advances animation
     * @param deltaTime of game
     */
    public void update(float deltaTime) {
        super.update(deltaTime);
        pullEntities();
        timer--;
        texture.update(deltaTime, position);
    }

    /**
     * Tags ships to effect with pull
     * @param contactor of whirlpool
     */
    @Override
    protected void handleSensorContact(Fixture contactor) {
        if (EntityData.equals(contactor, PLAYER) || EntityData.equals(contactor, ENEMY)) {
            Body body = contactor.getBody();
            if (bodiesInRange.contains(body, false)) {
                bodiesInRange.removeValue(body, false);
            } else {
                bodiesInRange.add(body);
            }
        }
    }

    /**
     * Applies pull on relevant ships
     */
    private void pullEntities() {
        for (Body body : bodiesInRange) {
            Vector2 direction = getDirection(body);
            float distance = getPosition().dst(body.getPosition());
            float force = minForce + (maxForce - minForce) * (-1f / pullRadius * distance + 1);
            body.applyForceToCenter(direction.scl(force),true);
        }
    }

    /**
     * Finds direction for force to apply in
     * @param body of object pulled in
     * @return direction to center of whirlpool
     */
    private Vector2 getDirection(Body body) {
        return new Vector2(body.getPosition().x - getPosition().x,
                body.getPosition().y - getPosition().y).nor().scl(-1);
    }

    public boolean cleanup() {
        return timer <= 0;
    }

    /**
     * Defines whirlpool fixture and sensor
     * @param position of whirlpool/fixtures
     */
    public void defineWhirlpool(Vector2 position) {
        rigidBody = new RigidBody(position, BodyType.Static, 1f);

        CircleShape shape = new CircleShape();
        shape.setRadius(scl(radius*0.7f));
        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;
        fixture.filter.categoryBits = category;
        fixture.filter.maskBits = 0;
        rigidBody.addFixture(fixture);
        rigidBody.setData(data);

        shape = new CircleShape();
        shape.setRadius(pullRadius);
        fixture.shape = shape;
        fixture.filter.categoryBits = category;
        fixture.filter.maskBits = MASK_ALL - PLAYER_BULLET - ENEMY_BULLET;
        rigidBody.addSensor(fixture, category, "Whirlpool Sensor");
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
