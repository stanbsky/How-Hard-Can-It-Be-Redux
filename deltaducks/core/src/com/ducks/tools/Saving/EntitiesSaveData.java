package com.ducks.tools.Saving;

import java.util.ArrayList;

public class EntitiesSaveData implements ISaveData {
    public ArrayList<PowerupSaveData> powerups;
    public ArrayList<CollageSaveData> collages;
    public ArrayList<PirateSaveData> pirates;

    public EntitiesSaveData() {
        powerups = new ArrayList<>();
        collages = new ArrayList<>();
        pirates = new ArrayList<>();
    }
}
