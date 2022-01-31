package com.ducks.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.screens.MainGameScreen;
import com.ducks.sprites.College;
import com.ducks.sprites.Monster;

public class ListOfColleges {

    private World world;
    private MainGameScreen screen;

    private Array<College> collegeBodies;
    private final int NUMBER_OF_MONSTERS = 1;

    private final int SPAWN_X = 400;
    private final int SPAWN_Y = 400;
    private final float SPAWN_RADIUS = 10f * 4f * College.COLLEGE_HEIGHT / DeltaDucks.PIXEL_PER_METER;

    ListOfCannons cannons;

    public ListOfColleges(World world, MainGameScreen screen, ListOfCannons cannons) {
        this.world = world;
        this.screen = screen;
        this.cannons = cannons;
        collegeBodies = new Array<College>();
        spawnColleges();
    }

    public void spawnColleges() {
        for(int i = 0; i < NUMBER_OF_MONSTERS; i++) {
            collegeBodies.add(new College(world, screen, SPAWN_X,SPAWN_Y, SPAWN_RADIUS, College.CollegeName.DERWENT, cannons));
        }
    }

    public void update(float deltaTime) {
        for( College college : collegeBodies) {
            college.update(deltaTime);
        }
    }

    public void draw(SpriteBatch batch) {
        for( College college : collegeBodies) {
            college.draw(batch);
        }
    }
}
