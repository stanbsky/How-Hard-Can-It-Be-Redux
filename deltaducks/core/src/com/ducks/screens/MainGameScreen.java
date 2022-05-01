package com.ducks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ducks.DeltaDucks;
import com.ducks.managers.*;
import com.ducks.tools.Debug;
import com.ducks.tools.EntityContactListener;
import com.ducks.ui.*;
import com.ducks.entities.Player;

import static com.ducks.DeltaDucks.batch;
import static com.ducks.DeltaDucks.scl;

/***
 * Game Screen
 */
public class MainGameScreen implements Screen {
    DeltaDucks game;

    // World & related mechanics
    public static TiledMap map;
    private World world;
    private Camera camera;
    private EntityContactListener contactListener;

    // UI
    private static PauseMenu pauseMenu;
    private Hud hud;

    // Entities
    public static Player player;
    private Crosshair crosshair;

    private QuestManager questManager;

    /**
     * Constructor
     * @param game object of DeltaDucks
     */
    public MainGameScreen(DeltaDucks game) {
        this.game = game;
        AssetManager.Initialize();
        EntityManager.Initialize();
        QuestManager.Initialise();
        PowerupManager.Initialise();
        Debug.Initialize();
        Gdx.input.setCursorCatched(true);

        // Pause menu variables
        isPaused = false;
        quitToMenu = false;
    }

    /**
     * Initialize once the screen is visible
     */
    @Override
    public void show() {
        camera = new Camera();

        // Set up Box2D
        world = new World(new Vector2(0, 0), true);
        PhysicsManager.Initialize(world);
        contactListener = new EntityContactListener();
        world.setContactListener(contactListener);
        EntityManager.buildWorldMap(world);

        // Set up entities
        EntityManager.spawnEntities();
        player = new Player();

        // Set up UI
        hud = new Hud();
        pauseMenu = new PauseMenu();
        crosshair = new Crosshair();

        questManager = new QuestManager();
    }

    /**
     * Update the window every delta time interval
     * @param deltaTime of the game
     */
    public void update(float deltaTime) {
        questManager.checkForGameOver(this);

        // Step forward box2Dworld simulation
        world.step(deltaTime, 6, 2);

        // Update all entities
        crosshair.update(deltaTime);
        player.update(deltaTime);
        EntityManager.update(deltaTime);
        questManager.update(deltaTime);
        PowerupManager.update(deltaTime);
        StatsManager.update(deltaTime);
        Debug.update();

        camera.update();
    }
    /**
     * Render the window
     * @param delta time of the game
     */
    @Override
    public void render(float delta) {
        checkPausedStatus();
        updateGameUnlessPaused(delta);

        // Draw game map and entities
        camera.render();
        batch.setProjectionMatrix(camera.projection);
        batch.begin();
        crosshair.draw();
        EntityManager.render();
        player.draw();
        batch.end();



        // Draw UI elements
        hud.draw();
        if (isPaused)
            showPauseMenu();

        // TODO: remove once Subtitle is refactored
//        batch.setProjectionMatrix(subtitle.stage.getCamera().combined);
        //subtitle.stage.draw();

    }

    /**
     * Dispose the unwanted objects
     */
    @Override
    public void dispose() {
        map.dispose();
        camera.renderer.dispose();
//        world.dispose();
        Debug.dispose();
//        hud.dispose();
        pauseMenu.dispose();
    }

    /**
     * Resize the window
     * @param width of new window
     * @param height of new window
     */
    @Override
    public void resize(int width, int height) {
        camera.resize(width, height);
        pauseMenu.getViewport().update(width, height);
    }

    public void gameOver(boolean won) {
//        this.dispose(); crashed the game
        game.setScreen(new EndgameScreen(game, won));
//        game.setScreen(new FinalStorylineScreen(game, status));
    }


    // PAUSE MENU RELATED FEATURES
    // NB: these are not hidden in an inner class due to
    // the need for static access in UI buttons to toggle pause

    private static boolean isPaused = false;
    private boolean escPressed;
    public static boolean quitToMenu = false;

    public static void togglePause() {
        isPaused = !isPaused;
        Gdx.input.setInputProcessor(isPaused ? pauseMenu : null);
        Gdx.input.setCursorCatched(!isPaused);
    }

    private void updateGameUnlessPaused(float delta) {
        if (!isPaused){
            update(delta);
        }
    }

    private void checkPausedStatus() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            if (!escPressed) {
                escPressed = true;
                togglePause();
            }
        } else if (escPressed) {
            escPressed = false;
        }
    }

    private void showPauseMenu() {
        pauseMenu.act();
        pauseMenu.draw();
        if (quitToMenu) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    private class Camera {
        public OrthographicCamera gameCam;
        public Viewport gamePort;
        public MapProperties prop;
        public OrthogonalTiledMapRenderer renderer;
        public int mapPixelWidth;
        public int mapPixelHeight;
        public Matrix4 projection;

        public Camera() {
            setupGameCam();
        }

        private void setupGameCam() {
            gameCam = new OrthographicCamera();
            gamePort = new FitViewport(DeltaDucks.VIRTUAL_WIDTH / DeltaDucks.PIXEL_PER_METER, DeltaDucks.VIRTUAL_HEIGHT / DeltaDucks.PIXEL_PER_METER, gameCam);

            // Create Map
            TmxMapLoader mapLoader = new TmxMapLoader();
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
            projection = gameCam.combined;
        }

        public void update() {
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

        public void render() {
            Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            // Render our game map
            renderer.render();
            Debug.render(world, gameCam);
        }

        public void resize(int width, int height) {
            gamePort.update(width, height);
        }

        public void dispose() {
            renderer.dispose();
        }
    }

    // BOILERPLATE OVERRIDES

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
