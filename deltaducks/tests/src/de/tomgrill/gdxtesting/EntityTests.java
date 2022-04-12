package de.tomgrill.gdxtesting;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.ducks.entities.ListOfCannons;
import com.ducks.entities.PhysicsManager;
import com.ducks.sprites.Bullet;
import com.ducks.sprites.College;
import com.ducks.sprites.PlayerBullet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EntityTests {
    private static DDHeadless game;
    private static HeadlessApplication app;

    @Mock
    TextureAtlas atlas;
    @Mock
    ListOfCannons cannons;

    @BeforeAll
    public static void setup() {
        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
        conf.updatesPerSecond = 60;
        game = new DDHeadless();
        app = new HeadlessApplication(game, conf);
    }
    @Test
    public void testCreateBullet() {
        World world = new World(new Vector2(0,0), true);
        PhysicsManager.Initialize(world);

        Vector2 zero = new Vector2(0, 0);
        College college = new College(500, 0, "test", cannons, atlas);
        System.out.println(college.health);
        Bullet bullet = new PlayerBullet(zero, new Vector2(1, 0), zero, atlas);
        System.out.println(bullet.getPosition());
        System.out.println(college.health);
        System.out.println(bullet.getPosition());
        assert true;
    }
}
