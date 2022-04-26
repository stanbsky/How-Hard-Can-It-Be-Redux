package com.ducks.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.ducks.managers.AssetManager;

import static com.ducks.managers.AssetManager.*;

public class ShopButton extends Table {

    private ClickListener clickListener;

    public ShopButton(String label, String powerup, String price, BitmapFont font) {
        setBackground(button_up);
        this.setTouchable(Touchable.enabled);
        this.defaults().pad(5).left();
        // Gold icon & price
        font.getData().setScale(0.8f);
        this.add(new Image(AssetManager.ui.newDrawable("coin2"))).size(50).left();
        this.add(new Label(price, new Label.LabelStyle( font , Color.BLACK))).left();
        // Powerup icon & name
        this.add(new Image(AssetManager.ui.newDrawable(powerup))).size(50).right();
        this.add(new Label(label, new Label.LabelStyle( font , Color.BLACK))).right();

        addListener(clickListener = new ClickListener());
    }

    private void getBackgroundDrawable() {
        if (clickListener.isPressed()) {
            setBackground(button_down);
            return;
        }
        if (clickListener.isOver())
            setBackground(button_over);
        else
            setBackground(button_up);
    }

    public void draw(Batch batch, float parentAlpha) {
        getBackgroundDrawable();
        if (clickListener.isPressed()) {
            System.out.println("CLICK");
            // TODO: Do stuff!
        }
        super.draw(batch, parentAlpha);
    }
}
