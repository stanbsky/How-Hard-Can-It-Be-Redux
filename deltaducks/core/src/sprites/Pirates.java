package sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.scenes.Hud;
import com.ducks.screens.MainGameScreen;

public class Pirates extends Sprite {

    public World world;
    public Array <Body> pirateBodies;

    private final int NUMBER_OF_PIRATES = 10;

    public Pirates(World world, MainGameScreen screen) {
//        super(screen.getAtlas().findRegion("boat"));
        this.world = world;
        pirateBodies = new Array<Body>();

        spawnPirates();
//        setBounds(0, 0, SHIP_WIDTH / DeltaDucks.PIXEL_PER_METER, SHIP_HEIGHT / DeltaDucks.PIXEL_PER_METER);
//        setRegion(shipStand);
    }

    public void spawnPirates() {
        for(int i = 0; i < NUMBER_OF_PIRATES; i++) {
            pirateBodies.add(definePirates(32*i, 32*i, 6*2));
            Hud.addScore(200);
        }
    }

    public Body definePirates(int x, int y, int radius) {
        Body pirateBody;
        BodyDef bdef = new BodyDef();
        bdef.position.set(x / DeltaDucks.PIXEL_PER_METER, y / DeltaDucks.PIXEL_PER_METER);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearDamping = 1f;
        pirateBody = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius / DeltaDucks.PIXEL_PER_METER);

        fdef.shape = shape;
        fdef.filter.categoryBits = DeltaDucks.BIT_PIRATES;
        fdef.filter.maskBits = DeltaDucks.BIT_PLAYER | DeltaDucks.BIT_PIRATES;
        fdef.restitution = 0.2f;
        pirateBody.createFixture(fdef).setUserData("Pirates");
        return pirateBody;
    }
}
