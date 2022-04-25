package com.ducks.entities;

import com.badlogic.gdx.math.Vector2;
import com.ducks.components.Texture;
import com.ducks.intangibles.EntityData;
import com.ducks.managers.PowerupManager;
import com.ducks.ui.Crosshair;

import static com.ducks.screens.MainGameScreen.player;
import static com.ducks.tools.FixtureFilter.*;
/***
 * Bullet Class for Box2D Body and Sprite
 */
public class PlayerBullet extends Bullet {

    private float BULLET_SPEED;

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
    public PlayerBullet() { this(player.getPosition().cpy(), Crosshair.getCrosshairDirection().cpy(), player.getVelocity().cpy());  }

    public PlayerBullet(float offset) { this(player.getPosition().cpy(), Crosshair.getCrosshairDirection().cpy().rotateDeg(offset), player.getVelocity().cpy());  }

    private PlayerBullet(Vector2 position, Vector2 direction, Vector2 shipMomentum) {
        if (PowerupManager.hotshotActive()) {
            this.BULLET_SPEED = 500f;
            texture = new Texture("bullet_redhot", position, radius * 1.5f);
            PowerupManager.hotshotUsed();
        } else {
            this.BULLET_SPEED = 200f;
            texture = new Texture("bullet_player", position, radius * 1.5f);
        }
        mask = MASK_ALL - PLAYER - PLAYER_BULLET;
        category = PLAYER_BULLET;
        data = new EntityData(category);
        direction.nor();
        position.add(direction.cpy().scl(0.25f));
        defineBullet(position);
        setData(data);
        rigidBody.applyForce(shipMomentum, 55f);
        rigidBody.applyForce(direction, BULLET_SPEED);
    }

}
