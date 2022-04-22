package com.ducks.components;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.ducks.screens.MainGameScreen;

import static com.ducks.DeltaDucks.batch;
import static com.ducks.screens.MainGameScreen.atlas;

public class Texture {

    float stateTime;
    TextureRegion frame;
    protected float x;
    protected float y;
    protected float width;
    protected float height;

    public Texture(String name, Vector2 pos, float radius) {
        frame = atlas.findRegion(name);

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

    public void render() {
        batch.draw(this.frame, this.x, this.y, width, height);
    }

}
