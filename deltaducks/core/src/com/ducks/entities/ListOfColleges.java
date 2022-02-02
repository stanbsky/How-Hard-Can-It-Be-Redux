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

/**
 * Collective Colleges Class for Box2D Bodies and Sprites
 */
public class ListOfColleges {

    private World world;
    private MainGameScreen screen;

    public Array<College> collegeBodies;

    private final float SPAWN_RADIUS = 10f * 4f * College.COLLEGE_HEIGHT / DeltaDucks.PIXEL_PER_METER;

    ListOfCannons cannons;

    private String collegeName;

    private int collegesAlive = 3;

    /**
     * Constructor
     * @param world Box2D world
     * @param screen Game Screen
     * @param cannons Collective Cannons class to spawn and fire them on player's direction
     * @param map Tiled Map
     */
    public ListOfColleges(World world, MainGameScreen screen, ListOfCannons cannons, TiledMap map) {
        this.world = world;
        this.screen = screen;
        this.cannons = cannons;
        collegeBodies = new Array<College>();
        spawnColleges(map);
    }

    /**
     * Spawn every colleges corresponding to Tiled Map locations
     * @param map
     */
    public void spawnColleges(TiledMap map) {
        BodyDef bdef = new BodyDef();
        int colleState = 0;
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            switch (colleState++) {
                case 0:
                    collegeName = "college constantine";
                    break;
                case 1:
                    collegeName = "college goodrick";
                    break;
                case 2:
                    collegeName = "college halifax";
                    break;
            }
            collegeBodies.add(new College(world, screen, (rect.getX() + rect.getWidth() / 2) * DeltaDucks.TILEED_MAP_SCALE, (rect.getY() + rect.getHeight() / 2) * DeltaDucks.TILEED_MAP_SCALE, SPAWN_RADIUS, collegeName, cannons));
        }
    }

    /**
     * Update all colleges every delta time interval
     * @param deltaTime of the game
     */
    public void update(float deltaTime) {
        Array<College> collegeBodiesToRemove = new Array<College>();
        for( College college : collegeBodies) {
            if(college.health <= 0 && college.health != -10f){
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
            college.extendedDraw(batch);
        }
    }

    /**
     * Get coordinates of every college on the map
     * @return Array of coordinates
     */
    public Array<Vector2> getCoordinates() {
        Array <Vector2> coordinates = new Array <Vector2>();
        for( College college : collegeBodies) {
            coordinates.add(college.collegeBody.getPosition());
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
