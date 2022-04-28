package com.ducks.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.ducks.components.RigidBody;
import com.ducks.components.Texture;
import com.ducks.intangibles.EntityData;
import com.ducks.tools.BodyType;

import static com.ducks.DeltaDucks.scl;
import static com.ducks.tools.FixtureFilter.*;

public class Whirlpool extends Entity {

    private Vector2 position;

    public Whirlpool(Vector2 position) {
        radius = 50f;
        scale = 1f;
        category = SCENERY;
        mask = 0;
        this.position = position;
        texture = new Texture("whirlpool", position, scl(radius*scale), 45f, 0.15f);
        data = new EntityData(category);
        defineWhirlpool(position.scl(0.01f));
    }

    public void update(float deltaTime) {
        texture.update(deltaTime, position);
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
        shape.setRadius(scl(radius * 4f));
        fixture.shape = shape;
        fixture.filter.categoryBits = category;
        fixture.filter.maskBits = PLAYER + ENEMY;
        rigidBody.addSensor(fixture, category, "Whirlpool Sensor");
    }
}
