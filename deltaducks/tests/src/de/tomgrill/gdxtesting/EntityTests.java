package de.tomgrill.gdxtesting;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.ducks.managers.BulletManager;
import com.ducks.managers.PhysicsManager;
import com.ducks.entities.Bullet;
import com.ducks.entities.College;
import com.ducks.entities.PlayerBullet;
import com.ducks.screens.MainGameScreen;
import jdk.tools.jmod.Main;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EntityTests {
//    private static DDHeadless game;
//    private static HeadlessApplication app;
//
//    @Mock
//    TextureAtlas atlas;
//    @Mock
//    BulletManager cannons;
//    @Mock Contact contact;
//
//    private MyContactListener contactListener;
//    private World world;
//    private final Vector2 zero = new Vector2(0, 0);
//
//    @BeforeAll
//    public static void setupApp() {
//        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
//        conf.updatesPerSecond = 60;
//        game = new DDHeadless();
//        app = new HeadlessApplication(game, conf);
//    }
//    @BeforeEach
//    public void setupWorld() {
//        world = new World(new Vector2(0,0), true);
//        contactListener = new MyContactListener(null, null);
//        PhysicsManager.Initialize(world);
//    }
//    @Test
//    public void testCreateBullet() {
//        //TODO: test that getVelocity().len() is constant for all directions to within eg. 0.1f
//        Bullet bullet = new PlayerBullet(zero, new Vector2(1, 0), zero, atlas);
//        System.out.println(bullet.getBody().isAwake());
//        System.out.println(bullet.getVelocity());
//        for (int i = 0; i < 15; i++) {
//            world.step(1f, 6, 2);
//            bullet.update(1f);
//            System.out.println(bullet.getVelocity().len());
//        }
//
//        assert true;
//    }
//    @Test
//    public void testCollegeHit() {
//        College college = new College(500, 0, "test", cannons, atlas);
//        Bullet bullet;
//        float health;
//
//        // Forwards
//        bullet = new PlayerBullet(zero, zero, zero, atlas);
//        when(contact.getFixtureA()).thenReturn(college.getBody().getFixtureList().first());
//        when(contact.getFixtureB()).thenReturn(bullet.getBody().getFixtureList().first());
//        health = college.health;
//        contactListener.beginContact(contact);
//        assert bullet.getData().equals("Bullet Dead");
//        college.update(1f);
//        assert college.health < health;
//
//        // Forwards
//        bullet = new PlayerBullet(zero, zero, zero, atlas);
//        when(contact.getFixtureB()).thenReturn(college.getBody().getFixtureList().first());
//        when(contact.getFixtureA()).thenReturn(bullet.getBody().getFixtureList().first());
//        health = college.health;
//        contactListener.beginContact(contact);
//        assert bullet.getData().equals("Bullet Dead");
//        college.update(1f);
//        assert college.health < health;
//
//        // Destroy
//        for (int i = 0; i < 3; i++) {
//            bullet = new PlayerBullet(zero, zero, zero, atlas);
//            when(contact.getFixtureA()).thenReturn(bullet.getBody().getFixtureList().first());
//            contactListener.beginContact(contact);
//            college.update(1f);
//        }
//        assert college.health <= 0.001f;
//        college.update(1f);
//        assert college.health == -100f;
//
//    }
//    @Test
//    public void testBulletTimeout() {
//        Bullet bullet = new PlayerBullet(zero, zero, zero, atlas);
//        bullet.update(2.5f);
//        assert bullet.getData().equals("Bullet Dead");
//    }
    @Test
    public void testStatic() {
        try (MockedStatic mocked = mockStatic(MainGameScreen.class)) {
            mocked.when(MainGameScreen::getPlayerPosition).thenReturn(new Vector2(1,1));
            assert MainGameScreen.getPlayerPosition().equals(new Vector2(1, 1));
            mocked.verify(MainGameScreen::getPlayerPosition);
        }
    }
    @Test
    public void testVectorShit() {
        Vector2 a = new Vector2(34.253334f,61.329357f);
        Vector2 b = new Vector2(37.115204f,58.9929f);
        Vector2 sub = a.sub(b.x, b.y);
        Vector2 innerSub = new Vector2(a.x - b.x, a.y - b.y);
        assert a.sub(b).epsilonEquals(new Vector2(a.x - b.x, a.y - b.y));
    }
}
