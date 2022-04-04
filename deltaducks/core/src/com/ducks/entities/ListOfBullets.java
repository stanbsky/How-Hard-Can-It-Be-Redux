package com.ducks.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.ducks.sprites.Bullet;
import com.ducks.sprites.Crosshair;
import com.ducks.sprites.PlayerBullet;
import com.ducks.sprites.Ship;

/***
 * Collective Bullets Class for Box2D Bodies and Sprites
 */
public class ListOfBullets {
    private Ship player;

    private Array<Bullet> bulletBodies;

    private final float SHOOT_WAIT_TIME = .3f;
    float shootTimer;

    /**
     * Constructor
     */
    public ListOfBullets(Ship player) {
        this.player = player;
        bulletBodies = new Array<>();
    }

    /**
     * Spawn a bullet
     */
    public void spawnBullet() {
        if (shootTimer >= SHOOT_WAIT_TIME) {
            shootTimer=0;
            bulletBodies.add(new PlayerBullet(player.b2body.getPosition(), Crosshair.getCrosshairDirection(), player.b2body.getLinearVelocity()));
        }
    }

    /**
     * Update all bullets every delta time interval
     * @param deltaTime of the game
     */
    public void update(float deltaTime) {
        shootTimer += deltaTime;
        Array<Bullet> bulletBodiesToRemove = new Array<>();
        for( Bullet bullet : bulletBodies) {
            if(!bullet.getBody().getFixtureList().get(0).getUserData().equals("Bullet Alive")) {
                bulletBodiesToRemove.add(bullet);
                bullet.dispose();
            } else {
                bullet.update(deltaTime);
            }
        }
        bulletBodies.removeAll(bulletBodiesToRemove, true);
    }

    /**
     * Draw all bullets every delta time interval
     * @param batch to draw on the screen
     */
    public void draw(SpriteBatch batch) {
        for( Bullet bullet : bulletBodies) {
            bullet.draw(batch);
        }
    }

}
