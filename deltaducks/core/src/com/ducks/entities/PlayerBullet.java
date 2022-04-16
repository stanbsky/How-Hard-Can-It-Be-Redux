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

    public PlayerBullet(Vector2 position, Vector2 direction, Vector2 shipMomentum) {
        this(position, direction, shipMomentum, MainGameScreen.getAtlas());
    }

    /**
     * Purely for printing debug info
     * @param deltaTime of the game
     */
    public void update(float deltaTime) {
        super.update(deltaTime);
    }
    /**
     * Constructor
     */
    public PlayerBullet(Vector2 position, Vector2 direction, Vector2 shipMomentum, TextureAtlas atlas) {
        this.texture = new Texture("bullet_player", position, radius, atlas);
        this.mask = MASK_ALL - PLAYER - PLAYER_BULLET;
        this.category = PLAYER_BULLET;
        data = new EntityData("Bullet");
        defineBullet(position);
        setData(data);
        this.rigidBody.applyForce(shipMomentum, 55f);
        this.rigidBody.applyForce(direction, BULLET_SPEED);
    }

}
