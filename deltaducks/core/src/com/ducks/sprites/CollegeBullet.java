package com.ducks.sprites;

import com.badlogic.gdx.math.Vector2;
import com.ducks.DeltaDucks;
import com.ducks.components.Texture;

public class CollegeBullet extends Bullet {

    private final float BULLET_SPEED = 130f;

    /**
     * Constructor
     */
    public CollegeBullet(Vector2 position, Vector2 direction) {
        this.texture = new Texture("bullet_college", position, radius);
        this.category = DeltaDucks.BIT_CANNONS;
        this.mask = DeltaDucks.BIT_LAND | DeltaDucks.BIT_PLAYER;
        defineBullet(position);
        this.rigidBody.setData("Cannon Alive");
        this.rigidBody.applyForce(direction, BULLET_SPEED);
    }
}
