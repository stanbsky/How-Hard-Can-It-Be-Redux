package com.ducks.components;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import static com.ducks.DeltaDucks.batch;
import static com.ducks.screens.MainGameScreen.atlas;

public class ChestAnimation extends Texture {

    private Animation<TextureRegion> animation;

    public ChestAnimation(Vector2 pos, float radius) {
        animation = new Animation<>(1f, atlas.findRegions("chest"), Animation.PlayMode.NORMAL);
        width = height = radius * 2;
        updatePosition(pos);
        update(0f);
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;
        frame = animation.getKeyFrame(stateTime);
    }
}
