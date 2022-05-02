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
    public boolean ready();
    /**
     * sets the shooting timer to is default value
     */
    public void resetShootTimer();
    public Vector2 getPosition();
}
