package com.ducks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ducks.DeltaDucks;
import com.ducks.entities.*;
import com.ducks.scenes.*;
import com.ducks.sprites.Player;
import com.ducks.tools.B2WorldCreator;
import com.ducks.tools.Content;
import com.ducks.tools.MyContactListener;
import com.ducks.sprites.Crosshair;

import static com.ducks.DeltaDucks.scl;

/***
 * Game Screen
 */
public class MainGameScreen implements Screen {
    DeltaDucks game;

    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;
    private PauseMenu pauseMenu;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private MapProperties prop;
    private OrthogonalTiledMapRenderer renderer;
    private int mapPixelWidth;
    private int mapPixelHeight;

    private boolean isPaused;
    private boolean escPressed;

    // Box2d Variables
    private World world;
    private Box2DDebugRenderer b2dr;

    private Player player;
    private ListOfPirates bots;
    private ListOfMonsters creatures;
    private ListOfColleges colleges;
    private Minimap radar;
    private Crosshair crosshair;
    private Tutorial tutorial;
    private Subtitle subtitle;
    private ListOfBullets bullets;
    private ListOfEnemyBullets enemyBullets;

    private MyContactListener contactListener;

    public static Content resources;
    private static TextureAtlas atlas;

    /**
     * Constructor
     * @param game object of DeltaDucks
     */
    public MainGameScreen(DeltaDucks game) {
        this.game = game;
        resources = new Content();
        atlas = new TextureAtlas("all_assets.atlas");
        //TODO: delete after pirate refactor
        MainGameScreen.resources.loadTexture("ship_dark_SE.png", "pirate");
        MainGameScreen.resources.loadTexture("arrow.png", "arrow");

        Gdx.input.setCursorCatched(true);
    }

    /**
     * Method to get the texture atlas
     * @return texture packs
     */
    public static TextureAtlas getAtlas() { return atlas;}

    /**
     * Initialize once the screen is visible
     */
    @Override
    public void show() {
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(DeltaDucks.VIRTUAL_WIDTH / DeltaDucks.PIXEL_PER_METER, DeltaDucks.VIRTUAL_HEIGHT / DeltaDucks.PIXEL_PER_METER, gameCam);
        hud = new Hud(game.batch);
        pauseMenu = new PauseMenu();
        subtitle = new Subtitle(game.batch);

        // Create Map
        mapLoader = new TmxMapLoader();
//        map = mapLoader.load("test_map.tmx");
        map = mapLoader.load("new map.tmx");
        prop = map.getProperties();
        renderer = new OrthogonalTiledMapRenderer(map, DeltaDucks.TILEED_MAP_SCALE / DeltaDucks.PIXEL_PER_METER);

        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);
        mapPixelWidth = mapWidth * tilePixelWidth;
        mapPixelHeight = mapHeight * tilePixelHeight;


        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);


        // Set Up Box2D
        world = new World(new Vector2(0, 0), true);
        PhysicsManager.Initialize(world);

        player = new Player();

        contactListener = new MyContactListener(player, subtitle);
        world.setContactListener(contactListener);
        //TODO: debug rendering
        b2dr = new Box2DDebugRenderer(true, false, true, true, false, true);

        new B2WorldCreator(world, map);

        creatures = new ListOfMonsters(world, this);
        radar = new Minimap(gameCam, mapPixelWidth, mapPixelHeight);
        crosshair = new Crosshair(player);
        bullets = new ListOfBullets(player);
        enemyBullets = new ListOfEnemyBullets(player);
        colleges = new ListOfColleges(enemyBullets, map);
        bots = new ListOfPirates(enemyBullets, world, this, map);
        tutorial = new Tutorial(gameCam, player);
    }

    /**
     * Handle any Input
     */
    public void handleInput() {
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            bullets.spawnBullet();
        }
    }

    /**
     * Handle any changes to the game corresponding to the interval of time
     * @param deltaTime of the game
     */
    public void handleTime(float deltaTime) {
        if(hud.getTimer()<0.1f){
//            Gdx.app.exit();
            this.dispose();
            game.setScreen(new FinalStorylineScreen(this.game, "Lost"));
        }
        if(hud.getHealth()<=0f) {
            game.setScreen(new FinalStorylineScreen(this.game, "Lost"));
        }
        if(colleges.getNumbersOfColleges()<=0) {
            game.setScreen(new FinalStorylineScreen(this.game, "Won"));
        }
    }

    /**
     * Update the window every delta time interval
     * @param deltaTime of the game
     */
    public void update(float deltaTime) {
        handleInput();
        handleTime(deltaTime);

        world.step(deltaTime, 6, 2);

        player.update(deltaTime);
        bots.update(deltaTime);
        creatures.update(deltaTime);
        colleges.update(deltaTime);
        hud.update(deltaTime);
        radar.update(player, colleges);
        tutorial.update(deltaTime);
        subtitle.update(deltaTime);
        crosshair.update(deltaTime);
        bullets.update(deltaTime);
        enemyBullets.update(deltaTime);

        gameCam.position.x = player.getPosition().x;
        gameCam.position.y = player.getPosition().y;

        // Keeps camera centered if the ship reaches the edge of the map
        gameCam.position.x = MathUtils.clamp(gameCam.position.x,
                gameCam.viewportWidth/2, scl(mapPixelWidth) - gameCam.viewportWidth/2);
        gameCam.position.y = MathUtils.clamp(gameCam.position.y,
                gameCam.viewportHeight/2, scl(mapPixelHeight) - gameCam.viewportHeight/2);

        gameCam.update();

        renderer.setView(gameCam);
    }

    /**
     * Render the window
     * @param delta time of the game
     */
    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            if (!escPressed) {
                escPressed = true;
                Gdx.input.setCursorCatched(isPaused);
                isPaused = !isPaused;
            }
        }
        else if (escPressed) {
            escPressed = false;
        }
        if (!isPaused) {
            update(delta);
        }
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render our game map
        renderer.render();

        //TODO: debug rendering
        // Render our Box2DDebugLines
        b2dr.render(world, gameCam.combined);


        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        bots.draw(game.batch);
        creatures.draw(game.batch);
        colleges.draw(game.batch);
        radar.draw(game.batch);
        tutorial.draw(game.batch);
        bullets.draw(game.batch);
        enemyBullets.draw(game.batch);
        player.draw(game.batch);
        crosshair.draw(game.batch);
        game.batch.end();


        // Set our batch to now draw what the Hud camera sees.
        hud.draw(game.batch);


        // Display the pause menu, only when necessary
        if (isPaused) {
            pauseMenu.draw(game.batch);
        }

        game.batch.setProjectionMatrix(subtitle.stage.getCamera().combined);
        subtitle.stage.draw();

    }

    /**
     * Resize the window
     * @param width of new window
     * @param height of new window
     */
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

    /**
     * Dispose the unwanted objects
     */
    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
//        world.dispose();
        //TODO: debug rendering
        b2dr.dispose();
//        hud.dispose();
        radar.dispose();
    }
}
