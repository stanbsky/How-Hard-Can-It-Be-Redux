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
    private final Label label;

    /**
     * Sets up button
     * @param text of button
     * @param listener of button
     */
    public PlainButton(String text, ClickListener listener) {
        setBackground(button_up);
        this.setTouchable(Touchable.enabled);
        this.defaults().pad(5).left();
        BitmapFont font = pixelFont;
        font.getData().setScale(0.7f);
        label = new Label(text, new Label.LabelStyle(pixelFont, Color.BLACK));
        this.add(label);
        addListener(listener);
        addListener(clickListener = new ClickListener());
    }

    /**
     * Sets background depending on the clickListener
     */
    private void updateBackgroundDrawable() {
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

    /**
     * Draw button
     * @param batch for drawing
     * @param parentAlpha for drawing
     */
    public void draw(Batch batch, float parentAlpha) {
        updateBackgroundDrawable();
        if (clickListener.isPressed() && !isPressed) {
            isPressed = true;
        } else if (!clickListener.isPressed()) {
            isPressed = false;
        }
        super.draw(batch, parentAlpha);
    }

    public void setText(String text) {
        label.setText(text);
    }
}
