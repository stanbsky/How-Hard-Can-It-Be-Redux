package com.ducks.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.ducks.scenes.Hud;
import com.ducks.entities.*;

/***
 * Collective Cannons Class for Box2D Bodies and Sprites
 */
public class ListOfEnemyBullets {

    private final Ship player;

    private final Array<Bullet> cannonBodies;

    /**
     * Constructor
     * @param player Box2D object of player
     */
    public ListOfEnemyBullets(Ship player) {
        this.player = player;
        cannonBodies = new Array<>();
    }

    /**
     * Spawn a cannon
     * @param college College shooting at player
     */
    public void spawnBullet(College college) {
        if (college.shootTimer >= college.SHOOT_WAIT_TIME) {
            college.shootTimer = 0;
            Hud.addScore(100);
            Vector2 pos = college.getPosition();
            cannonBodies.add(new EnemyBullet(pos,
                    Crosshair.getDirection(pos, player.getPosition())));
        }
    }

    /**
     * Spawn a cannon
     * @param pirate Pirate Ship shooting at player
     */
    public void spawnBullet(Pirate pirate) {
        if (pirate.shootTimer >= pirate.SHOOT_WAIT_TIME) {
            pirate.shootTimer = 0;
            Hud.addScore(50);
            Vector2 pos = pirate.getPosition();
            cannonBodies.add(new EnemyBullet(pos,
                    Crosshair.getDirection(pos, player.getPosition())));
        }
    }

    /**
     * Update all cannons every delta time interval
     * @param deltaTime of the game
     */
    public void update(float deltaTime) {
        Array<Bullet> CannonBodiesToRemove = new Array<>();
        for( Bullet cannon : cannonBodies) {
            if(!cannon.getData().equals("Cannon Alive")) {
                CannonBodiesToRemove.add(cannon);
                cannon.dispose();
            } else {
                cannon.update(deltaTime);
            }
        }
        cannonBodies.removeAll(CannonBodiesToRemove, true);
    }

    /**
     * Draw all cannons every delta time interval
     * @param batch to draw on the screen
     */
    public void draw(SpriteBatch batch) {
        for( Bullet cannon : cannonBodies) {
            cannon.draw(batch);
        }
    }
}