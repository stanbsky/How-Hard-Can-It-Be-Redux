package com.ducks.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.ducks.scenes.Hud;
import com.ducks.screens.MainGameScreen;
import com.ducks.sprites.*;

public class ListOfCannons {

    private World world;
    private MainGameScreen screen;
    private Ship player;
    private Crosshair crosshair;

    private Array<Cannon> cannonBodies;

    private final float SHOOT_WAIT_TIME = 1f;
    float shootTimer ;

    public ListOfCannons(World world, MainGameScreen screen, Ship player, Crosshair crosshair) {
        this.world = world;
        this.screen = screen;
        this.player = player;
        this.crosshair = crosshair;
        cannonBodies = new Array<Cannon>();
    }

    public void spawnBullet(College college) {
        if (shootTimer >= SHOOT_WAIT_TIME) {
            shootTimer=0;
            Hud.addScore(100);
            cannonBodies.add(new Cannon(world, college, player));
        }
    }

    public void update(float deltaTime, OrthographicCamera gameCam) {
        shootTimer += deltaTime;
        Array<Cannon> CannonBodiesToRemove = new Array<Cannon>();
        for( Cannon cannon : cannonBodies) {
            if(!cannon.bulletBody.getFixtureList().get(0).getUserData().equals("Cannon Alive")) {
                CannonBodiesToRemove.add(cannon);
                cannon.dispose();
            } else {
                cannon.update(deltaTime);
            }
        }
        cannonBodies.removeAll(CannonBodiesToRemove, true);
    }

    public void draw(SpriteBatch batch) {
        for( Cannon cannon : cannonBodies) {
            cannon.draw(batch);
        }
    }
}
