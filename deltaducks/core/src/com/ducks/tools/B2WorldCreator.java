package com.ducks.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.ducks.DeltaDucks;
import static com.ducks.tools.FixtureFilter.*;
import static com.ducks.screens.MainGameScreen.map;

/***
 * Extract useful information from the tilemap and render Box2d body on the Box2d world
 */
public class B2WorldCreator {
    /**
     * Constructor which maps the information onto Box2d world
     * @param world Box2D world
     */
    public B2WorldCreator(World world) {

        // Ground
        for (MapObject object : map.getLayers().get("ground").getObjects().getByType(RectangleMapObject.class)) {
            defineLand(world, object);
        }
        // Boundaries
        for (MapObject object : map.getLayers().get("boundaries").getObjects().getByType(RectangleMapObject.class)) {
            defineLand(world, object);
        }

    }

    private void defineLand(World world, MapObject object) {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        Rectangle rect = ((RectangleMapObject) object).getRectangle();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((rect.getX() + rect.getWidth() / 2) * DeltaDucks.TILEED_MAP_SCALE / DeltaDucks.PIXEL_PER_METER, (rect.getY() + rect.getHeight() / 2) * DeltaDucks.TILEED_MAP_SCALE / DeltaDucks.PIXEL_PER_METER);
        body = world.createBody(bdef);
        shape.setAsBox(rect.getWidth() * DeltaDucks.TILEED_MAP_SCALE / 2 / DeltaDucks.PIXEL_PER_METER, rect.getHeight() * DeltaDucks.TILEED_MAP_SCALE / 2 / DeltaDucks.PIXEL_PER_METER);
        fdef.shape = shape;
        fdef.filter.categoryBits = SCENERY;
        fdef.filter.maskBits = MASK_ALL - PLAYER_BULLET - ENEMY_BULLET;
        body.createFixture(fdef);
    }
}
