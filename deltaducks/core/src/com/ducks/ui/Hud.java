package com.ducks.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.ducks.managers.AssetManager;
import com.ducks.managers.PowerupManager;

import java.util.Objects;

import com.ducks.managers.StatsManager;

import static com.ducks.managers.AssetManager.*;

public class Hud extends Stage {


    public static Subtitle subtitle;
    private static Image expSymbol;
    private static Label expLabel;
    private static Label expTagLabel;
    private static Table expTable = new Table();
    private static Label countdownLabel;
    private static Image timeSymbol;
    private static Label timeLabel;
    private static Table timeTable = new Table();
    private static Image goldSymbol;
    private static Label goldTagLabel;
    private static Label goldLabel;
    private static Table goldTable = new Table();

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private static BitmapFont topBarFont;

    private static Table powerupList;

    private static Table shieldCell;
    private static Table multishotCell;
    private static Table hotshotCell;
    private static Table quickshotCell;
    private static Table supersizeCell;

    public Hud() {

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/futur.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;

        topBarFont = generator.generateFont(parameter);
        topBarFont.getData().setScale(.8f);

        // root
        Table root = new Table();
        addActor(root);
        root.setFillParent(true);
        root.setDebug(false);
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
        powerupList.setBackground(ui_background);
        //powerupList.setColor(1, 1, 1, 0.7f); // makes background transparent

        bottomUI.add(powerupList).width(120).bottom();
        // Subtitle button - ShopButton placeholder for layout purposes
//        ShopButton sub = new ShopButton("shield", font);
//        bottomUI.add(sub).expandX().bottom();

        subtitle = new Subtitle(plainFont);
        bottomUI.add(subtitle).expandX().bottom();

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

        topbar_background.setRightWidth(32);

        expTable.pack();
        expTable.setBackground(topbar_background);
        topBar.add(expTable).width(expTable.getWidth() + 32);

        goldTable.pack();
        goldTable.setBackground(topbar_background);
        topBar.add(goldTable).width(goldTable.getWidth() + 32);

        timeTable.pack();
        timeTable.setBackground(topbar_background);
        topBar.add(timeTable).width(timeTable.getWidth() + 32);
    }

    public static void drawPowerups() {
        powerupList.reset();
        if (PowerupManager.shieldActive()) {
            shieldCell = makePowerupUI("shield");
            powerupList.add(shieldCell).size(60);
            powerupList.row();
        }
        if (PowerupManager.multishotActive()) {
            multishotCell = makePowerupUI("spray");
            powerupList.add(multishotCell).size(60);
            powerupList.row();
        }
        if (PowerupManager.hotshotActive()) {
            hotshotCell = makePowerupUI("bullet_hotshot");
            powerupList.add(hotshotCell).size(60);
            powerupList.row();
        }
        if (PowerupManager.quickshotActive()) {
            quickshotCell = makePowerupUI("quickfire");
            powerupList.add(quickshotCell).size(60);
            powerupList.row();
        }
        if (PowerupManager.supersizeActive()) {
            supersizeCell = makePowerupUI("supersize");
            powerupList.add(supersizeCell).size(60);
        }
    }

    private static Table makePowerupUI(String powerup) {
        Table thisPowerupCell = new Table();

        if (!Objects.equals(powerup, "")) {
            thisPowerupCell.add(new Image(AssetManager.ui.newDrawable(powerup))).size(60);

            Label powerupLabel = new Label(PowerupManager.powerupLeft(powerup), new Label.LabelStyle(topBarFont, Color.BLACK));
            thisPowerupCell.add(powerupLabel);
        }

        return thisPowerupCell;
    }
    public void draw() {
        countdownLabel.setText(String.format("%d", StatsManager.getWorldTimer()));
        goldLabel.setText(String.format("%d", StatsManager.getGold()));
        expLabel.setText(String.format("%d", StatsManager.getScore()));

        drawPowerups();

        super.draw();
    }

}
