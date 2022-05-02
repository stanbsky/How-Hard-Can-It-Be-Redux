package com.ducks.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import static com.ducks.managers.AssetManager.button_up;
import static com.ducks.managers.AssetManager.ui;

public class Subtitle extends Table {

    private final String initialMessage = "Find your bearings and get ready!";
    private final float maxWidth = 800f;
    private BitmapFont font;

    public Subtitle(BitmapFont font) {
        this.font = font;
        setBackground(button_up);
        setDebug(false);
        this.defaults().prefWidth(maxWidth).center();
        font.getData().setScale(0.8f);
        Label l = new Label(initialMessage, new Label.LabelStyle(font, Color.BLACK));
        l.setAlignment(Align.center);
        this.add(l);
    }

    public void setQuestNotice(String first, String icon, String second) {
        this.clearChildren();
        Label l = new Label(first, new Label.LabelStyle(font, Color.BLACK));
        l.setAlignment(Align.right);
        this.add(l);
        this.add(new Image(ui.newDrawable(icon))).size(40);
        l = new Label(second, new Label.LabelStyle(font, Color.BLACK));
        l.setAlignment(Align.left);
        this.add(l);
    }

    public void setNotice(String notice) {
        this.clearChildren();
        Label l = new Label(notice, new Label.LabelStyle(font, Color.BLACK));
        l.setAlignment(Align.center);
        this.add(l);
    }

    public void setDoubleIconNotice(String notice, String icon) {
        this.clearChildren();
        Image i = new Image(ui.newDrawable(icon));
        i.setAlign(Align.right);
        this.add(i).size(40);
        this.add(new Label(notice, new Label.LabelStyle(font, Color.BLACK)));
        i = new Image(ui.newDrawable(icon));
        i.setAlign(Align.left);
        this.add(i).size(40);
    }
}
