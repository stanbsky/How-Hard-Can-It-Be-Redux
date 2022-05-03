package com.ducks.tools.Saving;

import com.badlogic.gdx.math.Vector2;

/**
 * The data saved for The Player
 */
public class PlayerSaveData implements ISaveData {
    public float health;
    public Vector2 position;

    public PlayerSaveData(){
        position = new Vector2();
    }
}
