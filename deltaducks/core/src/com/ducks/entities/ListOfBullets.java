package com.ducks.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.ducks.screens.MainGameScreen;
import com.ducks.sprites.Bullet;
import com.ducks.sprites.Crosshair;
import com.ducks.sprites.Ship;

public class ListOfBullets {
    private World world;
    private MainGameScreen screen;
    private Ship player;
    private Crosshair crosshair;

    private Array<Bullet> bulletBodies;

    private final float SHOOT_WAIT_TIME = .3f;
    float shootTimer ;

    public ListOfBullets(World world, MainGameScreen screen, Ship player, Crosshair crosshair) {
        this.world = world;
        this.screen = screen;
        this.player = player;
        this.crosshair = crosshair;
        bulletBodies = new Array<Bullet>();
    }

    public void spawnBullet() {
        if (shootTimer >= SHOOT_WAIT_TIME) {
            shootTimer=0;
            bulletBodies.add(new Bullet(world, player, crosshair));
        }
    }

    public void update(float deltaTime) {
        shootTimer += deltaTime;
        Array<Bullet> bulletBodiesToRemove = new Array<Bullet>();
        for( Bullet bullet : bulletBodies) {
            if(!bullet.bulletBody.getFixtureList().get(0).getUserData().equals("Bullet Alive")) {
                bulletBodiesToRemove.add(bullet);
                bullet.dispose();
            }
        }
        bulletBodies.removeAll(bulletBodiesToRemove, true);
        for( Bullet bullet : bulletBodies) {
            bullet.update(deltaTime);
        }
    }

    public void draw(SpriteBatch batch) {
        for( Bullet bullet : bulletBodies) {
            bullet.draw(batch);
        }
    }

}
