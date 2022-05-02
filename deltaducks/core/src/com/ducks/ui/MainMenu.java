package com.ducks.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import com.ducks.managers.AssetManager;
import com.ducks.screens.MainMenuScreen;
import sun.tools.jconsole.Tab;

public class MainMenu extends Stage {

    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 130;

    public MainMenu() {

        Table table = new Table();
        addActor(table);
        table.setBackground(AssetManager.ui.newDrawable("blank", new Color(1,1,1,0.8f)));
        table.setFillParent(true);
        table.setDebug(false);
        table.defaults().height(BUTTON_HEIGHT);
        table.defaults().pad(15).expandX();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/boy.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 60;
        BitmapFont font = generator.generateFont(parameter);
//        font.getData().setScale(.5f);
        Subtitle title = new Subtitle(font);
        title.setNotice("~~ Ship of Theseus ~~");
        Table subTable = new Table();
        Cell<Subtitle> titleCell = table.add(title).width(950);
        table.row();

        PlainButton button;
        Table middleRow = new Table();
        middleRow.defaults().pad(40).width(BUTTON_WIDTH).height(BUTTON_HEIGHT);

        button = new PlainButton("EASY",
                new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                MainMenuScreen.setButtonPressed("easy");
            }
        });
        middleRow.add(button);

        button = new PlainButton("MEDIUM", new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                MainMenuScreen.setButtonPressed("medium");
            }
        });

        middleRow.add(button);

        button = new PlainButton("HARD", new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                MainMenuScreen.setButtonPressed("hard");
            }
        });

        middleRow.add(button);
        table.add(middleRow);
        table.row();

        Table bottomRow = new Table();
        bottomRow.defaults().width(BUTTON_WIDTH).height(BUTTON_HEIGHT).pad(40);

        button = new PlainButton("LOAD", new ClickListener() {
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

        // Adding instructions
        table.row();
        BitmapFont instructionFont = generator.generateFont(parameter);
        instructionFont.getData().setScale(0.4f);
        Label instructions = new Label("Use W A S D to move the ship.\nUse the mouse to move the aim and fire with left click", new Label.LabelStyle(instructionFont, Color.BLACK));
        instructions.setWrap(true);
        table.add(instructions).colspan(3).fillX().expandX().width(1000);

//        table.pack();
//        //titleCell.width(table.getWidth());
//        table.pack();
    }
}
