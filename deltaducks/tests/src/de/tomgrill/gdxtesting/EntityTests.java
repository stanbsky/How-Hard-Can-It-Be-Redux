package de.tomgrill.gdxtesting;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.ducks.components.Texture;
import com.ducks.entities.PhysicsManager;
import com.ducks.sprites.Bullet;
import com.ducks.sprites.PlayerBullet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


public class EntityTests {
    private static DDHeadless game;
    private static HeadlessApplication app;

    @Mock Texture textureMock;
    @InjectMocks Bullet bullet;

    @BeforeAll
    public static void setup() {
        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
        conf.updatesPerSecond = 60;
        game = new DDHeadless();
        app = new HeadlessApplication(game, conf);
    }
    @Test
    public void testCreateBullet() {
        //MainGameScreen = mock(MainGameScreen.class);
        World world = new World(new Vector2(0,0), true);
        PhysicsManager.Initialize(world);


        // Static mocks don't work without mockito-inline magic!
        //try (MockedStatic<MainGameScreen> mockedScreen = Mockito.mockStatic(MainGameScreen.class)) {
        //}
            Vector2 zero = new Vector2(0, 0);
            bullet = new PlayerBullet(zero, zero, zero);
            assert true;

    }
}
