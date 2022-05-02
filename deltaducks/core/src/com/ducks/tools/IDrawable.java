package com.ducks.tools;

/**
 * Adds the functionality to be drawn
 */
public interface IDrawable {
    /**
     * Actualy draws the object
     */
    public void draw();

    /**
     * Calls before draw every frame
     * @param deltaTime the time elapsed since the last draw call
     */
    public void update(float deltaTime);

    /**
     * Does object require cleanup
     * @return true or false
     */
    public boolean cleanup();

    /**
     * Disposes of assets and resources
     */
    public void dispose();
}
