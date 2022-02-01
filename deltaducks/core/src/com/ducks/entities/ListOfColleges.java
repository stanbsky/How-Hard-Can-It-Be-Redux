package com.ducks.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.screens.MainGameScreen;
import com.ducks.sprites.Cannon;
import com.ducks.sprites.College;
import com.ducks.sprites.Monster;

public class ListOfColleges {

    private World world;
    private MainGameScreen screen;

    private Array<College> collegeBodies;
    private final int NUMBER_OF_MONSTERS = 1;

    private final int SPAWN_X = 500;
    private final int SPAWN_Y = 500;
    private final float SPAWN_RADIUS = 10f * 4f * College.COLLEGE_HEIGHT / DeltaDucks.PIXEL_PER_METER;

    ListOfCannons cannons;

    private College.CollegeName collegeName;

    public ListOfColleges(World world, MainGameScreen screen, ListOfCannons cannons, TiledMap map) {
        this.world = world;
        this.screen = screen;
        this.cannons = cannons;
        collegeBodies = new Array<College>();
        spawnColleges(map);
    }

    public void spawnColleges(TiledMap map) {
        BodyDef bdef = new BodyDef();
//        for(int i = 0; i < NUMBER_OF_MONSTERS; i++) {
//            collegeBodies.add(new College(world, screen, SPAWN_X,SPAWN_Y, SPAWN_RADIUS, College.CollegeName.DERWENT, cannons));
//        }
        int colleState = 0;
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            switch (colleState++) {
                case 0:
                    collegeName = College.CollegeName.CONSTANTINE;
                    break;
                case 1:
                    collegeName = College.CollegeName.GOODRICK;
                    break;
                case 2:
                    collegeName = College.CollegeName.HALIFAX;
                    break;
            }
            collegeBodies.add(new College(world, screen, (rect.getX() + rect.getWidth() / 2) * DeltaDucks.TILEED_MAP_SCALE, (rect.getY() + rect.getHeight() / 2) * DeltaDucks.TILEED_MAP_SCALE, SPAWN_RADIUS, collegeName, cannons));
        }
        collegeBodies.add(new College(world, screen, SPAWN_X,SPAWN_Y, SPAWN_RADIUS, College.CollegeName.DERWENT, cannons));

    }

    public void update(float deltaTime) {
        Array<College> collegeBodiesToRemove = new Array<College>();
        for( College college : collegeBodies) {
            if(college.health <= 0){
                collegeBodiesToRemove.add(college);
                college.dispose();
            } else {
                college.update(deltaTime);
            }
        }
        collegeBodies.removeAll(collegeBodiesToRemove, true);
    }

    public void draw(SpriteBatch batch) {
        for( College college : collegeBodies) {
            college.extendedDraw(batch);
        }
    }

    public Array<Vector2> getCoordinates() {
        Array <Vector2> coordinates = new Array <Vector2>();
        for( College college : collegeBodies) {
            coordinates.add(college.collegeBody.getPosition());
        }
        return coordinates;
    }

    public int getNumbersOfColleges() {
        return collegeBodies.size;
    }
}
