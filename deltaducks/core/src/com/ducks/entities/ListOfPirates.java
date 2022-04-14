package com.ducks.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.screens.MainGameScreen;
import com.ducks.sprites.Pirate;

/***
 * Collective Pirates Class for Box2D Bodies and Sprites
 */
public class ListOfPirates {

    private World world;
    private MainGameScreen screen;

    private Array<Pirate> pirateBodies;


//    private final float RADIUS = 4f * Pirate.PIXEL_PIRATE_HEIGHT / DeltaDucks.PIXEL_PER_METER;

    /**
     *
     * @param world Box2D world
     * @param screen Game Screen
     * @param map Tiled Map
     */
    public ListOfPirates(World world, MainGameScreen screen, TiledMap map) {
        this.world = world;
        this.screen = screen;
        pirateBodies = new Array<Pirate>();
        spawnPirates(map);
    }

    /**
     * Spawn pirates ships (30% chance for each) corresponding to Tiled Map locations
     * @param map
     */
    public void spawnPirates(TiledMap map) {
        BodyDef bdef = new BodyDef();

        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            if(Math.random() > .3f)
                continue;
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            String college;
            double c = Math.random();
            if (c < 0.3f)
                college = "goodricke";
            else if (c > 0.6f)
                college = "halifax";
            else
                college = "constantine";
            pirateBodies.add(new Pirate(college, rect.getX()*DeltaDucks.TILEED_MAP_SCALE, rect.getY()*DeltaDucks.TILEED_MAP_SCALE));
//            pirateBodies.add(new Pirate(world, screen, (rect.getX() + rect.getWidth() / 2) * DeltaDucks.TILEED_MAP_SCALE, (rect.getY() + rect.getHeight() / 2) * DeltaDucks.TILEED_MAP_SCALE, RADIUS));
        }
        //TODO: testing spawn near player
        pirateBodies.add(new Pirate("constantine", 3358,5163));
    }

    /**
     * Update all pirates ships every delta time interval
     * @param deltaTime of the game
     */
    public void update(float deltaTime) {
        Array<Pirate> piratesBodiesToRemove = new Array<Pirate>();
        for( Pirate pirate : pirateBodies ) {
            if(!pirate.getData().equals("Pirate")) {
                piratesBodiesToRemove.add(pirate);
                pirate.dispose();
            } else {
                pirate.update(deltaTime);
            }
        }
        pirateBodies.removeAll(piratesBodiesToRemove, true);
    }

    /**
     * Draw all pirates ships every delta time interval
     * @param batch to draw on the screen
     */
    public void draw(SpriteBatch batch) {
        for( Pirate pirate : pirateBodies) {
            pirate.draw(batch);
        }
    }

}
