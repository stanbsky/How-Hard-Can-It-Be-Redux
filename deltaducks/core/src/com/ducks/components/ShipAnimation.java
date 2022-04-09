package com.ducks.components;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.ducks.screens.MainGameScreen;


public class ShipAnimation extends Texture {

    private Animation[][] animation;
    private static final int DEFAULT_DIRECTION = 6;

    public ShipAnimation(String name, float frameDuration, Vector2 pos) {
        animation = new Animation[2][9];
        for (int i = 0; i < 9; i++) {
            // No animation exists for 5
            if (i == 4)
                continue;

            // Idle
            animation[0][i] = new Animation<>(frameDuration,
                    MainGameScreen.getAtlas().findRegions(name + "0" + (i + 1)),
                    Animation.PlayMode.LOOP);
            // Moving
            animation[1][i] = new Animation<>(frameDuration,
                    MainGameScreen.getAtlas().findRegions(name + (i + 1)),
                    Animation.PlayMode.LOOP);
        }
        update(0f, pos, DEFAULT_DIRECTION, false);
        System.out.println(width + "," + height);
    }

    public void update(float deltaTime, Vector2 pos, int direction, boolean moving) {
        stateTime += deltaTime;
        updateFrame(stateTime, direction, moving);
        updateDimensions();
        updatePosition(pos);
    }

    private void updateDimensions() {
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
