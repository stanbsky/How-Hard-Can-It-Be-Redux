package de.tomgrill.gdxtesting;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.ducks.intangibles.Quest;
import com.ducks.managers.PhysicsManager;
import com.ducks.tools.EntityContactListener;
import com.ducks.ui.Subtitle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class QuestTests {
    private static DDHeadless game;
    private static HeadlessApplication app;
    private World world;
    private final Vector2 zero = new Vector2(0, 0);
    private EntityContactListener contactListener;
    @Mock
    TextureAtlas atlas;
    @Mock
    BulletManager cannons;
    @Mock
    Contact contact;
    @Mock Subtitle sub;

    @BeforeAll
    public static void setupApp() {
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
    }
    @Test
    public void testCreateQuest() {
//        when(sub.)
        Quest quest = new Quest("chest", zero, sub, atlas);
    }
}
