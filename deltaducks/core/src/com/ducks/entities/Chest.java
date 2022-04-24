package com.ducks.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.ducks.DeltaDucks;
import com.ducks.components.ChestAnimation;
import com.ducks.intangibles.EntityData;
import com.ducks.tools.BodyType;
import com.ducks.components.RigidBody;

import static com.ducks.DeltaDucks.scl;
import static com.ducks.tools.FixtureFilter.*;

public class Chest extends Entity {

    private int timeToCollect = 180;
    private boolean playerInRange = false;
    private ChestAnimation animation;

    public Chest(Vector2 position) {
        radius = 50f;
        scale = 1.2f;
        category = SCENERY;
        mask = MASK_ALL - PLAYER_BULLET - ENEMY_BULLET;
        animation = new ChestAnimation(position, scl(radius*scale));
        data = new EntityData(category);
        defineChest(position.scl(0.01f));
        //TODO: we should be setting position in ChestAnimation
        animation.updatePosition(rigidBody.getBody().getPosition());
    }

    public void defineChest(Vector2 position) {
        rigidBody = new RigidBody(position, BodyType.Static, 1f);

        CircleShape shape = new CircleShape();
        shape.setRadius(scl(radius*0.7f));
        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;
        fixture.filter.categoryBits = category;
        fixture.filter.maskBits = mask;
        rigidBody.addFixture(fixture);
        rigidBody.setData(data);

        PolygonShape polyShape = new PolygonShape();
        float side = scl(radius * 4f);
        polyShape.setAsBox(side, side, new Vector2(0, -5 / DeltaDucks.PIXEL_PER_METER), 0);
        fixture.shape = polyShape;
        fixture.filter.categoryBits = category;
        fixture.filter.maskBits = PLAYER;
        rigidBody.addSensor(fixture, category, "Chest Sensor");
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (playerInRange) {
            animation.update(deltaTime);
            timeToCollect -= 1;
        }
    }

    public void draw() {
        animation.render();
    }

    @Override
    protected void handleSensorContact(Fixture contactor) {
        if (EntityData.equals(contactor, PLAYER))
            playerInRange = !playerInRange;
    }

    @Override
    protected void handleContact(Fixture contactor) {
        if (EntityData.equals(contactor, PLAYER))
            playerInRange = !playerInRange;
    }

    @Override
    public boolean isAlive() { return timeToCollect > 0; }

    public void dispose() {
        rigidBody.dispose();
    }
}
