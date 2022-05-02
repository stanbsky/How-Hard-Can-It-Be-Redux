package com.ducks.components;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import static com.ducks.managers.AssetManager.atlas;

public class ChestAnimation extends Texture {

    private final Animation<TextureRegion> animation;

    /**
     * Places animation for chest in location
     * @param pos position of the chest
     * @param radius size of the animation
     */
    public ChestAnimation(Vector2 pos, float radius) {
        animation = new Animation<>(1f, atlas.findRegions("chest"), Animation.PlayMode.NORMAL);
        width = height = radius * 2;
        updatePosition(pos);
        update(0f);
    }

    /**
     * Updates the animation
     * @param deltaTime of the game
     */
    public void update(float deltaTime) {
        stateTime += deltaTime;
        frame = animation.getKeyFrame(stateTime);
    }
}
