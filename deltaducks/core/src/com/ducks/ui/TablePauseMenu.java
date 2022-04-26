package com.ducks.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.ducks.DeltaDucks;
import com.ducks.ui.Hud;
import jdk.internal.icu.impl.NormalizerImpl;

import static com.ducks.screens.MainGameScreen.*;

public class TablePauseMenu extends Stage {

    private static final int BUTTON_WIDTH = 250;

    public TablePauseMenu () {

        Table table = new Table();
        addActor(table);
        table.setFillParent(true);
//        table.setDebug(true, true);
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

        // Imagine this is the save button...
        table.add(button);

        table.row();

        Table shop = new Table();
        shop.setDebug(true);
        shop.pad(20);
        Container shopContainer = new Container<>(shop);
        table.add(shopContainer);

        // Do the font stuff
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/OpenSans-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        BitmapFont font = generator.generateFont(parameter);
//        font.getData().setScale(.5f);
//        Label shopLabel = new Label("Powerup Shop", new Label.LabelStyle( font , Color.BLACK));
//        shop.add(shopLabel);
//        shop.row();

//        ImageTextButton.ImageTextButtonStyle istyle = new ImageTextButton.ImageTextButtonStyle();
//        ImageTextButton ibutton;
//        istyle.font = font;
//        istyle.up = ui.newDrawable("spray");
//        istyle.over = ui.newDrawable("spray", Color.LIGHT_GRAY);
//        istyle.overFontColor = Color.LIGHT_GRAY;
//        istyle.down = ui.newDrawable("spray", Color.PINK);
//        istyle.downFontColor = Color.PINK;
//        ibutton = new ImageTextButton("Bullet Spray", istyle);
//        ibutton.getImage().setAlign(Align.left);
//        ibutton.getLabel().setAlignment(Align.right);
//        shop.add(ibutton);
        shop.add(new ShopButton("Quick Fire", "quickfire", "500", font));

//        table.background(draw(Batch batch, float x, float y, float width, float height));
    }
}
