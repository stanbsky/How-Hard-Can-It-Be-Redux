package com.ducks.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import static com.ducks.screens.MainGameScreen.ui;

public class TablePauseMenu extends Stage {

    private static final int BUTTON_WIDTH = 300;

    public TablePauseMenu (Viewport viewport) {

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        addActor(table);
        table.defaults().width(BUTTON_WIDTH);
        table.defaults().pad(30);
        Button.ButtonStyle button = new Button.ButtonStyle();
        button.up = ui.newDrawable("play_up");
        button.over = ui.newDrawable("play_up", Color.LIGHT_GRAY);
        button.down = ui.newDrawable("play_down");
        table.add(new Button(button));
        table.row();
        button = new Button.ButtonStyle();
        button.up = ui.newDrawable("exit_up");
        button.over = ui.newDrawable("exit_up", Color.LIGHT_GRAY);
        button.down = ui.newDrawable("exit_down");
        table.add(new Button(button));
    }
}
