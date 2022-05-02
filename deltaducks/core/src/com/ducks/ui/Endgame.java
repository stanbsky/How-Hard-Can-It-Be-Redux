package com.ducks.ui;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ducks.DeltaDucks;
import com.ducks.managers.AssetManager;
import com.ducks.managers.StatsManager;
import com.ducks.screens.EndgameScreen;
import com.ducks.screens.MainMenuScreen;
import com.ducks.ui.MainMenu;
import com.ducks.ui.Subtitle;

import static com.ducks.managers.AssetManager.*;

public class Endgame extends Stage {

    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 130;

    public Endgame(boolean won) {
        Table table = new Table();
        addActor(table);
        table.setBackground(AssetManager.ui.newDrawable("blank", new Color(1,1,1,0.8f)));
        table.setFillParent(true);
        table.setDebug(false);
        table.defaults().pad(20);

        Subtitle title = new Subtitle(AssetManager.pixelFont);
        if (won) {
            title.setDoubleIconNotice(" You did it! ", "trophy");
        } else {
            title.setDoubleIconNotice(" Better luck next time! ", "supersize");
        }
        table.add(title);
        table.row();

        Table infoBox = new Table();
        infoBox.setDebug(false);
        Label.LabelStyle style = new Label.LabelStyle(pixelFont, Color.BLACK);
        infoBox.setBackground(ui_background);
        infoBox.defaults().right().pad(20);

        // Time played
        infoBox.add(new Label("You played for ", style)).expandX();
        infoBox.add(new Image(ui.newDrawable("time"))).size(64);
        infoBox.add((new Label(" " + (300 - StatsManager.getWorldTimer()) + " seconds", style)));
        infoBox.row();

        // Gold earned
        infoBox.add(new Label("You earned a total of ", style));
        infoBox.add(new Image(ui.newDrawable("coin2"))).size(64);
        infoBox.add((new Label(" " + StatsManager.getTotalEarnedGold() + " gold", style)));
        infoBox.row();

        // Score earned
        infoBox.add(new Label("You gained a total of ", style));
        infoBox.add(new Image(ui.newDrawable("trophy"))).size(64);
        infoBox.add((new Label(" " + StatsManager.getScore() + " points of score", style)));
        infoBox.row();

        infoBox.add(new Label("Try again?", style)).bottom().right().colspan(3);
        table.add(infoBox);
        table.row();

        Table bottomRow = new Table();
        bottomRow.defaults().width(BUTTON_WIDTH).height(BUTTON_HEIGHT).pad(15);

        PlainButton button = new PlainButton("PLAY", new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                EndgameScreen.setButtonPressed("play");
            }
        });
        bottomRow.add(button);

        button = new PlainButton("EXIT", new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                EndgameScreen.setButtonPressed("exit");
            }
        });
        bottomRow.add(button);
        table.add(bottomRow);
    }
}
