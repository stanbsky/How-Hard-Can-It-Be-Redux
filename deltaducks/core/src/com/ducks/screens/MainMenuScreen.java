package com.ducks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.ducks.DeltaDucks;

public class MainMenuScreen implements Screen {

    DeltaDucks game;

    private static final int PLAY_BUTTON_WIDTH = 300;
    private static final int PLAY_BUTTON_HEIGHT = 120;
    private static final int EXIT_BUTTON_WIDTH = 250;
    private static final int EXIT_BUTTON_HEIGHT = 120;

    private static final int PLAY_BUTTON_Y = 300;
    private static final int EXIT_BUTTON_Y = 170;

    Texture playButtonActive, playButtonInactive;
    Texture exitButtonActive, exitButtonInactive;

    int play_button_X;
    int exit_button_X;

    public MainMenuScreen(DeltaDucks game) {
        this.game = game;
    }

    @Override
    public void show() {
        playButtonActive = new Texture("main_menu/play_button_active.png");
        playButtonInactive = new Texture("main_menu/play_button_inactive.png");
        exitButtonActive = new Texture("main_menu/exit_button_active.png");
        exitButtonInactive = new Texture("main_menu/exit_button_inactive.png");

        play_button_X = DeltaDucks.WIDTH/2 - PLAY_BUTTON_WIDTH/2;
        exit_button_X = DeltaDucks.WIDTH/2 - EXIT_BUTTON_WIDTH/2;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.enableBlending();
        game.batch.begin();

        if ((Gdx.input.getX() < play_button_X + PLAY_BUTTON_WIDTH) && (Gdx.input.getX() > play_button_X) && (DeltaDucks.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT) && (DeltaDucks.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y)) {
            game.batch.draw(playButtonActive, play_button_X, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new MainGameScreen(this.game));
            }
        } else {
            game.batch.draw(playButtonInactive, play_button_X, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }
        if ((Gdx.input.getX() < exit_button_X + EXIT_BUTTON_WIDTH) && (Gdx.input.getX() > exit_button_X) && (DeltaDucks.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT) && (DeltaDucks.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y)) {
            game.batch.draw(exitButtonActive, exit_button_X, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(exitButtonInactive, exit_button_X, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

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
