package com.ducks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ducks.DeltaDucks;
import com.ducks.scenes.Hud;
import com.ducks.tools.B2WorldCreator;
import com.ducks.tools.Content;
import com.ducks.tools.MyContactListener;
import sprites.Ship;

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
//    private Player player;
    private MyContactListener contactListener;

    private static float ACCELERATION = 0.5f;
    private static float MAX_VELOCITY = 2f;

    public static Content resources;
    private TextureAtlas atlas;


    public MainGameScreen(DeltaDucks game) {
        this.game = game;
        resources = new Content();
        atlas = new TextureAtlas("sprites/ship.pack");
    }

    public TextureAtlas getAtlas() { return atlas;}

    @Override
    public void show() {
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(DeltaDucks.VIRTUAL_WIDTH / DeltaDucks.PIXEL_PER_METER, DeltaDucks.VIRTUAL_HEIGHT / DeltaDucks.PIXEL_PER_METER, gameCam);
        hud = new Hud(game.batch);

        // Create Map
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("test_map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / DeltaDucks.PIXEL_PER_METER);

        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);

        // Set Up Box2D
        world = new World(new Vector2(0, 0), true);
        contactListener = new MyContactListener();
        world.setContactListener(contactListener);
        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(world, map);

        // Create Player
        player = new Ship(world, this);
//        player = new Player(body.b2body);


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

    }

    public void update(float deltaTime) {
        handleInput(deltaTime);

        world.step(deltaTime, 6, 2);

        player.update(deltaTime);

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

        // Render our game map
        renderer.render();

        // Render our Box2DDebugLines
        b2dr.render(world, gameCam.combined);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        // Set our batch to now draw what the Hud camera sees.
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

//        player.render(game.batch);


//        game.batch.draw(resources.getTexture("badlogic"),0,0);

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
