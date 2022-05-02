package de.tomgrill.gdxtesting;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.ducks.managers.AssetManager;
import com.ducks.managers.PhysicsManager;
import com.ducks.tools.EntityContactListener;

import static org.mockito.Mockito.mock;

/**
 * Attempt to create a HeadlessApplication akin to GdxTestRunner
 */
public class DDHeadless implements ApplicationListener {
    public DDHeadless() {
    }

    @Override
    public void render() {
    }
    @Override
    public void create() {
    }

    @Override
    public void resume() {
    }
    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void dispose() {
    }
}
