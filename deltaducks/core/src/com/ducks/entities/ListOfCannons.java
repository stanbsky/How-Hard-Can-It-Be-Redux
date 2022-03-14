package com.ducks.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.ducks.scenes.Hud;
import com.ducks.sprites.*;

/***
 * Collective Cannons Class for Box2D Bodies and Sprites
 */
public class ListOfCannons {

    private Ship player;

    private Array<Bullet> cannonBodies;

    private final float SHOOT_WAIT_TIME = 1f;
    float shootTimer ;

    /**
     * Constructor
     * @param player Box2D object of player
     */
    public ListOfCannons(Ship player) {
        this.player = player;
        cannonBodies = new Array<>();
    }

    /**
     * Spawn a cannon
     * @param college
     */
    public void spawnCannon(College college) {
        if (shootTimer >= SHOOT_WAIT_TIME) {
            shootTimer=0;
            Hud.addScore(100);
            Vector2 pos = college.collegeBody.getPosition();
            cannonBodies.add(new CollegeBullet(pos,
                    Crosshair.getDirection(pos, player.b2body.getPosition())));
        }
    }

    /**
     * Update all cannons every delta time interval
     * @param deltaTime of the game
     */
    public void update(float deltaTime) {
        shootTimer += deltaTime;
        Array<Bullet> CannonBodiesToRemove = new Array<>();
        for( Bullet cannon : cannonBodies) {
            if(!cannon.getBody().getFixtureList().get(0).getUserData().equals("Cannon Alive")) {
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
