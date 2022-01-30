package com.ducks.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.screens.MainGameScreen;
import com.ducks.sprites.Monsters;

public class ListOfMonsters {

    private World world;
    private MainGameScreen screen;

    private Array<Monsters> monsterBodies;
    private final int NUMBER_OF_MONSTERS = 1;

    private final int SPAWN_X = 200;
    private final int SPAWN_Y = 400;
    private final float SPAWN_RADIUS = 10f * 1.5f * Monsters.PIXEL_WORM_HEIGHT / DeltaDucks.PIXEL_PER_METER;

    public ListOfMonsters(World world, MainGameScreen screen) {
        this.world = world;
        this.screen = screen;
        monsterBodies = new Array<Monsters>();
        spawnMonsters();
    }

    public void spawnMonsters() {
        for(int i = 0; i < NUMBER_OF_MONSTERS; i++) {
            monsterBodies.add(new Monsters(world, screen, SPAWN_X,SPAWN_Y, SPAWN_RADIUS));
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
