package de.tomgrill.gdxtesting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.ducks.entities.*;
import com.ducks.intangibles.*;
import com.ducks.managers.*;
import com.ducks.screens.*;
import com.ducks.tools.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;

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
        PhysicsManager.Initialize(world);
        AssetManager.Initialize();
        EntityManager.Initialize();
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
    public void test_FR_COMBAT() throws SecurityException,
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
    public void test_FR_ENTITY_SPAWNS() {
        // Load the map for pulling the spawn locations
//        TmxMapLoader mapLoader = new TmxMapLoader();
//        MainGameScreen.map = mapLoader.load("abi_map.tmx");
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

        // The whirlpool should be open after the player is in its range for 4 seconds
        assert !chest.isAlive();
    }

    @Test
    public void test_Pirate_Move() {
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
    public void test_FR_COLLEGE_DAMAGE(){
        //Ensure colleges' health goes down after a bullet hits them
        College college = new College(new Vector2(0,0),"constantine");
        int initial_health = college.health;
        PlayerBullet bullet = new PlayerBullet(zero, zero, zero);
        world.step(deltaTime, 6, 2);
        college.update(deltaTime);
        bullet.update(deltaTime);
        //The current health should be less than it started with
        assert initial_health > college.health;
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
    public void test_FR_BOSS_DAMAGE(){
        //Ensure boss's health goes down after a bullet hits it
        Boss boss = new Boss("constantine", zero);
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
    }
}
