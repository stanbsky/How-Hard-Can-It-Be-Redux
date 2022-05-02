package de.tomgrill.gdxtesting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.ducks.managers.AssetManager;
import com.ducks.managers.EntityManager;
import com.ducks.managers.PhysicsManager;
import com.ducks.tools.EntityContactListener;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class TestTemplate {

    private static DDHeadless game;
    private static HeadlessApplication app;
    private World world;
    private EntityContactListener contactListener;
    private final Vector2 zero = new Vector2(0, 0);
    private final float deltaTime = 1/60f;
    private final String collegeName = "constantine";

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
        world.setContactListener(contactListener);
        PhysicsManager.Initialize(world);
        AssetManager.Initialize();
        EntityManager.Initialize();
    }
}
