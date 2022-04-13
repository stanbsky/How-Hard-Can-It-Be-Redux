package com.ducks.components;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ducks.DeltaDucks;
import com.ducks.screens.MainGameScreen;

public class Texture {

    float stateTime;
    TextureRegion frame;
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    private TextureAtlas atlas;

    public Texture(String name, Vector2 pos, float radius) {
        this(name, pos, radius, MainGameScreen.getAtlas());
    }

    public Texture(String name, Vector2 pos, float radius, TextureAtlas atlas) {
        this.atlas = atlas;
        frame = this.atlas.findRegion(name);

        width = height = radius * 2;
        x = pos.x - this.width/2;
        y = pos.y - this.height/2;
    }

    public Texture() {
    }

    public void update(float deltaTime, Vector2 pos) {
        stateTime += deltaTime;
        updatePosition(pos);
    }

    public void updatePosition(Vector2 pos) {
        x = pos.x - width/2;
        y = pos.y - height/2;
    }

    public void render(SpriteBatch batch) {
        batch.draw(this.frame, this.x, this.y, width, height);
    }

}
