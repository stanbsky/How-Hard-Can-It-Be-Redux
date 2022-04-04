package com.ducks.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ducks.DeltaDucks;
import com.ducks.components.Texture;
import com.ducks.screens.MainGameScreen;

import static com.ducks.DeltaDucks.scl;

/**
 * Crosshair Class for Box2D Body and Sprite
 */
public class Crosshair {
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

    private Texture texture;


    private float crosshairRadius = 1.0f;

    static Vector2 points;

    private float angle = 45f;

    private int tick;

    Viewport gamePort;

    /**
     * Constructor
     * @param world Box2D world
     * @param screen Game Screen
     * @param player Box2D object of player
     * @param gameCam OrthographicCamera
     * @param gamePort Viewport
     */
    public Crosshair(World world, MainGameScreen screen, Ship player, OrthographicCamera gameCam, Viewport gamePort) {
//        super(MainGameScreen.resources.getTexture("mehnat"));
        this.world = world;
        this.player = player;
        this.gameCam = gameCam;
        this.gamePort = gamePort;
        tick = 0;

        points = new Vector2(0, 0);
        this.texture = new Texture("crosshair256", points, scl(25.6f));

//        Array<TextureRegion> frames = new Array<TextureRegion>();
//
//        frames.add(new TextureRegion(getTexture(), 1 * PIXEL_CROSSHAIR_WIDTH, 2 * PIXEL_CROSSHAIR_HEIGHT, PIXEL_CROSSHAIR_WIDTH, PIXEL_CROSSHAIR_HEIGHT));
//        crosshairIdle = new Animation(0.1f, frames);
//        frames.clear();
//
//        setBounds(player.b2body.getPosition().x, player.b2body.getPosition().y, CROSSHAIR_WIDTH / DeltaDucks.PIXEL_PER_METER, CROSSHAIR_HEIGHT / DeltaDucks.PIXEL_PER_METER);
//        setRegion(crosshairIdle.getKeyFrame(stateTime, true));
//
//        points = new Vector2(0, 0);
//        Gdx.graphics.setCursor(Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("bunny.png")), (int)getWidth()/2, (int)getHeight()/2));
    }

    /**
     * Update the crosshair every delta time interval
     * @param deltaTime of the game
     */
    public void update(float deltaTime) {
        stateTime += deltaTime;

        points = getPosition();
//        this.texture.x = points.x;
//        this.texture.y = points.y;
        this.texture.update(deltaTime, points);
//        this.texture.x = player.b2body.getPosition().x + points.x - this.texture.width / 2;
//        this.texture.y = player.b2body.getPosition().y + points.y - this.texture.height / 2;
//        tick++;
//        if (tick % 15 == 0) {
//            System.out.println("SH: " + player.x + "," + player.y);
//            System.out.println("C2: " + points);
//            System.out.println(Crosshair.points.sub(points));
//            System.out.println("C2: " + this.texture.width);
//        }
//        setPosition(player.b2body.getPosition().x + points.x - getWidth()/2, player.b2body.getPosition().y + points.y - getWidth()/2);

    }

    public void draw(SpriteBatch batch) {
        this.texture.render(batch);
    }

    public Vector2 getPosition() {
        Vector3 loc = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        gameCam.unproject(loc, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        float x = player.b2body.getPosition().x - loc.x - this.texture.getWidth()/2 + player.b2body.getFixtureList().get(0).getShape().getRadius();
        float y = player.b2body.getPosition().y - loc.y - this.texture.getHeight()/2 + player.b2body.getFixtureList().get(0).getShape().getRadius();
        Vector2 loc2 = new Vector2(x, y).nor().scl(-1f * crosshairRadius);
        loc2.x = player.b2body.getPosition().x + loc2.x;// - this.texture.width / 2;
        loc2.y = player.b2body.getPosition().y + loc2.y;// - this.texture.height / 2;
        return loc2;
    }

    /**
     * get X and Y coordinates of the crosshair
     * @return Vector2
     */
    public static Vector2 getCrosshair() {
        return points;
    }

    /**
     * get Unit (Directional) Vector between two Vectors (points)
     * @param body vector (measured from)
     * @param target vector (measured to)
     * @return Vector2
     */
    public static Vector2 getDirection(Vector2 body, Vector2 target) {
        return new Vector2(body.x - target.x, body.y - target.y).nor().scl(-1);
    }
}