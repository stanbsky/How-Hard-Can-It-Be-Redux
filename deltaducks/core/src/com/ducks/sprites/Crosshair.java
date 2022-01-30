package com.ducks.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ducks.DeltaDucks;
import com.ducks.screens.MainGameScreen;

public class Crosshair extends Sprite {
    private World world;
    private Ship player;

    private Animation<TextureRegion> crosshairIdle;

    private final int PIXEL_CROSSHAIR_WIDTH = 256;
    private final int PIXEL_CROSSHAIR_HEIGHT = 256;

    private final float CROSSHAIR_WIDTH = PIXEL_CROSSHAIR_WIDTH * .2f;
    private final float CROSSHAIR_HEIGHT = PIXEL_CROSSHAIR_HEIGHT * .2f;

    private float stateTime;

    private static Vector2 mouseInWorld2D = new Vector2();
    private  Vector3 mouseInWorld3D = new Vector3();
    private OrthographicCamera gameCam;


    float worldCordX;
    float worldCordY;

    static Vector2 points;

    private float angle = 45f;

    Viewport gamePort;
    public Crosshair(World world, MainGameScreen screen, Ship player, OrthographicCamera gameCam, Viewport gamePort) {
        super(MainGameScreen.resources.getTexture("mehnat"));
        this.world = world;
        this.player = player;
        this.gameCam = gameCam;
        this.gamePort = gamePort;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        frames.add(new TextureRegion(getTexture(), 1 * PIXEL_CROSSHAIR_WIDTH, 2 * PIXEL_CROSSHAIR_HEIGHT, PIXEL_CROSSHAIR_WIDTH, PIXEL_CROSSHAIR_HEIGHT));
        crosshairIdle = new Animation(0.1f, frames);
        frames.clear();

        setBounds(player.b2body.getPosition().x, player.b2body.getPosition().y, CROSSHAIR_WIDTH / DeltaDucks.PIXEL_PER_METER, CROSSHAIR_HEIGHT / DeltaDucks.PIXEL_PER_METER);
        setRegion(crosshairIdle.getKeyFrame(stateTime, true));

//        Gdx.graphics.setCursor(Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("bunny.png")), (int)getWidth()/2, (int)getHeight()/2));
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;

        Vector3 loc = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        gameCam.unproject(loc, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        worldCordX = player.b2body.getPosition().x - loc.x  + player.b2body.getFixtureList().get(0).getShape().getRadius();
        worldCordY = player.b2body.getPosition().y - loc.y  + player.b2body.getFixtureList().get(0).getShape().getRadius();
        points = new Vector2(worldCordX, worldCordY).nor().scl(-1);

        setPosition(player.b2body.getPosition().x + points.x, player.b2body.getPosition().y + points.y);

    }

    public static float getCrosshairX() {
        return points.x;
    }

    public static float getCrosshairY() {
        return points.y;
    }

    public static Vector2 getCrosshair() {
        return points;
    }
}
