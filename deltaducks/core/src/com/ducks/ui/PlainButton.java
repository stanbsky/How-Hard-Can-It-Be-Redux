package com.ducks.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static com.ducks.managers.AssetManager.*;

public class PlainButton extends Table {

    private ClickListener clickListener;
    private boolean isPressed;

    public PlainButton(String text, ClickListener listener) {
        setBackground(button_up);
        this.setTouchable(Touchable.enabled);
        this.defaults().pad(5).left();
        BitmapFont font = pixelFont;
        font.getData().setScale(0.7f);
        this.add(new Label(text, new Label.LabelStyle(pixelFont, Color.BLACK)));
        addListener(listener);
        addListener(clickListener = new ClickListener());
    }

    private void getBackgroundDrawable() {
        if (clickListener.isPressed()) {
            setBackground(button_down);
            return;
        }
        if (clickListener.isOver()) {
            setBackground(button_over);
        } else {
            setBackground(button_up);
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        getBackgroundDrawable();
        if (clickListener.isPressed() && !isPressed) {
            isPressed = true;
        } else if (!clickListener.isPressed()) {
            isPressed = false;
        }
        super.draw(batch, parentAlpha);
    }
}
