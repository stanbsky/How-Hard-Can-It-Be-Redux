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
import com.ducks.managers.AssetManager;
import com.ducks.managers.PowerupManager;

import java.util.Objects;

import static com.ducks.DeltaDucks.batch;
import static com.ducks.managers.AssetManager.*;

public class TableHud extends Stage {


    private static Image expSymbol;
    private static Label expLabel;
    private static Label expTagLabel;
    private static Label countdownLabel;
    private static Image timeSymbol;
    private static Label timeLabel;
    private static Image goldSymbol;
    private static Label goldTagLabel;
    private static Label goldLabel;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont topBarFont;

    private Table powerupList;
    private Table[] powerupCells;

    public TableHud() {

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/futur.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;

        topBarFont = generator.generateFont(parameter);
        topBarFont.getData().setScale(.8f);

        // root
        Table root = new Table();
        addActor(root);
        root.setFillParent(true);
        root.setDebug(true);
        root.top();

        // topBar
        Table topBar = new Table();
        topBar.setDebug(false);
        topBar.defaults().pad(10).expandX().height(70);
//        topBar.setFillParent(true);
        root.add(topBar).expandX().fillX();

        createTopBarUI(topBar);

        root.row();

        // bottomUI
        Table bottomUI = new Table();
        bottomUI.bottom().left();
        bottomUI.setDebug(true);
        bottomUI.defaults().pad(10);
        root.add(bottomUI).expand().fill();

        // HP bar
        HealthBar hpBar = new HealthBar();
        bottomUI.add(hpBar).height(300).width(50);

        // TODO: add powerup info box here
        powerupList = new Table();
        powerupCells = new Table[]{new Table(), new Table(), new Table(), new Table(), new Table()};
        powerupList.bottom();
        for (Table powerupCell : powerupCells) {
            powerupCell = makePowerupUI("shield");
            powerupList.add(powerupCell).size(60);
            powerupList.row();
        }


        bottomUI.add(powerupList);
        update();
        // Subtitle button - ShopButton placeholder for layout purposes
//        ShopButton sub = new ShopButton("shield", font);
//        bottomUI.add(sub).expandX().bottom();

    }

    public void update () {
        for (int i = 0; i < 5; i++) {
            powerupCells[i].reset();
        }
    }

    private void createTopBarUI(Table topBar) {
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
        expTableS.add(expTagLabel);
        expTableS.row();
        expTableS.add(expLabel);

        Table goldTableS = new Table();
        goldTableS.add(goldTagLabel);
        goldTableS.row();
        goldTableS.add(goldLabel);

        Table timeTableS = new Table();
        timeTableS.add(timeLabel);
        timeTableS.row();
        timeTableS.add(countdownLabel);

        Table expTable = new Table();
        expTable.add(expSymbol).size(64);
        expTable.add(expTableS);

        Table goldTable = new Table();
        goldTable.add(goldSymbol).size(64);
        goldTable.add(goldTableS);

        Table timeTable = new Table();
        timeTable.add(timeSymbol).size(64);
        timeTable.add(timeTableS);

        topBar.add(expTable);
        topBar.add(goldTable);
        topBar.add(timeTable);
    }

    private Table makePowerupUI(String powerup) {
        Table thisPowerupCell = new Table();

        if (!Objects.equals(powerup, "")) {
            thisPowerupCell.add(new Image(AssetManager.ui.newDrawable(powerup))).size(60);

            Label powerupLabel = new Label(PowerupManager.powerupLeft(powerup), new Label.LabelStyle(topBarFont, Color.BLACK));
            thisPowerupCell.add(powerupLabel);
        }

        return thisPowerupCell;
    }
}
