package com.ducks.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.screens.MainGameScreen;
import com.ducks.sprites.Ship;

public class Tutorial {
    public OrthographicCamera gameCam;
    private Pixmap pixmap;

    private final int PIXEL_WORM_WIDTH = 212;
    private final int PIXEL_WORM_HEIGHT = 128;

    private final float WORM_WIDTH = PIXEL_WORM_WIDTH * .2f;
    private final float WORM_HEIGHT = PIXEL_WORM_HEIGHT * .2f;

    private Animation <TextureRegion> arrow;

    float stateTime;
    float SPAWN_X;
    float SPAWN_Y;
    Ship player;

    public Tutorial(OrthographicCamera gameCam,  Ship player) {
        this.gameCam = gameCam;
        this.player = player;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i=0; i<6; i++) {
            frames.add(new TextureRegion(MainGameScreen.resources.getTexture("arrow"), i * PIXEL_WORM_WIDTH, 0, PIXEL_WORM_WIDTH, PIXEL_WORM_HEIGHT));
        }
        arrow = new Animation(0.1f, frames);
        frames.clear();

        SPAWN_X = player.b2body.getPosition().x - 2*player.b2body.getFixtureList().get(0).getShape().getRadius();
        SPAWN_Y = player.b2body.getPosition().y ;
    }

    public void update(float deltaTime) {
        pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(1f, 0.1f, 0.1f, .3f));
        pixmap.fillRectangle(0, 0, 100, 100);
        stateTime += deltaTime;
    }

    public void draw(SpriteBatch batch) {
//        batch.draw(MainGameScreen.resources.getTexture("arrow"), 1, 1, MainGameScreen.resources.getTexture("arrow").getWidth() / DeltaDucks.PIXEL_PER_METER,MainGameScreen.resources.getTexture("arrow").getHeight()/ DeltaDucks.PIXEL_PER_METER);
        batch.draw(arrow.getKeyFrame(stateTime, true), SPAWN_X, SPAWN_Y, WORM_WIDTH/ DeltaDucks.PIXEL_PER_METER,WORM_HEIGHT/ DeltaDucks.PIXEL_PER_METER);

        batch.draw(new Texture(pixmap), gameCam.position.x - gameCam.viewportWidth/2, gameCam.position.y  - gameCam.viewportHeight/2, pixmap.getWidth() / DeltaDucks.PIXEL_PER_METER, pixmap.getHeight() / DeltaDucks.PIXEL_PER_METER);
    }
}
