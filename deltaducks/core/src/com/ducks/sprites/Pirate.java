package com.ducks.sprites;

import com.badlogic.gdx.math.Vector2;
import com.ducks.DeltaDucks;
import com.ducks.components.ShipAnimation;
import com.ducks.tools.InputParser;

public class Pirate extends Ship {

    public Pirate(String college, float spawn_x, float spawn_y) {
        super();
        mask = DeltaDucks.BIT_PLAYER | DeltaDucks.BIT_PIRATES | DeltaDucks.BIT_LAND | DeltaDucks.BIT_BOUNDARY;
        category = DeltaDucks.BIT_PIRATES;

        x = spawn_x - width / 2;
        y = spawn_y - height / 2;
        acceleration = 2f;
        max_velocity = 8f;

        // Set up ShipAnimation
        direction = 6;
        moving = false;
        animation = new ShipAnimation(college, new Vector2(x, y), radius*scale, 0.5f);
        data = "Pirate";

        defineShip();
    }

    public void update(float deltaTime) {
        parseDirection(InputParser.fakeInput(0.3f));
        moving = false;
        super.update(deltaTime);
    }
}
