package com.ducks.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import static com.ducks.screens.MainGameScreen.*;

public class ShopButton extends Table {

    private ClickListener clickListener;
    private NinePatchDrawable passive, over, down;

    public ShopButton(String label, String powerup, String price, BitmapFont font) {
        this.setDebug(true);
        initBackgrounds();
        this.setTouchable(Touchable.enabled);
        this.setWidth(200);
        this.defaults().pad(20).left();
        // Gold icon & price
        this.add(new Image(ui.newDrawable("coin2"))).size(50);
        this.add(new Label(price, new Label.LabelStyle( font , Color.WHITE)));
        // Powerup icon & name
        this.add(new Image(ui.newDrawable(powerup))).size(50);
        this.add(new Label(label, new Label.LabelStyle( font , Color.WHITE)));
        // Description
        this.row();
        this.add(new Label("yadda yadda", new Label.LabelStyle( font , Color.WHITE)));

        addListener(clickListener = new ClickListener());
    }

    private void initBackgrounds() {
        NinePatch patch;
        patch = new NinePatch(ui.getRegion("button"), 17, 17, 17, 17);
        passive = new NinePatchDrawable(patch);
        patch = new NinePatch(ui.getRegion("button_over"), 17, 17, 17, 17);
        over = new NinePatchDrawable(patch);
        patch = new NinePatch(ui.getRegion("button_down"), 17, 17, 17, 17);
        down = new NinePatchDrawable(patch);
        this.setBackground(passive);
    }

    private void getBackgroundDrawable() {
        if (clickListener.isPressed()) {
            setBackground(down);
            return;
        }
        if (clickListener.isOver())
            setBackground(over);
        else
            setBackground(passive);
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
