package com.ducks.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.ducks.DeltaDucks;
import com.ducks.entities.Player;

import static com.ducks.DeltaDucks.batch;
import static com.ducks.managers.AssetManager.*;

public class TableHud extends Stage {


    private static Image expSymbol;
    private static Label expLabel;
    private static Label expTagLabel;
    private Table expTable = new Table();
    private static Label countdownLabel;
    private static Image timeSymbol;
    private static Label timeLabel;
    private Table timeTable = new Table();
    private static Image goldSymbol;
    private static Label goldTagLabel;
    private static Label goldLabel;
    private Table goldTable = new Table();

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont topBarFont;

    public TableHud() {

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/futur.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;

        topBarFont = generator.generateFont(parameter);
        topBarFont.getData().setScale(.5f);

        // root
        Table root = new Table();
        addActor(root);
        root.setFillParent(true);
        root.setDebug(true);
        root.top();

        // topBar
        Table topBar = new Table();
        topBar.setDebug(true);
        topBar.defaults().pad(10).expandX().fillX();
        root.add(topBar).expandX().fillX();

        createTopBarUI();
        topBar.add(expTable);
        topBar.add(goldTable);
        topBar.add(timeTable);

        root.row();

        // bottomUI
        Table bottomUI = new Table();
        bottomUI.bottom();
        bottomUI.setDebug(true);
        bottomUI.defaults().pad(10);
        root.add(bottomUI).expand().fill();

        // HP bar
        HealthBar hpBar = new HealthBar();
        bottomUI.add(hpBar).height(300).width(50);

        // TODO: add powerup info box here

        // Subtitle button - ShopButton placeholder for layout purposes
        ShopButton sub = new ShopButton("shield", font);
        bottomUI.add(sub).expandX().bottom();

    }

    private void createTopBarUI() {
        expSymbol = new Image(ui.newDrawable("trophy"));
        expTagLabel = new Label("USER EXP", new Label.LabelStyle(topBarFont, Color.WHITE));
        expLabel = new Label(String.format("%d", 0), new Label.LabelStyle(topBarFont, Color.WHITE));

        goldSymbol = new Image(ui.newDrawable("coin2"));
        goldTagLabel = new Label("GOLD", new Label.LabelStyle(topBarFont, Color.WHITE));
        goldLabel = new Label(String.format("%d", 0), new Label.LabelStyle(topBarFont, Color.WHITE));

        timeSymbol = new Image(ui.newDrawable("time"));
        timeLabel = new Label("TIME", new Label.LabelStyle(topBarFont, Color.WHITE));
        countdownLabel = new Label(String.format("%d", 0), new Label.LabelStyle(topBarFont, Color.WHITE));

        Table expTableS = new Table();
        expTableS.add(expTagLabel).colspan(2);
        expTableS.row();
        expTableS.add(expLabel);

        Table goldTableS = new Table();
        goldTableS.add(goldTagLabel).colspan(2);
        goldTableS.row();
        goldTableS.add(goldLabel);

        Table timeTableS = new Table();
        timeTableS.add(timeLabel).colspan(2);
        timeTableS.row();
        timeTableS.add(countdownLabel);

        expTable.add(expSymbol);
        expTable.add(expTableS);

        goldTable.add(goldSymbol);
        goldTable.add(goldTableS);

        timeTable.add(timeSymbol);
        timeTable.add(timeTableS);
    }

}
