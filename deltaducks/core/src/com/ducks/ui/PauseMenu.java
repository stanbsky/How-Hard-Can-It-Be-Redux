package com.ducks.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.ducks.managers.AssetManager;
import com.ducks.managers.SaveManager;
import com.ducks.managers.StatsManager;
import com.ducks.screens.MainGameScreen;
import com.ducks.screens.MainMenuScreen;

import static com.ducks.managers.AssetManager.*;
import static com.ducks.screens.MainGameScreen.*;

public class PauseMenu extends Stage {

    private static final int BUTTON_WIDTH = 250;
    private static final int BUTTON_HEIGHT = 100;
    public static Label info;
    public static PlainButton saveButton;

    private static Label goldLabel;

    /**
     * Sets up table representing the pause menu
     */
    public PauseMenu() {

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

        // Unpause and go back to game
        PlainButton button = new PlainButton("Play",
                new ClickListener() {
                    @Override
                    public void clicked (InputEvent event, float x, float y) {
                        togglePause();
                    }
                });

        buttons.add(button).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);

        // Save the game
        saveButton = new PlainButton("Save", new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                SaveManager.Save();
                PauseMenu.saveButton.setText("Saved");
            }
        });

        buttons.add(saveButton).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);

        // Save button functionality needed
        button = new PlainButton("Quit",
                new ClickListener() {
                    @Override
                    public void clicked (InputEvent event, float x, float y) {
                        quitToMenu = true;
                    }
                });
        buttons.add(button).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);

        Table powerups = new Table();
        powerups.defaults().spaceBottom(5).minWidth(500);
        powerups.add(new ShopButton("shield", plainFont));
        powerups.row();
        powerups.add(new ShopButton( "quickfire", plainFont));
        powerups.row();
        powerups.add(new ShopButton("spray", plainFont));
        powerups.row();
        powerups.add(new ShopButton("bullet_hotshot", plainFont));
        powerups.row();
        powerups.add(new ShopButton( "supersize", plainFont));
        shop.add(powerups);

        Table infoBox = new Table();
        infoBox.setBackground(ui_background);
        Table infoCoinBox = new Table();
        infoCoinBox.add(new Image(ui.newDrawable("coin2"))).size(64);
        goldLabel = new Label(String.format("%d", StatsManager.getGold()), new Label.LabelStyle(plainFont, Color.BLACK));
        infoCoinBox.add(goldLabel);
        infoBox.add(infoCoinBox).right().expandX().padRight(10);
        infoBox.row();
        info = new Label("Mouse over an item on the left for more info", new Label.LabelStyle(plainFont, Color.BLACK));
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
