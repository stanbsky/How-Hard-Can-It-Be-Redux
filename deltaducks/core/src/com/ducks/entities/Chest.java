package com.ducks.entities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.ducks.DeltaDucks;
import com.ducks.intangibles.EntityData;
import com.ducks.tools.BodyType;
import com.ducks.components.RigidBody;
import com.ducks.components.Texture;
import com.ducks.tools.Debug;

import static com.ducks.DeltaDucks.scl;
import static com.ducks.tools.FixtureFilter.*;

public class Chest extends Entity {

    private int timeToCollect = 180;
    private boolean playerInRange = false;

    public Chest(Vector2 position, TextureAtlas atlas) {
        this.atlas = atlas;
        radius = 50f;
        scale = 2f;
        category = SCENERY;
        mask = MASK_ALL - PLAYER_BULLET - ENEMY_BULLET;
        texture = new Texture("warning256", position, scl(radius*scale), atlas);
        data = new EntityData(category);
        defineChest(position.scl(0.01f));
        System.out.println(position.scl(0.01f));
    }

    public void defineChest(Vector2 position) {
        rigidBody = new RigidBody(position, BodyType.Static, 1f);

        CircleShape shape = new CircleShape();
        shape.setRadius(scl(radius));
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
        Debug.debug(playerInRange);
        Debug.debug(timeToCollect);
        texture.update(deltaTime, rigidBody.getBody().getPosition());
        if (playerInRange)
            timeToCollect--;
    }

    @Override
    protected void handleSensorContact(Fixture contactor) {
        if (EntityData.equals(contactor, PLAYER))
            playerInRange = !playerInRange;
    }

    public boolean isCompleted() { return timeToCollect < 0; }

    public void dispose() {
        rigidBody.dispose();
    }
}
