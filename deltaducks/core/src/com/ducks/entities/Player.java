package com.ducks.entities;

import com.badlogic.gdx.math.Vector2;
import com.ducks.intangibles.EntityData;
import com.ducks.tools.InputParser;
import com.ducks.components.ShipAnimation;
import static com.ducks.tools.FixtureFilter.*;

public class Player extends Ship {

    //    private final int SHIP_SPAWN_X = 29;
//    private final int SHIP_SPAWN_Y = 52;
//    private final int SHIP_SPAWN_X = 1370;
//    private final int SHIP_SPAWN_Y = 1340;
    //TODO: reset to one of the above (maybe) for production
    private final int SHIP_SPAWN_X = 3358;
    private final int SHIP_SPAWN_Y = 5563;

    private final float SHIP_FRAME_DURATION = 0.5f;

    public Player() {
        super();
        category = PLAYER;
        mask = MASK_ALL - PLAYER_BULLET;

        x = SHIP_SPAWN_X - width/2;
        y = SHIP_SPAWN_Y - height/2;
        //TODO: reset to 1 & 4 for production
        acceleration = 10f;
        max_velocity = 40f;

        // Set up ShipAnimation
        direction = 6;
        moving = false;
        animation = new ShipAnimation("player", new Vector2(x, y), radius*scale, SHIP_FRAME_DURATION);
        data = new EntityData("Player");

        defineShip();
    }

    public void update(float deltaTime) {
        parseDirection(InputParser.parseInput());
        applyForce();
        animation.update(deltaTime, getPosition(), direction, moving);
        super.update();
    }
}
