package com.ducks.tools;

/**
 * Adds the functionality to be drawn
 */
public interface IDrawable {
    /**
     * Actually draws the object
     */
    void draw();

    /**
     * Calls before draw every frame
     * @param deltaTime the time elapsed since the last draw call
     */
    void update(float deltaTime);

    /**
     * Does object require cleanup
     * @return true or false
     */
    boolean cleanup();

    /**
     * Disposes of assets and resources
     */
    void dispose();
}
