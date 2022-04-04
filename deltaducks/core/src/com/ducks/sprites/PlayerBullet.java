package com.ducks.sprites;

import com.badlogic.gdx.math.Vector2;
import com.ducks.DeltaDucks;
import com.ducks.components.Texture;

/***
 * Bullet Class for Box2D Body and Sprite
 */
public class PlayerBullet extends Bullet {

    private final float BULLET_SPEED = 200f;

    /**
     * Constructor
     */
    public PlayerBullet(Vector2 position, Vector2 direction, Vector2 shipMomentum) {
        this.texture = new Texture("bullet_player", position, radius);
        this.mask = DeltaDucks.BIT_PIRATES | DeltaDucks.BIT_LAND | DeltaDucks.BIT_BOUNDARY;
        this.category = DeltaDucks.BIT_BULLETS;
        defineBullet(position);
        this.rigidBody.setData("Bullet Alive");
        this.rigidBody.applyForce(shipMomentum, 55f);
        this.rigidBody.applyForce(direction, BULLET_SPEED);
    }

}
