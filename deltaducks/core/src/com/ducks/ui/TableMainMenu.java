package com.ducks.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import com.ducks.managers.AssetManager;
import com.ducks.screens.MainMenuScreen;

public class TableMainMenu extends Stage {

    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 150;

    public TableMainMenu() {

        Table table = new Table();
        addActor(table);
        table.setBackground(AssetManager.ui.newDrawable("blank", new Color(1,1,1,0.5f)));
        table.setFillParent(true);
        table.setDebug(false);
        table.defaults().width(BUTTON_WIDTH);
        table.defaults().height(BUTTON_HEIGHT);
        table.defaults().pad(20);


        PlainButton button;

        button = new PlainButton("EASY",
                new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                MainMenuScreen.setButtonPressed("easy");
            }
        });
        table.add(button);

        button = new PlainButton("MEDIUM", new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                MainMenuScreen.setButtonPressed("medium");
            }
        });

        table.add(button);

        button = new PlainButton("HARD", new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                MainMenuScreen.setButtonPressed("hard");
            }
        });

        table.add(button);
        table.row();

        button = new PlainButton("LOAD",new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                MainMenuScreen.setButtonPressed("load");
            }
        });

        table.add(button).colspan(2);

        button = new PlainButton("EXIT",new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) { MainMenuScreen.setButtonPressed("exit"); }
        });

        table.add(button);
        table.row();
    }
}
