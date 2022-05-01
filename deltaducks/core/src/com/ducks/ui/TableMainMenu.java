package com.ducks.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import com.ducks.managers.AssetManager;
import com.ducks.screens.MainMenuScreen;

public class TableMainMenu extends Stage {

    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 130;

    public TableMainMenu() {

        Table table = new Table();
        addActor(table);
        table.setBackground(AssetManager.ui.newDrawable("blank", new Color(1,1,1,0.8f)));
        table.setFillParent(true);
        table.setDebug(true);
        table.defaults().width(BUTTON_WIDTH);
        table.defaults().height(BUTTON_HEIGHT);
        table.defaults().pad(15);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/boy.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 60;
        BitmapFont font = generator.generateFont(parameter);
//        font.getData().setScale(.5f);
        TableSubtitle title = new TableSubtitle(font);
        title.setNotice("~~ Pirates of York ~~");
        Cell<TableSubtitle> titleCell = table.add(title).colspan(3).grow();//.width(600);
        table.row();

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

        Table bottomRow = new Table();
        bottomRow.defaults().width(BUTTON_WIDTH).height(BUTTON_HEIGHT).pad(15);

        button = new PlainButton("LOAD",new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                MainMenuScreen.setButtonPressed("load");
            }
        });

        bottomRow.add(button);

        button = new PlainButton("EXIT",new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) { MainMenuScreen.setButtonPressed("exit"); }
        });

        bottomRow.add(button);
        table.add(bottomRow).colspan(3);

        table.pack();
        titleCell.width(table.getWidth());
        table.pack();
    }
}
