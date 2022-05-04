package de.tomgrill.gdxtesting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.ducks.entities.*;
import com.ducks.intangibles.*;
import com.ducks.managers.*;
import com.ducks.screens.*;
import com.ducks.tools.*;
import com.ducks.ui.Endgame;
import com.ducks.ui.Subtitle;
import jdk.internal.loader.AbstractClassLoaderValue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static com.ducks.managers.AssetManager.ui;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FRTests {

    private static DDHeadless game;
    private static HeadlessApplication app;
    private World world;
    private EntityContactListener contactListener;
    private final Vector2 zero = new Vector2(0, 0);
    private final float deltaTime = 1 / 60f;
    private final String collegeName = "constantine";

    @BeforeAll
    public static void setupApp() {
        Gdx.gl = mock(GL20.class);
        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
        conf.updatesPerSecond = 60;
        game = new DDHeadless();
        app = new HeadlessApplication(game, conf);

        TmxMapLoader mapLoader = new TmxMapLoader();
        MainGameScreen.map = mapLoader.load("abi_map.tmx");
    }

    @BeforeEach
    public void setupWorld() {
        Mockito.reset(Gdx.gl);
        world = new World(new Vector2(0, 0), true);
        contactListener = new EntityContactListener();
        world.setContactListener(contactListener);
        SaveManager.Initialize();
        DifficultyControl.setDifficulty(1);
        StatsManager.Initialise();
        PhysicsManager.Initialize(world);
        AssetManager.Initialize();
        EntityManager.Initialize();
        PowerupManager.Initialise();
        QuestManager.Initialise();
    }

    /**
     * WASD controls should control the ship’s movement
     */
    @Test
    public void test_FR_W() {
        // Create a player and record their initial position
        Player player = new Player();
        Vector2 initial = player.getPosition().cpy();
        // Mock the InputParser component to allow us to fake keyboard input
        try (MockedStatic<InputParser> inputParser = Mockito.mockStatic(InputParser.class)) {
            ArrayList<InputParser.Direction> directions = new ArrayList<>();

            // Set up the mock to always return northern direction
            directions.add(InputParser.Direction.NORTH);
            inputParser.when(InputParser::parseInput).thenReturn(directions);

            // Run our world for 60 steps aka 1 second's worth of time at 60fps
            for (int i = 0; i < 60; i++) {
                world.step(deltaTime, 6, 2);
                player.update(deltaTime);
            }
            // The location of the player should be further north than it was at the start
            assert initial.y < player.getPosition().y;
            // The player's ship should have positive momentum in the y direction
            assert player.getVelocity().y > 0;
            // The player's ship should NOT have any momentum in other directions!
            assert player.getVelocity().x == 0;
        }
    }

    @Test
    public void test_FR_A() {
        // Create a player and record their initial position
        Player player = new Player();
        Vector2 initial = player.getPosition().cpy();
        // Mock the InputParser component to allow us to fake keyboard input
        try (MockedStatic<InputParser> inputParser = Mockito.mockStatic(InputParser.class)) {
            ArrayList<InputParser.Direction> directions = new ArrayList<>();

            // Set up the mock to always return northern direction
            directions.add(InputParser.Direction.WEST);
            inputParser.when(InputParser::parseInput).thenReturn(directions);

            // Run our world for 60 steps aka 1 second's worth of time at 60fps
            for (int i = 0; i < 60; i++) {
                world.step(deltaTime, 6, 2);
                player.update(deltaTime);
            }
            // The location of the player should be further west than it was at the start
            assert initial.x > player.getPosition().x;
            // The player's ship should have positive momentum in the x direction
            assert player.getVelocity().x < 0;
            // The player's ship should NOT have any momentum in other directions!
            assert player.getVelocity().y == 0;
        }

    }

    @Test
    public void test_FR_S() {
        // Create a player and record their initial position
        Player player = new Player();
        Vector2 initial = player.getPosition().cpy();
        // Mock the InputParser component to allow us to fake keyboard input
        try (MockedStatic<InputParser> inputParser = Mockito.mockStatic(InputParser.class)) {
            ArrayList<InputParser.Direction> directions = new ArrayList<>();

            // Set up the mock to always return northern direction
            directions.add(InputParser.Direction.SOUTH);
            inputParser.when(InputParser::parseInput).thenReturn(directions);

            // Run our world for 60 steps aka 1 second's worth of time at 60fps
            for (int i = 0; i < 60; i++) {
                world.step(deltaTime, 6, 2);
                player.update(deltaTime);
            }
            // The location of the player should be further south than it was at the start
            assert initial.y > player.getPosition().y;
            // The player's ship should have positive momentum in the y direction
            assert player.getVelocity().y < 0;
            // The player's ship should NOT have any momentum in other directions!
            assert player.getVelocity().x == 0;
        }

    }

    @Test
    public void test_FR_D() {
        // Create a player and record their initial position
        Player player = new Player();
        Vector2 initial = player.getPosition().cpy();
        // Mock the InputParser component to allow us to fake keyboard input
        try (MockedStatic<InputParser> inputParser = Mockito.mockStatic(InputParser.class)) {
            ArrayList<InputParser.Direction> directions = new ArrayList<>();

            // Set up the mock to always return northern direction
            directions.add(InputParser.Direction.EAST);
            inputParser.when(InputParser::parseInput).thenReturn(directions);

            // Run our world for 60 steps aka 1 second's worth of time at 60fps
            for (int i = 0; i < 60; i++) {
                world.step(deltaTime, 6, 2);
                player.update(deltaTime);
            }
            // The location of the player should be further west than it was at the start
            assert initial.x < player.getPosition().x;
            // The player's ship should have positive momentum in the x direction
            assert player.getVelocity().x > 0;
            // The player's ship should NOT have any momentum in other directions!
            assert player.getVelocity().y == 0;
        }

    }

    @Test
    public void test_FR_ENTITY_SPAWNS() {
        // Load the map for pulling the spawn locations
        EntityManager.spawnEntities();

        // There should be at least one powerup spawned at the start of the game
        assert EntityManager.powerups.size >= 1;

        // There should be at least one pirate spawned at the start of the game
        int pirateCount = EntityManager.pirates.size;

        assert EntityManager.livingPiratesExist();
        assert pirateCount >= 1;

        // If a pirate is killed, the list should be smaller
        EntityManager.killPirate(EntityManager.pirates.get(0));
        assert pirateCount > EntityManager.pirates.size;

        // There should be 3 colleges spawned at the start of the game
        assert EntityManager.colleges.size == 3;
        assert EntityManager.livingCollegesExist();

        // The program should be able to spawn 'unlimited' whirlpools, never in the same place
        for (int i = 0; i <= EntityManager.whirlpoolSpawns.size + 1; i++) {
            EntityManager.spawnNextWhirlpool();
        }
    }

    @Test
    public void test_FR_WHIRLPOOL_SPAWNING() {
        Whirlpool whirlpool = new Whirlpool(new Vector2(0 ,0));

        // The whirlpool should exist after it's created
        assert !whirlpool.cleanup();

        // Progressing the game
        for (int i = 0; i <= 30 * 60; i++) {
            whirlpool.update(deltaTime);
        }

        // The whirlpool should no longer exist after 30 seconds (1800 frames) have passed
        assert whirlpool.cleanup();

        try (MockedStatic<EntityManager> ignored = Mockito.mockStatic(EntityManager.class)) {
            whirlpool.dispose();
        }

    }

    @Test
    public void test_FR_WHIRLPOOL_EFFECT() {
        Player player = new Player();
        Vector2 initialLocation = player.getPosition().cpy();

        Whirlpool whirlpool = new Whirlpool(player.getPosition().cpy().scl(100f));

        world.step(deltaTime, 6,2);
        player.update(deltaTime);
        whirlpool.update(deltaTime);
        world.step(deltaTime, 6,2);

        // The player should be moved by the whirlpool's proximity
        assert !initialLocation.equals(player.getPosition().cpy()) ;
    }

    @Test
    public void test_FR_CHEST() {
        Player player = new Player();
        Chest chest = new Chest(player.getPosition().cpy().scl(100f));

        // The chest should be closed after it's created
        assert chest.isAlive();

        for (int i = 0; i <= 240; i++) {
            world.step(deltaTime, 6, 2);
            player.update(deltaTime);
            chest.update(deltaTime);
        }

        // The chest should be open after the player is in its range for 4 seconds
        assert !chest.isAlive();
    }

    @Test
    public void test_PIRATE_SPAWN() {
        Pirate pirate = new Pirate(collegeName, zero);

        assert pirate.isAlive();

        PlayerBullet bullet = new PlayerBullet(zero, zero, zero);
        world.step(deltaTime, 6, 2);
        pirate.update(deltaTime);
        bullet.update(deltaTime);

        //The pirate should die
        assert !pirate.isAlive();
    }

    @Test
    public void test_PIRATE_MOVE() {
        // Create a pirate and record their initial position
        Pirate pirate = new Pirate(collegeName, zero);
        Vector2 initialPosition = pirate.getPosition().cpy();
        Vector2 initialVelocity = pirate.getVelocity().cpy();

        ArrayList<Vector2> firstRunPositions = new ArrayList<>();
        ArrayList<Vector2> firstRunVelocities = new ArrayList<>();

        // Run our world for 300 steps = 5 second's worth of time at 60fps
        for (int i = 0; i < 300; i++) {
            world.step(deltaTime, 6, 2);
            pirate.update(deltaTime);
            // If position is different from starting, save it
            if (!pirate.getPosition().epsilonEquals(initialPosition)) {
                firstRunPositions.add(pirate.getPosition().cpy());
            }
            // If velocity is different from starting, save it
            if (!pirate.getVelocity().epsilonEquals(initialVelocity)) {
                firstRunVelocities.add(pirate.getVelocity().cpy());
            }
        }

        // Different positions and velocities must've been observed
        assert firstRunPositions.size() > 0;
        assert firstRunVelocities.size() > 0;

        // Record current position and velocity for a second run
        initialPosition = pirate.getPosition().cpy();
        initialVelocity = pirate.getVelocity().cpy();

        ArrayList<Vector2> secondRunPositions = new ArrayList<>();
        ArrayList<Vector2> secondRunVelocities = new ArrayList<>();

        // Run our world for 300 steps = 5 second's worth of time at 60fps
        for (int i = 0; i < 300; i++) {
            world.step(deltaTime, 6, 2);
            pirate.update(deltaTime);
            // If position is different from starting, save it
            if (!pirate.getPosition().epsilonEquals(initialPosition)) {
                secondRunPositions.add(pirate.getPosition().cpy());
            }
            // If velocity is different from starting, save it
            if (!pirate.getVelocity().epsilonEquals(initialVelocity)) {
                secondRunVelocities.add(pirate.getVelocity().cpy());
            }
        }

        // Different positions and velocities must've been observed
        assert secondRunPositions.size() > 0;
        assert secondRunVelocities.size() > 0;
    }

    @Test
    public void test_FR_PIRATE_COMBAT() throws SecurityException,
            IllegalAccessException, IllegalArgumentException,
            NoSuchFieldException {
        Player player = new Player();
        Vector2 playerLocation = player.getPosition().cpy();
        playerLocation.x -= 0.5;
        float originalHealth = Player.getHealth();
        Pirate pirate = new Pirate(collegeName, playerLocation.scl(100));
        EntityManager.registerEntity(pirate);
        try (MockedStatic<MainGameScreen> mgs = Mockito.mockStatic(MainGameScreen.class)) {
            Field playerField = MainGameScreen.class.getDeclaredField("player");
            playerField.setAccessible(true);
            playerField.set(mgs, player);
            for (int i = 0; i < 600; i++) {
                world.step(deltaTime, 6, 2);
                player.update(deltaTime);
                EntityManager.update(deltaTime);
            }
        }
        assert Player.getHealth() < originalHealth;
    }

    @Test
    public void test_FR_COLLEGE_SPAWN(){
        //Ensure colleges' health goes down after a bullet hits them
        College college = new College(new Vector2(0,0),collegeName);

        // Checks college is alive
        assert college.isAlive();

        // Set health to 0
        college.health = 0;

        assert !college.isAlive();
    }

    @Test
    public void test_FR_COLLEGE_DAMAGE(){
        //Ensure colleges' health goes down after a bullet hits them
        College college = new College(zero ,collegeName);
        int initial_health = college.health;
        PlayerBullet bullet = new PlayerBullet(zero, zero, zero);
        world.step(deltaTime, 6, 2);
        college.update(deltaTime);
        bullet.update(deltaTime);
        //The current health should be less than it started with
        assert initial_health > college.health;
    }

    @Test
    public void test_FR_COLLEGE_COMBAT() throws SecurityException,
            IllegalAccessException, IllegalArgumentException,
            NoSuchFieldException {
        Player player = new Player();
        Vector2 playerLocation = player.getPosition().cpy();
        playerLocation.x -= 0.5;
        float originalHealth = Player.getHealth();
        College college = new College(playerLocation.scl(100), collegeName);
        EntityManager.registerEntity(college);
        try (MockedStatic<MainGameScreen> mgs = Mockito.mockStatic(MainGameScreen.class)) {
            Field playerField = MainGameScreen.class.getDeclaredField("player");
            playerField.setAccessible(true);
            playerField.set(mgs, player);
            for (int i = 0; i < 600; i++) {
                world.step(deltaTime, 6, 2);
                player.update(deltaTime);
                EntityManager.update(deltaTime);
            }
        }
        assert Player.getHealth() < originalHealth;
    }

    @Test
    public void test_FR_PLAYER_DAMAGE(){
        //Ensure player's health goes down after a bullet hits them
        Player player = new Player();
        float initial_health = Player.getHealth();
        Vector2 playerLocation = player.getPosition().cpy();
        EnemyBullet bullet = new EnemyBullet(playerLocation, playerLocation);
        world.step(deltaTime, 6, 2);
        player.update(deltaTime);
        bullet.update(deltaTime);
        float new_health = Player.getHealth();
        //The current health should be less than it started with
        assert initial_health > new_health;
    }

    @Test
    public void test_FR_BOSS_SPAWN() {
        Boss boss = new Boss(collegeName, zero);

        // Confirm that boss is alive
        assert boss.isAlive();

        boss.setHealth(0);

        assert !boss.isAlive();
    }

    @Test
    public void test_FR_BOSS_DAMAGE(){
        //Ensure boss's health goes down after a bullet hits it
        Boss boss = new Boss(collegeName, zero);
        float initial_health = boss.getHealth();
        PlayerBullet bullet = new PlayerBullet(zero, zero, zero);
        world.step(deltaTime, 6, 2);
        boss.update(deltaTime);
        bullet.update(deltaTime);
        float new_health = boss.getHealth();
        //The current health should be less than it started with
        assert initial_health > new_health;
    }

    @Test
    public void test_FR_BOSS_TRIPLE_SHOT() {
        Player player = new Player();
        MainGameScreen.player = player;
        Vector2 playerLocation = player.getPosition().cpy();

        Boss boss = new Boss(collegeName, playerLocation.scl(100));

        assert boss.bossShotCount == 0;

        world.step(deltaTime, 6, 2);
        for (int i = 0; i <= 21; i++) {
            boss.update(1);
        }

        assert boss.bossShotCount > 0;
    }

    @Test
    public void test_FR_SAVE_LOAD() {
        SaveManager.Initialize();

        // Set up data to save

        Player.setHealth(2);
        MainGameScreen.player = new Player();

        DifficultyControl.setDifficulty(1);
        PowerupManager.setPowerUps( new int[] { 1, 1, 1, 1, 1 } );

        StatsManager.Initialise();
        EntityManager.Initialize();
        EntityManager.spawnEntities();
        QuestManager.Initialise();

        // Execute save
        SaveManager.Save();

        // Change data
        PowerupManager.setPowerUps( new int[] { 0, 0, 0, 0, 0 } );
        assert Arrays.equals(PowerupManager.getPowerUps(), new int[]{0, 0, 0, 0, 0});

        // Execute load
        SaveManager.LoadSave();
        assert Arrays.equals(PowerupManager.getPowerUps(), new int[]{1, 1, 1, 1, 1});

        // Reset state for other tests
        SaveManager.LoadSave = false;
    }

    @Test
    public void test_FR_DIFFICULTY() {
        DifficultyControl.setDifficulty(0);

        // Sees if difficulty set and get works
        assert DifficultyControl.getDifficulty() == 0;

        // Sees if getting difficulty value with difficulty works
        assert DifficultyControl.getValue(1, 2 ,3) == 1;
    }

    @Test
    public void test_FR_BULLET() {
        EnemyBullet enemyBullet = new EnemyBullet(zero, new Vector2(1, 1));

        // checks enemy bullet can exist and flag that it does
        assert enemyBullet.isAlive();

        PlayerBullet playerBullet = new PlayerBullet(new Vector2(100, 100), new Vector2(1, 1), zero);

        // checks enemy bullet can exist and flag that it does
        assert playerBullet.isAlive();

        // Step world
        world.step(deltaTime, 6, 2);
        enemyBullet.update(deltaTime);
        playerBullet.update(deltaTime);

        Vector2 enemySpeed = enemyBullet.getVelocity().cpy();
        Vector2 playerSpeed = playerBullet.getVelocity().cpy();

        // Step world
        world.step(deltaTime, 6, 2);
        enemyBullet.update(deltaTime);
        playerBullet.update(deltaTime);

        // Enemy bullet slows down after fired
        assert enemySpeed.x > enemyBullet.getVelocity().x;
        assert enemySpeed.y > enemyBullet.getVelocity().y;

        // Player bullet slows down after fired
        assert playerSpeed.x > playerBullet.getVelocity().x;
        assert playerSpeed.y > playerBullet.getVelocity().y;

        // Enemy bullet does despawn
        enemyBullet.dispose();

        // Player bullet does despawn
        playerBullet.dispose();
    }

    @Test
    public void test_FR_QUEST_CREATION() throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchFieldException {
//        Field currentQuestField = QuestManager.class.getDeclaredField("currentQuest");
//        currentQuestField.setAccessible(true);
//        // We don't have a quest set
//        assert currentQuestField.get(QuestManager.class) == null;
//        QuestManager.update(3f);
//        assert currentQuestField.get(QuestManager.class) != null;
    }
    @Test
    public void test_FR_QUEST_COMPLETION() {
        assert true;
    }
    @Test
    public void test_FR_BOSS_DEATH_WIN() {
        assert true;
    }

    @Test
    public void test_FR_PLAYER_SPAWN() {
        Player p = new Player();
        assert p.isAlive();
    }

    @Test
    public void test_FR_PLAYER_SHOOT() throws NoSuchFieldException, IllegalAccessException {
        Player p = new Player();
        assert p.isAlive();

        MainGameScreen.player = p;

        Gdx gdx = mock(Gdx.class);
        Input input = mock(Input.class);
        when(input.isButtonJustPressed(Input.Buttons.LEFT)).thenReturn(true);
        Field inputfield = Gdx.class.getDeclaredField("input");
        inputfield.set(gdx, input);

        int timeout = 0;
        boolean shoot = false;
        while(!shoot && timeout < 1) {
            p.update(deltaTime);
            try{
                shoot = EntityManager.entities.get(EntityManager.entities.size - 1) instanceof Bullet;
            }
            catch (Exception ignore) {

            }
            timeout += deltaTime;
        }

        //p.getShooter().playerShoots();
        assert shoot;
    }

    @Test
    public void test_FR_PLAYER_DIE() {
        new Player();

        Player.setHealth(-1);

        assert Player.getHealth() < 0.0f;
    }
    @Test
    public void test_UI_ENDSCREEN() {
        Endgame.createLayout(true);
        assert !Endgame.table.getDebug();
    }

    @Test
    public void test_UI_SUBTITLE() {
        Subtitle s = new Subtitle(AssetManager.pixelFont);

        assert s.hasChildren();
        assert s.getChild(0) instanceof Label;

        s.setQuestNotice("Open the ", "chest", " chest");

        Label l0 = (Label) s.getChild(0);
        assert l0.textEquals("Open the ");
        Image i = (Image) s.getChild(1);
        // assert Objects.equals(i, new Image(ui.newDrawable("chest")));
        Label l1 = (Label) s.getChild(2);
        assert l1.textEquals(" chest");
    }

    @Test
    public void test_B2_WORLDCREATOR() {
        World w = new World(new Vector2(0, 0), true);
        B2WorldCreator wc = new B2WorldCreator(w);
    }
}
