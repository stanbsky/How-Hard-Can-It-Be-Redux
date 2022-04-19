package com.ducks.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.ducks.entities.Bullet;
import com.ducks.ui.Crosshair;
import com.ducks.entities.PlayerBullet;
import com.ducks.entities.Ship;

import static com.ducks.screens.MainGameScreen.player;

/***
 * Collective Bullets Class for Box2D Bodies and Sprites
 */
public class ListOfBullets {
    private Array<Bullet> bulletBodies;

    private final float SHOOT_WAIT_TIME = .3f;
    float shootTimer;

    /**
     * Constructor
     */
    public ListOfBullets() {
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
            if(!bullet.isAlive()) {
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
     */
    public void draw() {
        for( Bullet bullet : bulletBodies) {
            bullet.draw();
        }
    }

}
