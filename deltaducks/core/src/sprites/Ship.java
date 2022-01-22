package sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.screens.MainGameScreen;

public class Ship extends Sprite {
    public enum State {STANDING, MOVING, NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion shipStand;
    private Animation <TextureRegion> shipMove;
    private Animation <TextureRegion> shipAttack;

    private Animation <TextureRegion> shipMoveFromTop;
    private Animation <TextureRegion> shipMoveFromDown;

    private Animation <TextureRegion> shipMoveToTop;
    private Animation <TextureRegion> shipMoveToDown;

    private float stateTimer;
    private boolean movingUp;
    private boolean movingRight;

    private final int PIXEL_WIDTH = 64;
    private final int PIXEL_HEIGHT = 64;

    public Ship(World world, MainGameScreen screen) {
        super(screen.getAtlas().findRegion("boat"));
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        movingUp = true;
        movingRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i=2; i<4; i++) {
            frames.add(new TextureRegion(getTexture(), i*PIXEL_WIDTH, 0, PIXEL_WIDTH, PIXEL_HEIGHT));
        }
        frames.add(new TextureRegion(getTexture(), 0, 0, PIXEL_WIDTH, PIXEL_HEIGHT));

        shipMove = new Animation(0.1f, frames);

        frames.clear();

        shipStand = new TextureRegion(getTexture(), PIXEL_WIDTH*0, 0, PIXEL_WIDTH, PIXEL_HEIGHT);


        defineShip();
        setBounds(0, 0, PIXEL_WIDTH / DeltaDucks.PIXEL_PER_METER, PIXEL_HEIGHT / DeltaDucks.PIXEL_PER_METER);
        setRegion(shipStand);
    }

    public void update(float deltaTime) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(deltaTime));
    }

    public TextureRegion getFrame(float deltaTime) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case MOVING:
                region = shipMove.getKeyFrame(stateTimer, true);
                break;
            default:
                region = shipStand;
                break;
        }

        if((b2body.getLinearVelocity().x < 0 || !movingRight) && !region.isFlipX()){
            region.flip(true, false);
            movingRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || movingRight) && region.isFlipX()) {
            region.flip(true, false);
            movingRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + deltaTime : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {
        if(b2body.getLinearVelocity().x !=0 || b2body.getLinearVelocity().y !=0)
            return State.MOVING;
        else
            return State.STANDING;
    }

    public void defineShip() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / DeltaDucks.PIXEL_PER_METER, 32 / DeltaDucks.PIXEL_PER_METER);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearDamping = 1f;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / DeltaDucks.PIXEL_PER_METER);

        fdef.shape = shape;
        fdef.filter.categoryBits = DeltaDucks.BIT_PLAYER;
        fdef.filter.maskBits = DeltaDucks.BIT_LAND;
        fdef.restitution = 0.2f;
        b2body.createFixture(fdef).setUserData("Player");

        PolygonShape polyShape = new PolygonShape();
        polyShape.setAsBox(2 / DeltaDucks.PIXEL_PER_METER, 2 / DeltaDucks.PIXEL_PER_METER, new Vector2(0, -5 / DeltaDucks.PIXEL_PER_METER), 0);
        fdef.shape = polyShape;
        fdef.filter.categoryBits = DeltaDucks.BIT_PLAYER;
        fdef.filter.maskBits = DeltaDucks.BIT_LAND;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("Sensor");

    }
}
