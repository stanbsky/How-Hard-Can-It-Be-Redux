package com.ducks.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.entities.Pirate;
import static com.ducks.screens.MainGameScreen.map;
/***
 * Collective Pirates Class for Box2D Bodies and Sprites
 */
public class ListOfPirates {

    private static Array<Pirate> pirateBodies;

    public ListOfPirates() {
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
            if(!pirate.isAlive()) {
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
     */
    public void draw() {
        for( Pirate pirate : pirateBodies) {
            pirate.draw();
        }
    }

    public static Pirate getRandomPirate() {
        return pirateBodies.random();
    }

}
