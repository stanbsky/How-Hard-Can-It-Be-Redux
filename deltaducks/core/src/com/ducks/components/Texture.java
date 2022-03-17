package com.ducks.components;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.screens.MainGameScreen;

public class Texture extends Sprite {

    float stateTime;
    TextureRegion frame;

    public Texture(String name, Vector2 pos, float width, float height) {
        super(MainGameScreen.resources.getTexture(name));
        setBounds(pos.x, pos.y, scl(width), scl(height));
        frame = new TextureRegion(getTexture(), 0, 0, width, height);
        setRegion(frame);
    }

    /**
     * Scale the dimensions by the world's scale ratio
     * @param dim unscaled size
     * @return scaled size
     */
    public static float scl(float dim) {
        return dim / DeltaDucks.PIXEL_PER_METER;
    }

    public void update(float deltaTime, Vector2 pos) {
        stateTime += deltaTime;
        setPosition(pos.x - getWidth()/2, pos.y - getHeight()/2);
        setRegion(frame);
    }

    public void render(SpriteBatch batch) {
        System.out.println(this.getBoundingRectangle());
        this.draw(batch);
    }
}
