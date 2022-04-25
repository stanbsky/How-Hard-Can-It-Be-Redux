package com.ducks.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import com.ducks.components.Texture;
import com.ducks.screens.MainMenuScreen;

public class MainMenu extends Stage {

    private static final int BUTTON_WIDTH = 500;

    public MainMenu (com.badlogic.gdx.graphics.Texture playButtonActive) {

        Table table = new Table();
        addActor(table);
        table.setFillParent(true);
        table.setDebug(true);
        table.defaults().width(BUTTON_WIDTH);
        table.defaults().pad(30);

        // PLAY

        // Tint the "up" texture on mouse over, switch to "down" on click
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(MainMenuScreen.playButtonInactive));
        style.over = new TextureRegionDrawable(new TextureRegion(MainMenuScreen.playButtonInactive)).tint(Color.GRAY);
        style.down = new TextureRegionDrawable(new TextureRegion(MainMenuScreen.playButtonActive));
        Button button = new Button(style);

        // Unpause and go back to game
        button.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                System.out.println("tees");
            }
        });

        table.add(button);

        // EXIT

        style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(MainMenuScreen.exitButtonInactive));
        style.over = new TextureRegionDrawable(new TextureRegion(MainMenuScreen.exitButtonInactive)).tint(Color.GRAY);
        style.down = new TextureRegionDrawable(new TextureRegion(MainMenuScreen.exitButtonActive));
        button = new Button(style);

        // Add 10000 to gold
        button.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                Hud.addGold(10000);
            }
        });

        table.row();

        // EASY

        style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(MainMenuScreen.easyButtonInactive));
        style.over = new TextureRegionDrawable(new TextureRegion(MainMenuScreen.easyButtonInactive)).tint(Color.GRAY);
        style.down = new TextureRegionDrawable(new TextureRegion(MainMenuScreen.easyButtonActive));
        button = new Button(style);

        // Add 10000 to gold
        button.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                Hud.addGold(10000);
            }
        });

        // MEDIUM

        style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(MainMenuScreen.mediumButtonInactive));
        style.over = new TextureRegionDrawable(new TextureRegion(MainMenuScreen.mediumButtonInactive)).tint(Color.GRAY);
        style.down = new TextureRegionDrawable(new TextureRegion(MainMenuScreen.mediumButtonActive));
        button = new Button(style);

        // Add 10000 to gold
        button.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                Hud.addGold(10000);
            }
        });

        // HARD

        style = new Button.ButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(MainMenuScreen.hardButtonInactive));
        style.over = new TextureRegionDrawable(new TextureRegion(MainMenuScreen.hardButtonInactive)).tint(Color.GRAY);
        style.down = new TextureRegionDrawable(new TextureRegion(MainMenuScreen.hardButtonActive));
        button = new Button(style);

        // Add 10000 to gold
        button.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                Hud.addGold(10000);
            }
        });

        table.add(button);

//        table.background(draw(Batch batch, float x, float y, float width, float height));
    }
}
