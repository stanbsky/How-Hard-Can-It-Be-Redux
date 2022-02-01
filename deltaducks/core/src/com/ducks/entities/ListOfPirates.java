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
import com.ducks.sprites.Cannon;
import com.ducks.sprites.College;
import com.ducks.sprites.Pirate;

public class ListOfPirates {

    private World world;
    private MainGameScreen screen;

    private Array<Pirate> pirateBodies;
    private final int NUMBER_OF_PIRATES = 10;


    private final float RADIUS = 4f * Pirate.PIXEL_PIRATE_HEIGHT / DeltaDucks.PIXEL_PER_METER;

    public ListOfPirates(World world, MainGameScreen screen, TiledMap map) {
        this.world = world;
        this.screen = screen;
        pirateBodies = new Array<Pirate>();
        spawnPirates(map);
    }

    public void spawnPirates(TiledMap map) {
        BodyDef bdef = new BodyDef();

        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            if(Math.random() > .3f)
                continue;
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            pirateBodies.add(new Pirate(world, screen, (rect.getX() + rect.getWidth() / 2) * DeltaDucks.TILEED_MAP_SCALE, (rect.getY() + rect.getHeight() / 2) * DeltaDucks.TILEED_MAP_SCALE, RADIUS));
        }
    }

    public void update(float deltaTime) {
        Array<Pirate> piratesBodiesToRemove = new Array<Pirate>();
        for( Pirate pirate : pirateBodies ) {
            if(!pirate.pirateBody.getFixtureList().get(0).getUserData().equals("Pirate")) {
                piratesBodiesToRemove.add(pirate);
                pirate.dispose();
            } else {
                pirate.update(deltaTime);
            }
        }
        pirateBodies.removeAll(piratesBodiesToRemove, true);
    }

    public void draw(SpriteBatch batch) {
        for( Pirate pirate : pirateBodies) {
            pirate.draw(batch);
        }
    }

}
