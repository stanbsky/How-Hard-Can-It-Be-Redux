package com.ducks.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.ducks.screens.MainGameScreen;
import com.ducks.sprites.Bullet;
import com.ducks.sprites.Crosshair;
import com.ducks.sprites.Ship;

/***
 * Collective Bullets Class for Box2D Bodies and Sprites
 */
public class ListOfBullets {
    private World world;
    private MainGameScreen screen;
    private Ship player;
    private Crosshair crosshair;

    private Array<Bullet> bulletBodies;

    private final float SHOOT_WAIT_TIME = .3f;
    float shootTimer ;
    OrthographicCamera gameCam;

    /**
     * Constructor
     * @param world Box2D world
     * @param screen Game Screen
     * @param player Box2D object of player
     * @param crosshair Sprite of crosshair
     * @param gameCam OrthographicCamera
     */
    public ListOfBullets(World world, MainGameScreen screen, Ship player, Crosshair crosshair, OrthographicCamera gameCam) {
        this.world = world;
        this.screen = screen;
        this.player = player;
        this.crosshair = crosshair;
        this.gameCam = gameCam;
        bulletBodies = new Array<Bullet>();
    }

    /**
     * Spawn a bullet
     */
    public void spawnBullet() {
        if (shootTimer >= SHOOT_WAIT_TIME) {
            shootTimer=0;
            bulletBodies.add(new Bullet(world, player, crosshair, gameCam));
        }
    }

    /**
     * Update all bullets every delta time interval
     * @param deltaTime of the game
     */
    public void update(float deltaTime) {
        shootTimer += deltaTime;
        Array<Bullet> bulletBodiesToRemove = new Array<Bullet>();
        for( Bullet bullet : bulletBodies) {
            if(!bullet.bulletBody.getFixtureList().get(0).getUserData().equals("Bullet Alive")) {
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
