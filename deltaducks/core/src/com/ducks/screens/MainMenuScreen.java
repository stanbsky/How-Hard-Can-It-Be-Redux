package com.ducks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ducks.DeltaDucks;
import com.ducks.intangibles.DifficultyControl;
import com.ducks.managers.AssetManager;
import com.ducks.ui.MainMenu;
import com.ducks.managers.SaveManager;

/***
 * Main Menu Screen
 */
public class MainMenuScreen implements Screen {

    private DeltaDucks game;

    private OrthographicCamera gameCam;
    private Viewport gamePort;

    private static MainMenu mainMenu;

    private static String buttonPressed;

    /**
     * Constructor
     * @param game object of DeltaDucks
     */
    public MainMenuScreen(DeltaDucks game) {
        this.game = game;
        SaveManager.LoadSave = false;
        SaveManager.Initialize();
    }

    /**
     * Initialize once the screen is visible
     */
    @Override
    public void show() {
        AssetManager.Initialize();
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(DeltaDucks.WIDTH, DeltaDucks.HEIGHT, gameCam);
        mainMenu = new MainMenu();
        buttonPressed = "";
        Gdx.input.setInputProcessor(mainMenu);
    }

    /**
     * Handle any Input
     * @param deltaTime of the game
     */
    public void handleInput(float deltaTime) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            this.dispose();
            Gdx.app.exit();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            this.dispose();
//            game.setScreen(new InitialStorylineScreen(this.game));
            game.setScreen(new MainGameScreen(this.game));
        }

    }

    /**
     * Update the window every delta time interval
     * @param deltaTime of the game
     */
    public void update(float deltaTime) {
        handleInput(deltaTime);
    }

    /**
     * Render the window
     * @param delta time of the game
     */
    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mainMenu.act();
        mainMenu.draw();

        switch (buttonPressed) {
            case "easy":
                nextScreen(0);
                break;
            case "medium":
                nextScreen(1);
                break;
            case "hard":
                nextScreen(2);
                break;
            case "load":
                SaveManager.LoadSave();
                nextScreen(DifficultyControl.getDifficulty());
                break;
            case "exit":
                this.dispose();
                Gdx.app.exit();
                break;
        }
    }

    public static void setButtonPressed (String text) { buttonPressed = text; }

    /**
     * Sets the difficulty then loads the game into the screen
     * @param Difficulty for game
     */
    private void nextScreen (int Difficulty) {
        DifficultyControl.setDifficulty(Difficulty);
        this.dispose();
        game.setScreen(new MainGameScreen(this.game));
    }

    /**
     * Resize the window
     * @param width of window
     * @param height of window
     */
    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        mainMenu.getViewport().update(width, height);
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
