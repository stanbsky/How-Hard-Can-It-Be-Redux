package com.ducks.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ducks.DeltaDucks;
import com.ducks.entities.Bullet;
import com.ducks.scenes.Hud;
import com.ducks.tools.B2WorldCreator;
import com.ducks.tools.MyContactListener;
import sprites.Ship;

import java.util.ArrayList;

public class MainGameScreen implements Screen {


    DeltaDucks game;

    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Box2d Variables
    private World world;
    private Box2DDebugRenderer b2dr;

    private Ship player;
    private MyContactListener contactListener;

    private static float ACCELERATION = 0.5f;
    private static float MAX_VELOCITY = 2f;

    public MainGameScreen(DeltaDucks game) {
        this.game = game;
    }

    @Override
    public void show() {
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(DeltaDucks.VIRTUAL_WIDTH / DeltaDucks.PIXEL_PER_METER, DeltaDucks.VIRTUAL_HEIGHT / DeltaDucks.PIXEL_PER_METER, gameCam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("test_map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / DeltaDucks.PIXEL_PER_METER);

        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);

        world = new World(new Vector2(0, 0), true);
        contactListener = new MyContactListener();
        world.setContactListener(contactListener);
        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(world, map);

        player = new Ship(world);

        // Create Sea
//        BodyDef bdef = new BodyDef();
//        bdef.position.set(32 / DeltaDucks.PIXEL_PER_METER, 32 / DeltaDucks.PIXEL_PER_METER);
//        bdef.linearDamping = 0.5f;
//        bdef.type = BodyDef.BodyType.StaticBody;
//        Body body = world.createBody(bdef);
//
//        PolygonShape shape = new PolygonShape();
//        shape.setAsBox(50, 5);
//
//        FixtureDef fdef = new FixtureDef();
//        fdef.shape = shape;
//        body.createFixture(fdef);

    }

    public void handleInput(float deltaTime) {
//
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && player.b2body.getLinearVelocity().y <= MAX_VELOCITY)
            player.b2body.applyForce(new Vector2(0, ACCELERATION), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && player.b2body.getLinearVelocity().y >= -MAX_VELOCITY)
            player.b2body.applyForce(new Vector2(0, -ACCELERATION), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= MAX_VELOCITY)
            player.b2body.applyForce(new Vector2(ACCELERATION, 0), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -MAX_VELOCITY)
            player.b2body.applyForce(new Vector2(-ACCELERATION, 0), player.b2body.getWorldCenter(), true);
//
//        if(player.b2body.getLinearVelocity().x >= -MAX_VELOCITY && player.b2body.getLinearVelocity().x < 0) {
//            if (player.b2body.getLinearVelocity().x + MAX_VELOCITY > 0 && !Gdx.input.isKeyPressed(Input.Keys.LEFT))
//                player.b2body.applyForce(new Vector2(-player.b2body.getLinearVelocity().x, 0), player.b2body.getWorldCenter(), true);
//            player.b2body.applyForce(new Vector2(FRICTION, 0), player.b2body.getWorldCenter(), true);
//        }
//        if(player.b2body.getLinearVelocity().x <= MAX_VELOCITY && player.b2body.getLinearVelocity().x > 0) {
//            if (player.b2body.getLinearVelocity().x - MAX_VELOCITY < 0 && !Gdx.input.isKeyPressed(Input.Keys.RIGHT))
//                player.b2body.applyForce(new Vector2(-player.b2body.getLinearVelocity().x, 0), player.b2body.getWorldCenter(), true);
//            player.b2body.applyForce(new Vector2(-FRICTION, 0), player.b2body.getWorldCenter(), true);
//        }
//        if(player.b2body.getLinearVelocity().y >= -MAX_VELOCITY && player.b2body.getLinearVelocity().y < 0) {
//            if (player.b2body.getLinearVelocity().y + MAX_VELOCITY > 0 && !Gdx.input.isKeyPressed(Input.Keys.DOWN))
//                player.b2body.applyForce(new Vector2(0, -player.b2body.getLinearVelocity().y), player.b2body.getWorldCenter(), true);
//            player.b2body.applyForce(new Vector2(0, FRICTION), player.b2body.getWorldCenter(), true);
//        }
//        if(player.b2body.getLinearVelocity().y <= MAX_VELOCITY && player.b2body.getLinearVelocity().y > 0) {
//            if (player.b2body.getLinearVelocity().y - MAX_VELOCITY < 0 && !Gdx.input.isKeyPressed(Input.Keys.UP))
//                player.b2body.applyForce(new Vector2(0, -player.b2body.getLinearVelocity().y), player.b2body.getWorldCenter(), true);
//            player.b2body.applyForce(new Vector2(0, -FRICTION), player.b2body.getWorldCenter(), true);
//        }
//
//        if (player.b2body.getLinearVelocity().x > 0 && player.b2body.getLinearVelocity().y == 0) {
//            System.out.println("RIGTH");
//        }



//        System.out.println(player.b2body.getLinearVelocity());
    }

    public void update(float deltaTime) {
        handleInput(deltaTime);

        world.step(deltaTime, 6, 2);

        gameCam.position.x = player.b2body.getPosition().x;
        gameCam.position.y = player.b2body.getPosition().y;
        gameCam.update();

        renderer.setView(gameCam);
    }


    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
