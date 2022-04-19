package com.ducks.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.ducks.screens.MainGameScreen.*;

public class TablePauseMenu extends Stage {

    private static final int BUTTON_WIDTH = 400;

    public TablePauseMenu (Viewport viewport) {

        Table table = new Table();
        addActor(table);
        table.setFillParent(true);
        table.setDebug(true);
        table.defaults().width(BUTTON_WIDTH);
        table.defaults().pad(30);

        // Tint the "up" texture on mouse over, switch to "down" on click
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = ui.newDrawable("play_up");
        style.over = ui.newDrawable("play_up", Color.LIGHT_GRAY);
        style.down = ui.newDrawable("play_down");
        Button button = new Button(style);

        // Unpause and go back to game
        button.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                togglePause();
            }
        });

        table.add(button);
        table.row();

        style = new Button.ButtonStyle();
        style.up = ui.newDrawable("exit_up");
        style.over = ui.newDrawable("exit_up", Color.LIGHT_GRAY);
        style.down = ui.newDrawable("exit_down");
        button = new Button(style);

        // Add 10000 to gold
        button.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                Hud.addGold(10000);
            }
        });

        table.add(button);

    }
}
