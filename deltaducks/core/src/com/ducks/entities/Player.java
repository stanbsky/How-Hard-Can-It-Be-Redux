package com.ducks.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.ducks.components.Shooter;
import com.ducks.intangibles.EntityData;
import com.ducks.managers.PowerupManager;
import com.ducks.tools.InputParser;
import com.ducks.components.ShipAnimation;
import com.ducks.ui.Hud;

import static com.ducks.tools.FixtureFilter.*;

public class Player extends Ship {

    //    private final int SHIP_SPAWN_X = 29;
//    private final int SHIP_SPAWN_Y = 52;
//    private final int SHIP_SPAWN_X = 1370;
//    private final int SHIP_SPAWN_Y = 1340;
    //TODO: reset to one of the above (maybe) for production
    private final int SHIP_SPAWN_X = 1358;
    private final int SHIP_SPAWN_Y = 1563;

    private final float SHIP_FRAME_DURATION = 0.5f;

    private FixtureDef fixture2 = new FixtureDef();
    private Fixture fx;

    private boolean supersized = false;
    private boolean shield = false;

    public Player() {
        super();
        shootWaitTime = 0.3f;
        shooter = new Shooter(shootWaitTime);
        category = PLAYER;
        mask = MASK_ALL - PLAYER_BULLET;

        x = SHIP_SPAWN_X - width/2;
        y = SHIP_SPAWN_Y - height/2;
        //TODO: reset to 1 & 4 for production
        acceleration = 4f;
        max_velocity = 16f;

        // Set up ShipAnimation
        direction = 6;
        moving = false;
        animation = new ShipAnimation("player", new Vector2(x, y), radius*scale, SHIP_FRAME_DURATION);
        data = new EntityData(category);

        defineShip();
    }

    @Override
    public void defineShip() {
        super.defineShip();


        CircleShape shape = new CircleShape();
        shape.setRadius(radius*1.5f);
        fixture2.shape = shape;
        fixture2.filter.categoryBits = category;
        fixture2.filter.maskBits = 0;
        fx = rigidBody.addFixture(fixture2);
    }

    private void toggleSize(boolean large) {
        rigidBody.getBody().destroyFixture(fx);
        if (large) {
            fixture2.filter.maskBits = MASK_ALL - PLAYER - PLAYER_BULLET;
            animation.changeSize(1.5f);
        } else {
            fixture2.filter.maskBits = 0;
            animation.changeSize((1f/1.5f));
        }
        rigidBody.addFixture(fixture2);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        parseDirection(InputParser.parseInput());
        applyForce();
        if (PowerupManager.quickshotAcitve()) {
            shooter.skipShootTimer();
        }
        if (PowerupManager.supersizeAcitve() != supersized) {
            toggleSize(!supersized);
            supersized = !supersized;
        }
        if (PowerupManager.shieldAcitve()) {
            if (!shield) {
                animation.setColor(0.7f,0.8f,1f,1);
                shield = true;
            }
        }
        animation.update(deltaTime, getPosition(), direction, moving);
//        Debug.debug(getPosition());
    }

    @Override
    protected void handleContact(Fixture contactor) {
        if (EntityData.equals(contactor, ENEMY_BULLET)) {
            sufferHit();
        }
    }

    private void sufferHit() {
        if (PowerupManager.supersizeAcitve()) {

        } else if (PowerupManager.shieldAcitve()) {
            PowerupManager.shieldUsed();
            if (!PowerupManager.shieldAcitve()) {
                animation.removeColor();
                shield = false;
            }
        } else {
            Hud.decHealth();
        }
    }
}
