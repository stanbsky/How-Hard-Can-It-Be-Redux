package com.ducks.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ducks.DeltaDucks;
import com.ducks.screens.MainGameScreen;

public class Subtitle implements Disposable {
    public Stage stage;
    public Viewport viewport;

    private Label timeLabel;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;


    public Subtitle(SpriteBatch batch) {

        viewport = new FitViewport(DeltaDucks.VIRTUAL_WIDTH, DeltaDucks.VIRTUAL_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/boy.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 15;
        timeLabel = new Label("TIME", new Label.LabelStyle(generator.generateFont(parameter), Color.BLACK));


        table.add(timeLabel).expandX().padTop(DeltaDucks.VIRTUAL_HEIGHT * .9f);
//        table.row();
//        table.add(timeLabel).expandX();

        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
