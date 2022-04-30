package com.ducks.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import static com.ducks.managers.AssetManager.button_up;

public class TableSubtitle extends Table {

    private final String initialMessage = "Find your bearings and get ready!";
    private final float maxWidth = 800f;

    public TableSubtitle(BitmapFont font) {
        setBackground(button_up);
        setDebug(true);
        this.defaults().prefWidth(maxWidth).center();
        font.getData().setScale(0.8f);
        Label l = new Label(initialMessage, new Label.LabelStyle(font, Color.BLACK));
        l.setAlignment(Align.center);
        this.add(l);
    }

    public void setQuestNotice(String first, String glyph, String second) {
        this.clearChildren();
    }
}
