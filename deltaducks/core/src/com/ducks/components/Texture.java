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
//        super(MainGameScreen.resources.getTexture("mehnat"));
        frame = MainGameScreen.getAtlas().findRegion(name);
//        super(MainGameScreen.getAtlas().findRegion("bullet_player256"));
//        setBounds(pos.x, pos.y, scl(width), scl(height));

        setDims(scale);
        x = pos.x - this.width/2;
        y = pos.y - this.height/2;
//        frame = MainGameScreen.getAtlas().findRegion("bullet_player256");
//        frame = new TextureRegion(getTexture(), 0, 0, 256, 256);
//        setRegion(frame);
//        System.out.println(getWidth());
//        System.out.println(getRegionWidth());
//        System.out.println(getBoundingRectangle());
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
        //System.out.println(frame.getRegionWidth());
        width = scl(frame.getRegionWidth()) * scale;
        height = scl(frame.getRegionHeight()) * scale;
    }

    public void update(float deltaTime, Vector2 pos) {
        stateTime += deltaTime;
        x = pos.x - width/2;
        y = pos.y - height/2;
//        setPosition(pos.x - getWidth()/2, pos.y - getHeight()/2);
//        this.x = pos.x - getWidth()/2;
//        this.y = pos.y - getHeight()/2;
//        setRegion(frame);
    }

    public void render(SpriteBatch batch) {
//        this.draw(batch);
        batch.draw(this.frame, this.x, this.y, width, height);
    }

    public void dispose() {
//        this.dispose();
    }
}
