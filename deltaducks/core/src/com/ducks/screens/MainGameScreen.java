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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ducks.DeltaDucks;
import com.ducks.entities.Entity;
import com.ducks.managers.*;
import com.ducks.tools.Debug;
import com.ducks.tools.EntityContactListener;
import com.ducks.ui.*;
import com.ducks.entities.Player;
import com.ducks.tools.B2WorldCreator;

import static com.ducks.DeltaDucks.batch;
import static com.ducks.DeltaDucks.scl;

/***
 * Game Screen
 */
public class MainGameScreen implements Screen {
    DeltaDucks game;

    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private static TablePauseMenu pauseMenu;

    private TmxMapLoader mapLoader;
    public static TiledMap map;
    private MapProperties prop;
    private OrthogonalTiledMapRenderer renderer;
    private int mapPixelWidth;
    private int mapPixelHeight;

    private static boolean isPaused = false;
    private boolean escPressed;

    // Box2d Variables
    private World world;


    public static Player player;
    private Crosshair crosshair;
    private Subtitle subtitle;
    private TableHud newhud;

    private EntityContactListener contactListener;

    private QuestManager questManager;

    private boolean debug = false;

    /**
     * Constructor
     * @param game object of DeltaDucks
     */
    public MainGameScreen(DeltaDucks game) {
        this.game = game;
        AssetManager.Initialize();
        Gdx.input.setCursorCatched(true);
    }

    /**
     * Initialize once the screen is visible
     */
    @Override
    public void show() {
        newhud = new TableHud();
        pauseMenu = new TablePauseMenu();
        subtitle = new Subtitle();


        setupGameCam();

        // Set Up Box2D
        world = new World(new Vector2(0, 0), true);
        PhysicsManager.Initialize(world);

        EntityManager.Initialize();
        Debug.Initialize();

        EntityManager.spawnEntities();
        player = new Player();

        contactListener = new EntityContactListener();
        world.setContactListener(contactListener);


        EntityManager.buildWorldMap(world);

        crosshair = new Crosshair();
        questManager = new QuestManager(subtitle);
    }

    /**
     * Handle any Input
     */
    public void handleInput() {
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            EntityManager.spawnBullet();
        }
    }

    private void setupGameCam() {
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(DeltaDucks.VIRTUAL_WIDTH / DeltaDucks.PIXEL_PER_METER, DeltaDucks.VIRTUAL_HEIGHT / DeltaDucks.PIXEL_PER_METER, gameCam);

        // Create Map
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("abi_map.tmx");
        prop = map.getProperties();
        renderer = new OrthogonalTiledMapRenderer(map, DeltaDucks.TILEED_MAP_SCALE / DeltaDucks.PIXEL_PER_METER);

        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);
        mapPixelWidth = mapWidth * tilePixelWidth;
        mapPixelHeight = mapHeight * tilePixelHeight;

        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);
    }

    /**
     * Handle any changes to the game corresponding to the interval of time
     * @param deltaTime of the game
     */
    public void handleTime(float deltaTime) {
        if(StatsManager.getWorldTimer()<=0){
//            Gdx.app.exit();
            this.dispose();
            game.setScreen(new FinalStorylineScreen(this.game, "Lost"));
        }
        if(Player.getHealth()<=0f) {
            game.setScreen(new FinalStorylineScreen(this.game, "Lost"));
        }
        if(!EntityManager.livingCollegesExist()) {
//            game.setScreen(new FinalStorylineScreen(this.game, "Won"));
        }
    }

    public static void togglePause() {
        isPaused = !isPaused;
        Gdx.input.setInputProcessor(isPaused ? pauseMenu : null);
        Gdx.input.setCursorCatched(!isPaused);
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
        subtitle.update(deltaTime);
        crosshair.update(deltaTime);
        EntityManager.update(deltaTime);
        questManager.update(deltaTime);
        PowerupManager.update(deltaTime);
        StatsManager.update(deltaTime);
        Debug.update();

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
                togglePause();
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

        Debug.render(world, gameCam);


        batch.setProjectionMatrix(gameCam.combined);
        batch.begin();
        player.draw();
        crosshair.draw();
        EntityManager.render();
        batch.end();

        // Set our batch to now draw what the Hud camera sees.
//        hud.draw();
        newhud.draw();


        // Display the pause menu, only when necessary
        if (isPaused) {
            pauseMenu.act();
            pauseMenu.draw();
        }

        batch.setProjectionMatrix(subtitle.stage.getCamera().combined);
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
        pauseMenu.getViewport().update(width, height);
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
        Debug.dispose();
//        hud.dispose();
        pauseMenu.dispose();
    }
}
