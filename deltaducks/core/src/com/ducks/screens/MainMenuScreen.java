package com.ducks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ducks.DeltaDucks;
import com.ducks.intangibles.DifficultyControl;
import com.ducks.ui.MainMenu;

/***
 * Main Menu Screen
 */
public class MainMenuScreen implements Screen {

    private DeltaDucks game;

    private OrthographicCamera gameCam;
    private Viewport gamePort;

    private static final int PLAY_BUTTON_WIDTH = 300;
    private static final int PLAY_BUTTON_HEIGHT = 120;
    private static final int EXIT_BUTTON_WIDTH = 250;
    private static final int EXIT_BUTTON_HEIGHT = 120;

    private static final float PLAY_BUTTON_Y = 0+PLAY_BUTTON_HEIGHT/4f;
    private static final float EXIT_BUTTON_Y = 0-EXIT_BUTTON_HEIGHT;

    private static final float PLAY_BUTTON_X = 0-PLAY_BUTTON_WIDTH/2;
    private static final float EXIT_BUTTON_X = 0-EXIT_BUTTON_WIDTH/2;

    public static Texture playButtonActive, playButtonInactive;
    public static Texture exitButtonActive, exitButtonInactive;
    public static Texture easyButtonActive, easyButtonInactive;
    public static Texture mediumButtonActive, mediumButtonInactive;
    public static Texture hardButtonActive, hardButtonInactive;
    public static Texture loadButtonActive, loadButtonInactive;

    private Stage stage = new Stage();

    private static MainMenu mainMenu;

    private static String buttonPressed;

    /**
     * Constructor
     * @param game object of DeltaDucks
     */
    public MainMenuScreen(DeltaDucks game) {
        this.game = game;
    }

    /**
     * Initialize once the screen is visible
     */
    @Override
    public void show() {
        playButtonActive = new Texture("main_menu/play_button_active.png");
        playButtonInactive = new Texture("main_menu/play_button_inactive.png");
        exitButtonActive = new Texture("main_menu/exit_button_active.png");
        exitButtonInactive = new Texture("main_menu/exit_button_inactive.png");
        easyButtonActive = new Texture("main_menu/easy_button_active.png");
        easyButtonInactive = new Texture("main_menu/easy_button_inactive.png");
        mediumButtonActive = new Texture("main_menu/medium_button_active.png");
        mediumButtonInactive = new Texture("main_menu/medium_button_inactive.png");
        hardButtonActive = new Texture("main_menu/hard_button_active.png");
        hardButtonInactive = new Texture("main_menu/hard_button_inactive.png");
        loadButtonActive = new Texture("main_menu/load_button_active.png");
        loadButtonInactive = new Texture("main_menu/load_button_inactive.png");


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
            game.setScreen(new InitialStorylineScreen(this.game));
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


        /*
        Vector3 loc = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        gameCam.unproject(loc, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            this.dispose();
            game.setScreen(new InitialStorylineScreen(this.game));
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            this.dispose();
            Gdx.app.exit();
        }
        if ((loc.x < PLAY_BUTTON_X + PLAY_BUTTON_WIDTH) && (loc.x > PLAY_BUTTON_X) && (loc.y < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT) && (loc.y > PLAY_BUTTON_Y)) {
            game.batch.draw(playButtonActive, PLAY_BUTTON_X, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                this.dispose();
                game.setScreen(new InitialStorylineScreen(this.game));
            }
        } else {
            game.batch.draw(playButtonInactive, PLAY_BUTTON_X, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }

        if ((loc.x < EXIT_BUTTON_X + EXIT_BUTTON_WIDTH) && (loc.x > EXIT_BUTTON_X) && (loc.y < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT) && (loc.y > EXIT_BUTTON_Y)) {
            game.batch.draw(exitButtonActive, EXIT_BUTTON_X, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
            if (Gdx.input.isTouched()) {
                this.dispose();
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(exitButtonInactive, EXIT_BUTTON_X, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }
        */
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
            case "exit":
                this.dispose();
                Gdx.app.exit();
                break;
        }
    }

    public static void setButtonPressed (String text) { buttonPressed = text; }

    private void nextScreen (int Difficulty) {
        DifficultyControl.setDifficulty(Difficulty);
        this.dispose();
        game.setScreen(new InitialStorylineScreen(this.game));
    }

    /**
     * Resize the window
     * @param width
     * @param height
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
