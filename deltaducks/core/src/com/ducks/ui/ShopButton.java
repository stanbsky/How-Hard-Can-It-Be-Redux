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
import com.ducks.managers.PowerupManager;
import com.ducks.managers.ShopManager;

import static com.ducks.managers.AssetManager.*;

public class ShopButton extends Table {

    private ClickListener clickListener;
    private boolean isPressed;
    private String powerup;
    private String price;
    private String name;
    private String description;
    private Label powerupInfo;

    public ShopButton(String powerup, BitmapFont font) {
        setBackground(button_up);
        this.powerup = powerup;
        this.price = String.valueOf(ShopManager.getItem(powerup).x);
        this.name = ShopManager.getItem(powerup).y;
        this.description = ShopManager.getItem(powerup).z;
        powerupInfo = new Label(PowerupManager.powerupLeft(powerup), new Label.LabelStyle( font , Color.BLACK));
//        this.price = Integer.parseInt(price);
        this.setTouchable(Touchable.enabled);
        this.defaults().pad(5).left();
        // Gold icon & price
        font.getData().setScale(0.8f);
        this.add(new Image(AssetManager.ui.newDrawable("coin2"))).size(50).left();
        this.add(new Label(price, new Label.LabelStyle( font , Color.BLACK))).left();
        // Powerup icon & name
        this.add(new Image(AssetManager.ui.newDrawable(powerup))).size(50).right();
        this.add(powerupInfo).right();
        this.add(new Label(name, new Label.LabelStyle( font , Color.BLACK))).right().expandX();

        addListener(clickListener = new ClickListener());
    }

    private void getBackgroundDrawable() {
        if (clickListener.isPressed()) {
            setBackground(button_down);
            return;
        }
        if (clickListener.isOver()) {
            setBackground(button_over);
            TablePauseMenu.updateInfo(description);
        } else {
            setBackground(button_up);
        }
    }

    public void draw(Batch batch, float parentAlpha) {
        getBackgroundDrawable();
        if (clickListener.isPressed() && !isPressed) {
            isPressed = true;
            ShopManager.buyItem(powerup);
            powerupInfo.setText(PowerupManager.powerupLeft(powerup));
            // TODO: Do stuff!
        } else if (!clickListener.isPressed()) {
            isPressed = false;
        }
        super.draw(batch, parentAlpha);
    }
}
