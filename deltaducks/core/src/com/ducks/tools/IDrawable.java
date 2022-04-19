package com.ducks.tools;

public interface IDrawable {
    public void draw();
    public void update(float deltaTime);
    public boolean cleanup();
}
