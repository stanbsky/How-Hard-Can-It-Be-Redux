package com.ducks.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.ducks.screens.MainGameScreen;
import sprites.Monsters;
import sprites.Pirates;

public class ListOfMonsters {

    public World world;
    MainGameScreen screen;

    public Array<Monsters> monsterBodies;
    private final int NUMBER_OF_MONSTERS = 1;

    public ListOfMonsters(World world, MainGameScreen screen) {
        this.world = world;
        this.screen = screen;
        monsterBodies = new Array<Monsters>();
        spawnMonsters();
    }

    public void spawnMonsters() {
        for(int i = 0; i < NUMBER_OF_MONSTERS; i++) {
            monsterBodies.add(new Monsters(world, screen));
        }
    }

    public void update(float deltaTime) {
        for( Monsters pirate : monsterBodies) {
            pirate.update(deltaTime);
        }
    }

    public void draw(SpriteBatch batch) {
        for( Monsters pirate : monsterBodies) {
            pirate.draw(batch);
        }
    }
}
