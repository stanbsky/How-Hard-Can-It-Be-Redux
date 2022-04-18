package com.ducks.entities;

import com.badlogic.gdx.math.Vector2;

public interface IShooter {

    public boolean ready();
    public void resetShootTimer();
    public Vector2 getPosition();
}
