package com.ducks.components;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.ducks.screens.MainGameScreen;


public class ShipAnimation {

    private Animation[][] animation;

    public ShipAnimation(String name, float frameDuration) {
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
    }

    public AtlasRegion getFrame(float stateTime, int direction, boolean moving) {
        int movingKey = moving ? 1 : 0;
        return (AtlasRegion) animation[movingKey][direction-1].getKeyFrame(stateTime, true);
    }

}
