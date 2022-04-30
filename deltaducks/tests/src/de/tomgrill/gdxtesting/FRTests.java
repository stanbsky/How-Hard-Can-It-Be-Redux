package de.tomgrill.gdxtesting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.ducks.components.*;
import com.ducks.entities.*;
import com.ducks.intangibles.*;
import com.ducks.managers.*;
import com.ducks.screens.*;
import com.ducks.tools.*;
import com.ducks.ui.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class FRTests {

    private static DDHeadless game;
    private static HeadlessApplication app;
    private World world;
    private EntityContactListener contactListener;
    private final Vector2 zero = new Vector2(0, 0);
    private final float deltaTime = 1 / 60f;

    @BeforeAll
    public static void setupApp() {
        Gdx.gl = mock(GL20.class);
        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
        conf.updatesPerSecond = 60;
        game = new DDHeadless();
        app = new HeadlessApplication(game, conf);
    }

    @BeforeEach
    public void setupWorld() {
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
                // Print out our location to get a feel for if things are working out
                System.out.println(player.getPosition());
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
                // Print out our location to get a feel for if things are working out
                System.out.println(player.getPosition());
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
                // Print out our location to get a feel for if things are working out
                System.out.println(player.getPosition());
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
                // Print out our location to get a feel for if things are working out
                System.out.println(player.getPosition());
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
    public void test_FR_COMBAT() {
        Player player = new Player();
        Vector2 playerLocation = player.getPosition().cpy();
        playerLocation.x += 1;
        College college = new College(playerLocation, "constantine");
//        try (MockedStatic<EntityManager> entityManager = Mockito.mockStatic(EntityManager.class)) {
//            String foo = "";
//            Array<Vector2> spawns = new Array<>();
            for (int i = 0; i < 600; i++) {
                world.step(deltaTime, 6, 2);
                player.update(deltaTime);
                college.update(deltaTime);
                // Print out our location to get a feel for if things are working out
                System.out.println(Player.getHealth());
            }
//        }
    }

    @Test
    public void test_FR_ENTITY_SPAWNS() {
        // Load the map for pulling the spawn locations
        TmxMapLoader mapLoader = new TmxMapLoader();
        MainGameScreen.map = mapLoader.load("abi_map.tmx");
        EntityManager.spawnEntities();

        // There should be at least one powerup spawned at the start of the game
        assert EntityManager.powerups.size >= 1;

        // There should be at least one pirate spawned at the start of the game
        assert EntityManager.pirates.size >= 1;

        // There should be 3 colleges spawned at the start of the game
        assert EntityManager.colleges.size == 3;

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

        try (MockedStatic<EntityManager> entityManager = Mockito.mockStatic(EntityManager.class)) {
            whirlpool.dispose();
        }

    }

    @Test
    public void test_FR_WHIRLPOOL_EFFECT() {
        Player player = new Player();
        Vector2 playerLocation = player.getPosition().cpy();

        Whirlpool whirlpool = new Whirlpool(player.getPosition().cpy().add(new Vector2(5, 0)));

        world.step(deltaTime, 6,2);
        whirlpool.update(deltaTime);
        player.update(deltaTime);
        world.step(deltaTime, 6,2);

        // The player should be moved by the whirlpool's proximity
        assert !playerLocation.equals(player.getPosition().cpy()) ;
    }

    @Test
    public void test_Pirate_Move() {
        // Create a pirate and record their initial position
        Pirate pirate = new Pirate();
        Vector2 initial = pirate.getPosition().cpy();


        // Mock the InputParser component to allow us to fake keyboard input
        try (MockedStatic<InputParser> inputParser = Mockito.mockStatic(InputParser.class)) {
            ArrayList<InputParser.Direction> directions = new ArrayList<>();

            // Set up the mock to always return northern direction
            directions.add(InputParser.Direction.WEST);
            inputParser.when(InputParser::parseInput).thenReturn(directions);

            // Run our world for 60 steps aka 1 second's worth of time at 60fps
            for (int i = 0; i < 60; i++) {
                world.step(deltaTime, 6, 2);
                pirate.update(deltaTime);
                // Print out our location to get a feel for if things are working out
                System.out.println(pirate.getPosition());
            }




            // The location of the player should be further west than it was at the start
            assert initial.x > pirate.getPosition().x;
            // The player's ship should have positive momentum in the x direction
            assert pirate.getVelocity().x < 0;
            // The player's ship should NOT have any momentum in other directions!
            assert pirate.getVelocity().y == 0;
        }

    }

    @Test
    public void test_college_Damage(){
        College college = new College(new Vector2(0,0),"constantine");
        int initial_health = college.health;
        PlayerBullet bullet = new PlayerBullet(zero, zero, zero);
        world.step(deltaTime, 6, 2);
        college.update(deltaTime);
        bullet.update(deltaTime);
        assert initial_health > college.health;
    }



}
