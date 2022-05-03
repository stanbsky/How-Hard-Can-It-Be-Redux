package com.ducks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ducks.DeltaDucks;
import com.ducks.managers.StatsManager;
import com.ducks.ui.Endgame;

/**
 * The end game screen
 */
public class EndgameScreen implements Screen {
    private DeltaDucks game;
    private OrthographicCamera gameCam;
    private FitViewport gamePort;
    private static Endgame endgame;
    private static String buttonPressed;

    /**
     * ints the ui
     * @param game the game
     * @param won true if won false otherwise
     */
    public EndgameScreen(DeltaDucks game, boolean won) {
        this.game = game;
        endgame = new Endgame(won);
    }

    /**
     * sets the camera and viewport data
     */
    @Override
    public void show() {
        Gdx.input.setCursorCatched(false);
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(DeltaDucks.WIDTH, DeltaDucks.HEIGHT, gameCam);
        buttonPressed = "";
        Gdx.input.setInputProcessor(endgame);
    }

    /**
     * Quits if Esc is pressed
     * @param deltaTime the time since the last frame
     */
    public void handleInput(float deltaTime) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            this.dispose();
            Gdx.app.exit();
        }
    }

    public void update(float deltaTime) {
        handleInput(deltaTime);
    }

    /**
     * clears the screen and draws the up also checks for button press
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        endgame.act();
        endgame.draw();

        switch (buttonPressed) {
            case "play":
                game.setScreen(new MainMenuScreen(game));
                break;
            case "exit":
                this.dispose();
                Gdx.app.exit();
                break;
        }
    }

    public static void setButtonPressed (String text) { buttonPressed = text; }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height, true);
        endgame.setViewport(gamePort);
//        endgame.getViewport().update(width, height);
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
