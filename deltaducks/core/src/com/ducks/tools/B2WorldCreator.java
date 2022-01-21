package com.ducks.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.ducks.DeltaDucks;

public class B2WorldCreator {
    public B2WorldCreator(World world, TiledMap map) {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
//        fdef.friction = 0.4f;
        // Sea
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / DeltaDucks.PIXEL_PER_METER, (rect.getY() + rect.getHeight() / 2) / DeltaDucks.PIXEL_PER_METER);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2 / DeltaDucks.PIXEL_PER_METER, rect.getHeight() / 2 / DeltaDucks.PIXEL_PER_METER);
            fdef.shape = shape;
            fdef.filter.categoryBits = DeltaDucks.BIT_SEA;
            body.createFixture(fdef).setUserData("Sea");
        }

        // Ground
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / DeltaDucks.PIXEL_PER_METER, (rect.getY() + rect.getHeight() / 2) / DeltaDucks.PIXEL_PER_METER);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2 / DeltaDucks.PIXEL_PER_METER, rect.getHeight() / 2 / DeltaDucks.PIXEL_PER_METER);
            fdef.shape = shape;
            fdef.filter.categoryBits = DeltaDucks.BIT_LAND;
            body.createFixture(fdef).setUserData("Land");
        }

    }
}
