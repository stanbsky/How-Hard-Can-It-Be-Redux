package com.ducks.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.screens.MainGameScreen;
import com.ducks.sprites.Cannon;
import com.ducks.sprites.Pirate;

public class ListOfPirates {

    private World world;
    private MainGameScreen screen;

    private Array<Pirate> pirateBodies;
    private final int NUMBER_OF_PIRATES = 10;

    private int mapPixelWidth;
    private int mapPixelHeight;

    private final float RADIUS = 4f * Pirate.PIXEL_PIRATE_HEIGHT / DeltaDucks.PIXEL_PER_METER;

    public ListOfPirates(World world, MainGameScreen screen, int mapPixelWidth, int mapPixelHeight) {
        this.world = world;
        this.screen = screen;
        this.mapPixelWidth = mapPixelWidth;
        this.mapPixelHeight = mapPixelHeight;
        pirateBodies = new Array<Pirate>();
        spawnPirates();
    }

    public void spawnPirates() {
        System.out.println(RADIUS);
        for(int i = 0; i < NUMBER_OF_PIRATES; i++) {
            pirateBodies.add(new Pirate(world, screen, (float) (mapPixelWidth * Math.random()), (float) (mapPixelHeight * Math.random()), RADIUS));
        }
    }

    public void update(float deltaTime) {
        Array<Pirate> piratesBodiesToRemove = new Array<Pirate>();
        for( Pirate pirate : pirateBodies ) {
            if(!pirate.pirateBody.getFixtureList().get(0).getUserData().equals("Pirate")) {
                piratesBodiesToRemove.add(pirate);
                pirate.dispose();
            }
        }
        pirateBodies.removeAll(piratesBodiesToRemove, true);
        for( Pirate pirate : pirateBodies) {
            pirate.update(deltaTime);
        }
    }

    public void draw(SpriteBatch batch) {
        for( Pirate pirate : pirateBodies) {
            pirate.draw(batch);
        }
    }

}
