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

public class InitialStorylineScreen implements Screen {
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    DeltaDucks game;

    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    BitmapFont smallFont;

    BitmapFont font;
    GlyphLayout escLayout;
    GlyphLayout Layout;

    int state;

    public InitialStorylineScreen(DeltaDucks game) {
        this.game = game;
    }

    @Override
    public void show() {
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/boy.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 10;
        smallFont = generator.generateFont(parameter);

        parameter.size = 25;
        font = generator.generateFont(parameter);

        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(DeltaDucks.WIDTH, DeltaDucks.HEIGHT, gameCam);

        state = 0;
        Layout = new GlyphLayout(font, "There was once a Pirate \n known as The King Of Yorkshire...");
        escLayout = new GlyphLayout(smallFont, "Press Esc to Continue..");
    }

    public void handleInput(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            this.dispose();
            game.setScreen(new MainGameScreen(this.game));
        }
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            switch (++state){
                case 1:
                    Layout.setText(font, "And Continue...");
                    break;
                default:
                    Layout.setText(font, "The End...");
                    break;
            }
            System.out.println("YEh");

        }

    }

    public void update(float deltaTime) {
        handleInput(deltaTime);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(gameCam.combined);

        game.batch.begin();
//        font.getData().setScale(1f);
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
