package com.ducks.components;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.screens.MainGameScreen;

public class Texture extends Sprite {

    float stateTime;
    TextureRegion frame;
    float x;
    float y;
    float width;
    float height;

    public Texture(String name, Vector2 pos, float scale) {
        frame = MainGameScreen.getAtlas().findRegion(name);

        setDims(scale);
        x = pos.x - this.width/2;
        y = pos.y - this.height/2;
    }

    /**
     * Scale the dimensions by the world's scale ratio
     * @param dim unscaled size
     * @return scaled size
     */
    public static float scl(int dim) {
        return ((float) dim) / (DeltaDucks.PIXEL_PER_METER);
    }

    public void setDims(float scale) {
        width = scl(frame.getRegionWidth()) * scale;
        height = scl(frame.getRegionHeight()) * scale;
    }

    public void update(float deltaTime, Vector2 pos) {
        stateTime += deltaTime;
        x = pos.x - width/2;
        y = pos.y - height/2;
    }

    public void render(SpriteBatch batch) {
        batch.draw(this.frame, this.x, this.y, width, height);
    }

}
