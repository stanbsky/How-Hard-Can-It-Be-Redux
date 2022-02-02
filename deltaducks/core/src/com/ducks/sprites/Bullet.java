package com.ducks.sprites;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.screens.MainGameScreen;

public class Bullet extends Sprite {
    private World world;
    private Ship player;

    private Animation<TextureRegion> bulletIdle;

    private final int PIXEL_BULLET_WIDTH = 256;
    private final int PIXEL_BULLET_HEIGHT = 256;

    private final float BULLET_WIDTH = PIXEL_BULLET_WIDTH * .2f;
    private final float BULLET_HEIGHT = PIXEL_BULLET_HEIGHT * .2f;

    float stateTime;
    float spawnTimer;
    public Body bulletBody;

    private final float BULLET_SPEED = 200f;
    private final float BULLET_SPAWN_DURATION = 2f;
    OrthographicCamera gameCam;

    public Bullet(World world, Ship player, Crosshair crosshair, OrthographicCamera gameCam) {
        super(MainGameScreen.resources.getTexture("mehnat"));
        this.world = world;
        this.player = player;
        this.gameCam = gameCam;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(getTexture(), 0 * PIXEL_BULLET_WIDTH, 0 * PIXEL_BULLET_HEIGHT, PIXEL_BULLET_WIDTH, PIXEL_BULLET_HEIGHT));
        bulletIdle = new Animation(0.1f, frames);
        frames.clear();
        setBounds(player.b2body.getPosition().x - player.b2body.getFixtureList().get(0).getShape().getRadius(), player.b2body.getPosition().y - player.b2body.getFixtureList().get(0).getShape().getRadius(), BULLET_WIDTH / DeltaDucks.PIXEL_PER_METER, BULLET_HEIGHT / DeltaDucks.PIXEL_PER_METER);
        setRegion(bulletIdle.getKeyFrame(stateTime, true));

        defineBullet();
        bulletBody.applyForceToCenter(crosshair.getCrosshair().scl(BULLET_SPEED), true);
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
        spawnTimer += deltaTime;
        setPosition(bulletBody.getPosition().x - getWidth()/2, bulletBody.getPosition().y - getHeight()/2);
        if(spawnTimer > BULLET_SPAWN_DURATION) {
            bulletBody.getFixtureList().get(0).setUserData("Bullet Dead");
        }
        if(!gameCam.frustum.pointInFrustum(new Vector3(bulletBody.getPosition().x, bulletBody.getPosition().y, 0))) {
            bulletBody.getFixtureList().get(0).setUserData("Bullet Dead");
        }
    }

    public void defineBullet() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(player.b2body.getPosition().x, player.b2body.getPosition().y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.linearDamping = .5f;
        bulletBody = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / DeltaDucks.PIXEL_PER_METER);

        fdef.shape = shape;
        fdef.filter.categoryBits = DeltaDucks.BIT_BULLETS;
        fdef.filter.maskBits = DeltaDucks.BIT_PIRATES | DeltaDucks.BIT_LAND | DeltaDucks.BIT_BOUNDARY;
        fdef.restitution = 0.2f;
        bulletBody.createFixture(fdef).setUserData("Bullet Alive");
    }


    public void dispose() {
        world.destroyBody(bulletBody);
    }

}
