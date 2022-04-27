package de.tomgrill.gdxtesting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.ducks.entities.College;
import com.ducks.entities.Player;
import com.ducks.managers.AssetManager;
import com.ducks.managers.EntityManager;
import com.ducks.managers.PhysicsManager;
import com.ducks.tools.EntityContactListener;
import com.ducks.tools.InputParser;
import com.ducks.ui.Hud;
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
    private final float deltaTime = 1/60f;

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
        world = new World(new Vector2(0,0), true);
        contactListener = new EntityContactListener();
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
            inputParser.when(()->InputParser.parseInput()).thenReturn(directions);

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
            inputParser.when(()->InputParser.parseInput()).thenReturn(directions);

            // Run our world for 60 steps aka 1 second's worth of time at 60fps
            for (int i = 0; i < 60; i++) {
                world.step(deltaTime, 6, 2);
                player.update(deltaTime);
                // Print out our location to get a feel for if things are working out
                System.out.println(player.getPosition());
            }
            // The location of the player should be further north than it was at the start
            assert initial.x > player.getPosition().x;
            // The player's ship should have positive momentum in the y direction
            assert player.getVelocity().x < 0;
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
                System.out.println(player.getHealth());
            }
//        }
    }
}
