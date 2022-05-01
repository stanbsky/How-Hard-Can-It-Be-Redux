package com.ducks.tools.Saving;

import com.badlogic.gdx.math.Vector2;

public class PlayerSaveData implements ISaveData {
    public float health;
    public Vector2 position;

    public PlayerSaveData(){
        position = new Vector2();
    }
}
