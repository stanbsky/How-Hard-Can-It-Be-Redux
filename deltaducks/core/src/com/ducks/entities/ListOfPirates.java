package com.ducks.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.ducks.screens.MainGameScreen;
import sprites.Pirates;

public class ListOfPirates {

    public World world;
    MainGameScreen screen;

    public Array<Pirates> pirateBodies;
    private final int NUMBER_OF_PIRATES = 10;

    public ListOfPirates(World world, MainGameScreen screen) {
        this.world = world;
        this.screen = screen;
        pirateBodies = new Array<Pirates>();
        spawnPirates();
    }

    public void spawnPirates() {
        for(int i = 0; i < NUMBER_OF_PIRATES; i++) {
            pirateBodies.add(new Pirates(world, screen, 32*i, 32*i, 6*2));
        }
    }

    public void update(float deltaTime) {
        for( Pirates pirate : pirateBodies) {
            pirate.update(deltaTime);
        }
    }

    public void draw(SpriteBatch batch) {
        for( Pirates pirate : pirateBodies) {
            pirate.draw(batch);
        }
    }

}
