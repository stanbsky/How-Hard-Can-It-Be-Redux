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
import com.ducks.tools.BodyType;

import static com.ducks.DeltaDucks.scl;
import static com.ducks.tools.FixtureFilter.*;

public class Whirlpool extends Entity {

    private Vector2 position;
    private Array<Body> bodiesInRange;
    private int ticks = 0;
    private final float pullRadius = DifficultyControl.getValue(2f, 3f, 4f);
    private final float minForce = 1f;
    private final float maxForce = DifficultyControl.getValue(3f, 4f, 5f);

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

    public void update(float deltaTime) {
        super.update(deltaTime);
        pullEntities();
        texture.update(deltaTime, position);
    }

    @Override
    protected void handleSensorContact(Fixture contactor) {
        System.out.println("TICK");
        if (EntityData.equals(contactor, PLAYER) || EntityData.equals(contactor, ENEMY)) {
            Body body = contactor.getBody();
            if (bodiesInRange.contains(body, false)) {
                bodiesInRange.removeValue(body, false);
            } else {
                bodiesInRange.add(body);
            }
        }
    }

    private void pullEntities() {
        // Apply pull once every 20 frames
//        if (ticks % 20 != 0) {
//            return;
//        }
        for (Body body : bodiesInRange) {
            Vector2 direction = getDirection(body);
            float distance = getPosition().dst(body.getPosition());
            // At d=0, f=maxForce, at d=pullRadius, f=minForce
            float force = minForce + (maxForce - minForce) * (-1f / pullRadius * distance + 1);
            body.applyForceToCenter(direction.scl(force),true);
        }
    }

    private Vector2 getDirection(Body body) {
        return new Vector2(body.getPosition().x - getPosition().x,
                body.getPosition().y - getPosition().y).nor().scl(-1);
    }

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
}
