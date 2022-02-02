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

/***
 * Initial Story Line
 */
public class InitialStorylineScreen implements Screen {
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

    /**
     * Constructor
     * @param game object of DeltaDucks
     */
    public InitialStorylineScreen(DeltaDucks game) {
        this.game = game;
    }

    /**
     * Initialize once the screen is visible
     */
    @Override
    public void show() {
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/boy.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        smallFont = generator.generateFont(parameter);
        smallFont.getData().setScale(.5f);

        parameter.size = 25;
        font = generator.generateFont(parameter);

        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(DeltaDucks.WIDTH, DeltaDucks.HEIGHT, gameCam);

        state = 0;
        Layout = new GlyphLayout(font, "You have to fight all the colleges\nto be the next King Of Yorkshire");
        escLayout = new GlyphLayout(smallFont, "Press Left Click to Continue or Esc to Skip..");
    }

    /**
     * Handle any Input
     * @param deltaTime of the game
     */
    public void handleInput(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            this.dispose();
            game.setScreen(new MainGameScreen(this.game));
        }
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            switch (++state){
                case 1:
                    Layout.setText(font, "Defeat them to be victorious\nYou have got 5 minutes");
                    break;
                case 2:
                    Layout.setText(font, "Dodge the attacks\nand attack the attackers...");
                    break;
                default:
                    this.dispose();
                    game.setScreen(new MainGameScreen(this.game));
                    break;
            }
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
