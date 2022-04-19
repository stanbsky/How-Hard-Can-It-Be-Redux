package com.ducks.entities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.ducks.components.Texture;
import com.ducks.intangibles.EntityData;
import com.ducks.screens.MainGameScreen;
import static com.ducks.tools.FixtureFilter.*;
/***
 * Bullet Class for Box2D Body and Sprite
 */
public class PlayerBullet extends Bullet {

    private final float BULLET_SPEED = 200f;

    /**
     * Purely for printing debug info
     * @param deltaTime of the game
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }
    /**
     * Constructor
     */
    public PlayerBullet(Vector2 position, Vector2 direction, Vector2 shipMomentum) {
        texture = new Texture("bullet_player", position, radius);
        mask = MASK_ALL - PLAYER - PLAYER_BULLET;
        category = PLAYER_BULLET;
        data = new EntityData(category);
        defineBullet(position);
        setData(data);
        rigidBody.applyForce(shipMomentum, 55f);
        rigidBody.applyForce(direction, BULLET_SPEED);
    }

}
