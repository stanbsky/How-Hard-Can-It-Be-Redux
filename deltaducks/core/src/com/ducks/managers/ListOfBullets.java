package com.ducks.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.ducks.entities.Bullet;
import com.ducks.entities.Crosshair;
import com.ducks.entities.PlayerBullet;
import com.ducks.entities.Ship;

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
            bulletBodies.add(new PlayerBullet(player.getPosition(), Crosshair.getCrosshairDirection(), player.getVelocity()));
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
            if(!bullet.getData().equals("Bullet Alive")) {
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
