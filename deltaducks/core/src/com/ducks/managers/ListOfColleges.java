package com.ducks.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.entities.College;

/**
 * Collective Colleges Class for Box2D Bodies and Sprites
 */
public class ListOfColleges {

    public Array<College> collegeBodies;
    private int collegesAlive = 3;

    /**
     * Constructor
     * @param map Tiled Map
     */
    public ListOfColleges(TiledMap map) {
        collegeBodies = new Array<College>();
        spawnColleges(map);
    }

    /**
     * Spawn every colleges corresponding to Tiled Map locations
     * @param map
     */
    public void spawnColleges(TiledMap map) {
        String collegeName = "";
        int colleState = 0;
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            switch (colleState++) {
                case 0:
                    collegeName = "constantine";
                    break;
                case 1:
                    collegeName = "goodricke";
                    break;
                case 2:
                    collegeName = "halifax";
                    break;
            }
            collegeBodies.add(new College(
                    (rect.getX() + rect.getWidth() / 2) * DeltaDucks.TILEED_MAP_SCALE,
                    (rect.getY() + rect.getHeight() / 2) * DeltaDucks.TILEED_MAP_SCALE,
                    collegeName));
        }
    }

    /**
     * Update all colleges every delta time interval
     * @param deltaTime of the game
     */
    public void update(float deltaTime) {
        Array<College> collegeBodiesToRemove = new Array<College>();
        for( College college : collegeBodies) {
            if(college.health <= 0 && !college.destroyed){
                college.dispose();
                collegesAlive--;
            }
            college.update(deltaTime);
        }
        collegeBodies.removeAll(collegeBodiesToRemove, true);
    }

    /**
     * Draw all colleges every delta time interval
     * @param batch to draw on the screen
     */
    public void draw(SpriteBatch batch) {
        for( College college : collegeBodies) {
            college.draw(batch);
        }
    }

    /**
     * Get coordinates of every college on the map
     * @return Array of coordinates
     */
    public Array<Vector2> getCoordinates() {
        Array <Vector2> coordinates = new Array <Vector2>();
        for( College college : collegeBodies) {
            coordinates.add(college.getPosition());
        }
        return coordinates;
    }

    /**
     * Get numbers of colleges alive (or not destoryed)
     * @return int
     */
    public int getNumbersOfColleges() {
        return collegesAlive;
    }
}
