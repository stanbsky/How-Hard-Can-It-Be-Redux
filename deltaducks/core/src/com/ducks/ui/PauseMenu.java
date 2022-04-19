package com.ducks.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ducks.DeltaDucks;
import com.ducks.screens.InitialStorylineScreen;
import com.ducks.screens.MainMenuScreen;


public class PauseMenu {

    private final ShapeRenderer shapeRenderer;

    private OrthographicCamera gameCam;
    private Viewport gamePort;

    private static final int PLAY_BUTTON_WIDTH = 300;
    private static final int PLAY_BUTTON_HEIGHT = 120;
    private static final int EXIT_BUTTON_WIDTH = 250;
    private static final int EXIT_BUTTON_HEIGHT = 120;

    private static final float PLAY_BUTTON_Y = (DeltaDucks.HEIGHT/2f)+PLAY_BUTTON_HEIGHT/4f;
    private static final float EXIT_BUTTON_Y = (DeltaDucks.HEIGHT/2f)-EXIT_BUTTON_HEIGHT;

    private static final float PLAY_BUTTON_X = (DeltaDucks.WIDTH/2f)-PLAY_BUTTON_WIDTH/2f;
    private static final float EXIT_BUTTON_X = (DeltaDucks.WIDTH/2f)-EXIT_BUTTON_WIDTH/2f;

    public Stage stage;
    public Viewport viewport;

    private final Texture playButtonActive, playButtonInactive;
    private final Texture exitButtonActive, exitButtonInactive;

    public PauseMenu() {
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(DeltaDucks.WIDTH, DeltaDucks.HEIGHT, gameCam);

        playButtonActive = new Texture("main_menu/play_button_active.png");
        playButtonInactive = new Texture("main_menu/play_button_inactive.png");
        exitButtonActive = new Texture("main_menu/exit_button_active.png");
        exitButtonInactive = new Texture("main_menu/exit_button_inactive.png");
    }

    public void draw(SpriteBatch batch) {
        // Dark Grey background for pause menu
//        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 0);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport = new FitViewport(DeltaDucks.WIDTH, DeltaDucks.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        batch.setProjectionMatrix(stage.getCamera().combined);
        stage.draw();
        batch.begin();

        Vector3 loc = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        if ((loc.x < PLAY_BUTTON_X + PLAY_BUTTON_WIDTH) && (loc.x > PLAY_BUTTON_X) && (loc.y < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT) && (loc.y > PLAY_BUTTON_Y)) {
            batch.draw(playButtonActive, PLAY_BUTTON_X, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);

        } else {
            batch.draw(playButtonInactive, PLAY_BUTTON_X, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }

        if ((loc.x < EXIT_BUTTON_X + EXIT_BUTTON_WIDTH) && (loc.x > EXIT_BUTTON_X) && (loc.y < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT) && (loc.y > EXIT_BUTTON_Y)) {
            batch.draw(exitButtonActive, EXIT_BUTTON_X, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        } else {
            batch.draw(exitButtonInactive, EXIT_BUTTON_X, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }
        batch.end();
    }

    public void draw() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(25, 25, 25, 155));
        shapeRenderer.rect(100, 100, DeltaDucks.VIRTUAL_WIDTH, DeltaDucks.VIRTUAL_HEIGHT);
        shapeRenderer.end();
    }
}
