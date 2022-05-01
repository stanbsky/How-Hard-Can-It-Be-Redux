package com.ducks.tools.Saving;

import java.util.ArrayList;

public class EntitiesSaveData implements ISaveData {
    public ArrayList<CollageSaveData> collages;
    public ArrayList<PirateSaveData> pirates;

    public EntitiesSaveData() {
        collages = new ArrayList<>();
        pirates = new ArrayList<>();
    }
}
