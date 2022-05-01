package com.ducks.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.ducks.managers.AssetManager;
import com.ducks.managers.SaveManager;
import com.ducks.managers.StatsManager;

import static com.ducks.managers.AssetManager.*;
import static com.ducks.screens.MainGameScreen.*;

public class TablePauseMenu extends Stage {

    private static final int BUTTON_WIDTH = 250;
    private static final int BUTTON_HEIGHT = 100;
    public static Label info;

    private static Label goldLabel;

    public TablePauseMenu () {

        Table root = new Table();
        addActor(root);
        root.setFillParent(true);
        root.setBackground(AssetManager.ui.newDrawable("blank", new Color(1,1,1,0.5f)));
        Table buttons = new Table();
        buttons.defaults().pad(5);
        buttons.setDebug(false);
        Table shop = new Table().pad(5);
        root.add(buttons);
        root.row();
        root.add(shop);

        // Tint the "up" texture on mouse over, switch to "down" on click
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = AssetManager.ui.newDrawable("play_up");
        style.over = AssetManager.ui.newDrawable("play_up", Color.LIGHT_GRAY);
        style.down = AssetManager.ui.newDrawable("play_down");
        Button button = new Button(style);

        // Unpause and go back to game
        button.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                togglePause();
            }
        });

        buttons.add(button).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);

        style = new Button.ButtonStyle();
        style.up = AssetManager.ui.newDrawable("exit_up");
        style.over = AssetManager.ui.newDrawable("exit_up", Color.LIGHT_GRAY);
        style.down = AssetManager.ui.newDrawable("exit_down");
        button = new Button(style);

        // Add 10000 to gold
        button.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                SaveManager.Save();
            }
        });

        buttons.add(button).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);

        // Save button functionality needed
        style = new Button.ButtonStyle();
        style.up = AssetManager.ui.newDrawable("exit_up");
        style.over = AssetManager.ui.newDrawable("exit_up", Color.LIGHT_GRAY);
        style.down = AssetManager.ui.newDrawable("exit_down");
        button = new Button(style);

        // Add 10000 to gold
        button.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                StatsManager.addGold(10000);
            }
        });

        buttons.add(button).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);

        Table powerups = new Table();
        powerups.defaults().spaceBottom(5).minWidth(500);
        powerups.add(new ShopButton("shield", font));
        powerups.row();
        powerups.add(new ShopButton( "quickfire", font));
        powerups.row();
        powerups.add(new ShopButton("spray", font));
        powerups.row();
        powerups.add(new ShopButton("bullet_hotshot", font));
        powerups.row();
        powerups.add(new ShopButton( "supersize", font));
        shop.add(powerups);

        Table infoBox = new Table();
        infoBox.setBackground(ui_background);
        Table infoCoinBox = new Table();
        infoCoinBox.add(new Image(ui.newDrawable("coin2"))).size(64);
        goldLabel = new Label(String.format("%d", StatsManager.getGold()), new Label.LabelStyle(font, Color.BLACK));
        infoCoinBox.add(goldLabel);
        infoBox.add(infoCoinBox).right().expandX();
        infoBox.row();
        info = new Label("Mouse over an item on the left for more info", new Label.LabelStyle(font, Color.BLACK));
        info.setWrap(true);
        info.setAlignment(Align.center);
        infoBox.add(info).fillY().expandY().width(500);
        shop.add(infoBox).fillY().spaceLeft(5);
    }

    public static void updateGold () {
        goldLabel.setText(String.format("%d", StatsManager.getGold()));
    }

    public static void updateInfo (String newString) {
        info.setText(newString);
    }
}
