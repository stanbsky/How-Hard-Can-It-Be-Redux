package com.ducks.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.ducks.DeltaDucks;
import com.ducks.components.Texture;
import com.ducks.screens.MainGameScreen;

/***
 * Bullet Class for Box2D Body and Sprite
 */
public class PlayerBullet extends Bullet {

    private final float BULLET_SPEED = 200f;
    private final String data = "Bullet Alive";

    public PlayerBullet(Vector2 position, Vector2 direction, Vector2 shipMomentum) {
        this(position, direction, shipMomentum, MainGameScreen.getAtlas());
    }

    public void update(float deltaTime) {
        super.update(deltaTime);
        System.out.println(getPosition());
    }
    /**
     * Constructor
     */
    public PlayerBullet(Vector2 position, Vector2 direction, Vector2 shipMomentum, TextureAtlas atlas) {
        this.texture = new Texture("bullet_player", position, radius, atlas);
        this.mask = DeltaDucks.BIT_PIRATES | DeltaDucks.BIT_LAND | DeltaDucks.BIT_BOUNDARY;
        this.category = DeltaDucks.BIT_BULLETS;
        defineBullet(position);
        setData(data);
        this.rigidBody.applyForce(shipMomentum, 55f);
        this.rigidBody.applyForce(direction, BULLET_SPEED);
    }

}
