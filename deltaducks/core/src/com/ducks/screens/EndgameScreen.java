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

public class EndgameScreen implements Screen {
    private DeltaDucks game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private static Endgame endgame;
    private static String buttonPressed;

    public EndgameScreen(DeltaDucks game, boolean won) {
        this.game = game;
        endgame = new Endgame(won);
    }

    @Override
    public void show() {
        Gdx.input.setCursorCatched(false);
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(DeltaDucks.WIDTH, DeltaDucks.HEIGHT, gameCam);
        buttonPressed = "";
        Gdx.input.setInputProcessor(endgame);
    }

    public void handleInput(float deltaTime) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            this.dispose();
            Gdx.app.exit();
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

        endgame.act();
        endgame.draw();

        switch (buttonPressed) {
            case "play":
                StatsManager.reset();
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
        gamePort.update(width, height);
        endgame.getViewport().update(width, height);
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
