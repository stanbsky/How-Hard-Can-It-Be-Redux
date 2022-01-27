package com.ducks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ducks.DeltaDucks;

public class FinalStorylineScreen implements Screen {
    DeltaDucks game;

    private OrthographicCamera gameCam;
    private Viewport gamePort;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    private BitmapFont font;
    private BitmapFont smallFont;

    private GlyphLayout escLayout;
    private GlyphLayout Layout;

    private int state;
    private float stateTimer;

    public FinalStorylineScreen(DeltaDucks game) {
        this.game = game;
    }

    @Override
    public void show() {
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(DeltaDucks.WIDTH, DeltaDucks.HEIGHT, gameCam);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/boy.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 10;
        smallFont = generator.generateFont(parameter);

        parameter.size = 25;
        font = generator.generateFont(parameter);

        state = 0;
        Layout = new GlyphLayout();
        escLayout = new GlyphLayout(smallFont, "Press Esc to Exit..");
    }

    public void handleInput(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }
    public void update(float deltaTime) {
        handleInput(deltaTime);
        stateTimer += deltaTime;
        if(stateTimer>=1){
            state++;
            stateTimer = 0;
        }
        switch (state){
            case 0:
                Layout.setText(font,"You're The King Of Yorkshire Now..");
                break;
            default:
                Layout.setText(font,"Thanks for Playing!");
                break;
        }
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(gameCam.combined);

        game.batch.begin();
        font.draw(game.batch, Layout, -Layout.width/2, -0);
        smallFont.draw(game.batch, escLayout, -escLayout.width/2, -gameCam.viewportHeight/3);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
