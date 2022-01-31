package com.ducks.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.screens.MainGameScreen;
import com.ducks.sprites.Ship;

public class Tutorial {
    private OrthographicCamera gameCam;

    private final int PIXEL_ARROW_WIDTH = 212;
    private final int PIXEL_ARROW_HEIGHT = 128;

    private final float ARROW_WIDTH = PIXEL_ARROW_WIDTH * .2f;
    private final float ARROW_HEIGHT = PIXEL_ARROW_HEIGHT * .2f;

    private Animation <TextureRegion> arrow;

    private float stateTime;
    private float SPAWN_X;
    private float SPAWN_Y;
    private Ship player;
    private BitmapFont font;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    private GlyphLayout Layout;

    public Tutorial(OrthographicCamera gameCam,  Ship player) {
        this.gameCam = gameCam;
        this.player = player;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i=0; i<6; i++) {
            frames.add(new TextureRegion(MainGameScreen.resources.getTexture("arrow"), i * PIXEL_ARROW_WIDTH, 0, PIXEL_ARROW_WIDTH, PIXEL_ARROW_HEIGHT));
        }
        arrow = new Animation(0.1f, frames);
        frames.clear();

        SPAWN_X = player.b2body.getPosition().x - 2*player.b2body.getFixtureList().get(0).getShape().getRadius() - ARROW_WIDTH /2 / DeltaDucks.PIXEL_PER_METER;
        SPAWN_Y = player.b2body.getPosition().y - player.b2body.getFixtureList().get(0).getShape().getRadius()/2;

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/boy.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;
//        parameter.color = new Color(.2f, .2f, .2f, 1);
        font = generator.generateFont(parameter);
        font.getData().setScale(.6f);
        Layout = new GlyphLayout(font, "Start\nHere");
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(arrow.getKeyFrame(stateTime, true), SPAWN_X, SPAWN_Y, ARROW_WIDTH / DeltaDucks.PIXEL_PER_METER, ARROW_HEIGHT / DeltaDucks.PIXEL_PER_METER);

        Matrix4 originalMatrix = batch.getProjectionMatrix().cpy();
        batch.setProjectionMatrix(originalMatrix.cpy().scale( 1 / DeltaDucks.PIXEL_PER_METER, 1 / DeltaDucks.PIXEL_PER_METER, 1));
        font.draw(batch, Layout, SPAWN_X * DeltaDucks.PIXEL_PER_METER, SPAWN_Y * DeltaDucks.PIXEL_PER_METER);
        batch.setProjectionMatrix(originalMatrix);
    }
}
