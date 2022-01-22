package com.ducks.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.ducks.DeltaDucks;
import com.ducks.tools.Animation;

public class B2DSprite {

    protected Body body;
    protected Animation animation;
    protected float width;
    protected float height;

    public B2DSprite(Body body) {
        this.body = body;
        animation = new Animation();
    }

    public void setAnimation(TextureRegion[] reg, float delay) {
        animation.setFrames(reg, delay);
        width = reg[0].getRegionWidth();
        height = reg[0].getRegionHeight();
    }

    public void update(float deltaTime) {
        animation.update(deltaTime);
    }

    public void render(SpriteBatch batch) { //32 / DeltaDucks.PIXEL_PER_METER, 32 / DeltaDucks.PIXEL_PER_METER
        batch.begin();
        batch.draw(
                animation.getFrame(),
                body.getPosition().x * DeltaDucks.PIXEL_PER_METER - width / 2 + DeltaDucks.VIRTUAL_WIDTH/DeltaDucks.PIXEL_PER_METER,
                body.getPosition().y * DeltaDucks.PIXEL_PER_METER - height / 2 + DeltaDucks.VIRTUAL_HEIGHT/DeltaDucks.PIXEL_PER_METER
        );
        batch.end();
    }

    public Body getBody() {
        return body;
    }

    public Vector2 getPosition() { return body.getPosition(); }

    public float getWidth() { return width; }

    public float getHeight() { return height; }

}
