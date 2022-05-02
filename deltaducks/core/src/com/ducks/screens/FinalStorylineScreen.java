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
import com.ducks.managers.StatsManager;

/***
 * Final Story Line
 */
public class FinalStorylineScreen implements Screen {
    private DeltaDucks game;

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
    private String status;

    /**
     * Constructor
     * @param game object of DeltaDucks
     * @param status of Victory (or Defeat)
     */
    public FinalStorylineScreen(DeltaDucks game, String status) {
        this.game = game;
        this.status = status;
    }

    /**
     * Initialize once the screen is visible
     */
    @Override
    public void show() {
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(DeltaDucks.WIDTH, DeltaDucks.HEIGHT, gameCam);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/boy.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 20;
        smallFont = generator.generateFont(parameter);
        smallFont.getData().setScale(.5f);

        parameter.size = 25;
        font = generator.generateFont(parameter);

        state = 0;
        Layout = new GlyphLayout();
        escLayout = new GlyphLayout(smallFont, "Press Esc to Exit..");
    }

    /**
     * Handle any Input
     * @param deltaTime of the game
     */
    public void handleInput(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    /**
     * Update the window every delta time interval
     * @param deltaTime of the game
     */
    public void update(float deltaTime) {
        handleInput(deltaTime);
        stateTimer += deltaTime;
        if(stateTimer>=4){
            state++;
            stateTimer = 0;
        }
        switch (state){
            case 0:
                if(status=="Won")
                    Layout.setText(font,"You're The King Of Yorkshire Now..");
                else
                    Layout.setText(font,"I mean, you tried HAHAHA..");
                break;
            case 1:
                Layout.setText(font,"You gained " + StatsManager.getGold() + " gold and earned " + StatsManager.getScore()+" EXP!");
                break;
            default:
                Layout.setText(font,"Thanks for Playing!");
                break;
        }
    }

    /**
     *  the window
     * @param delta time of the game
     */
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

    /**
     * Resize the window
     * @param width of new window
     * @param height of new window
     */
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
