package de.tomgrill.gdxtesting;

import com.badlogic.gdx.ApplicationListener;

import static org.mockito.Mockito.mock;

/**
 * Attempt to create a HeadlessApplication akin to GdxTestRunner
 */
public class DDHeadless implements ApplicationListener {
    public DDHeadless() {
        //super(klass);
//        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
//
//        new HeadlessApplication(this, conf);
//        Gdx.gl = mock(GL20.class);
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
