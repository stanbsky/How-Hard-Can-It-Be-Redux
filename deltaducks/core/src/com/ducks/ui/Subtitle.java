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

    /**
     * Instantiate subtitle
     * @param font for label
     */
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

    /**
     * Updates label with new text
     * @param first block of text
     * @param icon in middle (relates to indicator)
     * @param second block of text
     */
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

    /**
     * Updates label with new text
     * @param notice block of text
     */
    public void setNotice(String notice) {
        this.clearChildren();
        Label l = new Label(notice, new Label.LabelStyle(font, Color.BLACK));
        l.setAlignment(Align.center);
        this.add(l);
    }

    /**
     * Updates label with new text
     * @param notice block of text
     * @param icon before and after text
     */
    public void setDoubleIconNotice(String notice, String icon) {
        this.clearChildren();
        Image i = new Image(ui.newDrawable(icon));
        i.setAlign(Align.right);
        this.add(i).size(40);
        Label l = new Label(notice, new Label.LabelStyle(font, Color.BLACK));
        l.setAlignment(Align.center);
        this.add(l);
        i = new Image(ui.newDrawable(icon));
        i.setAlign(Align.left);
        this.add(i).size(40);
    }
}
