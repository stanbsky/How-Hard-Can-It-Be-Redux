package com.ducks.components;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;

import static com.ducks.screens.MainGameScreen.atlas;


public class ShipAnimation extends Texture {

    private Animation[][] animation;
    private static final int DEFAULT_DIRECTION = 6;

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

    public void update(float deltaTime, Vector2 pos, int direction, boolean moving) {
        stateTime += deltaTime;
        updateFrame(stateTime, direction, moving);
        updatePosition(pos);
        updateRenderColor();
    }

    private void updateDimensions() {
        // TODO: see https://github.com/stanbsky/How-Hard-Can-It-Be-Redux/issues/11#issue-1198623843
        width = (float)frame.getRegionWidth();
        height = (float)frame.getRegionHeight();
    }

    public void updateFrame(float stateTime, int direction, boolean moving) {
        int movingKey = moving ? 1 : 0;
        frame = (AtlasRegion) animation[movingKey][direction-1].getKeyFrame(stateTime, true);
    }

    @Deprecated
    public AtlasRegion getFrame(float stateTime, int direction, boolean moving) {
        int movingKey = moving ? 1 : 0;
        return (AtlasRegion) animation[movingKey][direction-1].getKeyFrame(stateTime, true);
    }

}
