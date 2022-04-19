package com.ducks.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.ducks.components.Shooter;
import com.ducks.entities.*;
import com.ducks.tools.IShooter;

import static com.ducks.screens.MainGameScreen.player;

/***
 * Collective Cannons Class for Box2D Bodies and Sprites
 */
public final class BulletManager {

    private static Array<Bullet> bullets;

    /**
     * Constructor
     */
    public static void Initialize() {
        bullets = new Array<>();
    }

    public static void spawnBullet(IShooter shooter) {
        if (shooter.ready()) {
            shooter.resetShootTimer();
            //TODO: Add variable score, somehow
            bullets.add(new EnemyBullet(shooter.getPosition(),
                    Shooter.getDirection(shooter, player)));
        }
    }

    /**
     * Spawn a cannon
     * @param college College shooting at player
     */
    @Deprecated
    public void spawnBullet(College college) {
//        if (college.shootTimer >= college.SHOOT_WAIT_TIME) {
//            college.shootTimer = 0;
//            Hud.addScore(100);
//            Vector2 pos = college.getPosition();
//            bullets.add(new EnemyBullet(pos,
//                    Crosshair.getDirection(pos, player.getPosition())));
//        }
    }

    /**
     * Spawn a cannon
     * @param pirate Pirate Ship shooting at player
     */
    @Deprecated
    public void spawnBullet(Pirate pirate) {
//        if (pirate.shootTimer >= pirate.SHOOT_WAIT_TIME) {
//            pirate.shootTimer = 0;
//            Hud.addScore(50);
//            Vector2 pos = pirate.getPosition();
//            bullets.add(new EnemyBullet(pos,
//                    Crosshair.getDirection(pos, player.getPosition())));
//        }
    }

    /**
     * Update all cannons every delta time interval
     * @param deltaTime of the game
     */
    public static void update(float deltaTime) {
        Array<Bullet> deadBullets = new Array<>();
        for( Bullet cannon : bullets) {
            if(!cannon.isAlive()) {
                deadBullets.add(cannon);
                cannon.dispose();
            } else {
                cannon.update(deltaTime);
            }
        }
        bullets.removeAll(deadBullets, true);
    }

    /**
     * Draw all cannons every delta time interval
     * @param batch to draw on the screen
     */
    public static void draw() {
        for( Bullet cannon : bullets) {
            cannon.draw();
        }
    }
}
