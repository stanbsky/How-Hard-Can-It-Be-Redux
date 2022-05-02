package com.ducks.components;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;

import static com.ducks.managers.AssetManager.atlas;


public class ShipAnimation extends Texture {

    private Animation[][] animation;
    private static final int DEFAULT_DIRECTION = 6;

    /**
     * Instantiation of new ship animation
     * @param name of ship
     * @param pos of ship
     * @param radius of ship
     * @param frameDuration speed of animation
     */
    public ShipAnimation(String name, Vector2 pos, float radius, float frameDuration) {
        animation = new Animation[2][9];
        for (int i = 0; i < 9; i++) {
            // No animation exists for 5
            if (i == 4)
                continue;

            // Idle
            animation[0][i] = new Animation<>(frameDuration,
                    atlas.findRegions(name + "0" + (i + 1)),
                    Animation.PlayMode.LOOP);
            // Moving
            animation[1][i] = new Animation<>(frameDuration,
                    atlas.findRegions(name + (i + 1)),
                    Animation.PlayMode.LOOP);
        }
        width = height = radius * 2;
        update(0f, pos, DEFAULT_DIRECTION, false);
    }

    /**
     * Updating animation
     * @param deltaTime of game
     * @param pos of ship
     * @param direction of ship
     * @param moving whether the ship is moving
     */
    public void update(float deltaTime, Vector2 pos, int direction, boolean moving) {
        stateTime += deltaTime;
        updateFrame(stateTime, direction, moving);
        updatePosition(pos);
        updateRenderColor();
    }

    /**
     * Updating animation
     * @param stateTime point of animation
     * @param direction of ship
     * @param moving whether the ship is moving
     */
    public void updateFrame(float stateTime, int direction, boolean moving) {
        int movingKey = moving ? 1 : 0;
        frame = (AtlasRegion) animation[movingKey][direction-1].getKeyFrame(stateTime, true);
    }

}
