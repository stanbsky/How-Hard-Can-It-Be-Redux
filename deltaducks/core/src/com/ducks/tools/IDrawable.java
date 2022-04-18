package com.ducks.tools;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface IDrawable {
    public void draw(SpriteBatch batch);
    public void update(float deltaTime);
}
