package com.ducks.tools;

import com.badlogic.gdx.math.Vector2;

/**
 * Facilitates the shooter class
 */
public interface IShooter {

    /**
     * Query if it is ready to shoot
     * @return true or false
     */
    boolean ready();

    /**
     * sets the shooting timer to is default value
     */
    void resetShootTimer();
    Vector2 getPosition();
}
