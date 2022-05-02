package com.ducks.managers;

import com.badlogic.gdx.utils.Json;
import com.ducks.entities.Player;
import com.ducks.intangibles.DifficultyControl;
import com.ducks.screens.MainGameScreen;
import com.ducks.tools.Saving.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static com.ducks.DeltaDucks.PIXEL_PER_METER;

public class SaveManager {
    public static boolean LoadSave = false;
    public static SaveData saveData;
    private static final Path SAVE_LOCATION = Paths.get("./Saves/Save.json");

    /**
     * Sets up save manager
     */
    public static void Initialize() {
        saveData = null;
        LoadFile();
    }

    /**
     * Reads the save file, ready for loading
     */
    private static void LoadFile() {
        try {
            File myObj = new File("./Saves");
            if(myObj.exists()) {
                myObj = new File(SAVE_LOCATION.toString());
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String txt = myReader.nextLine();
                    Json j = new Json();
                    saveData = j.fromJson(SaveData.class, txt);
                }
                myReader.close();
            }
            else {
                myObj.mkdirs();
            }
        }
        catch (Exception ignored) {

        }
    }

    /**
     * Reads and activates loaded save file if one exists
     */
    public static void LoadSave() {
        if (saveData != null) {
            LoadSave = true;
            Player.setHealth(saveData.player.health);
            DifficultyControl.setDifficulty(saveData.difficulty);
            PowerupManager.setPowerUps(saveData.powerUps);

            StatsManager.Load(saveData.stats);
            // EntityManager.Load(saveData.entityManager);
        }
    }

    /**
     * Saves current data
     */
    public static void Save() {
        try {
            FileWriter out = new FileWriter(SAVE_LOCATION.toString());

            SaveData save = new SaveData();

            PlayerSaveData pData = new PlayerSaveData();
            pData.health = Player.getHealth();
            pData.position = MainGameScreen.player.getPosition().scl(PIXEL_PER_METER);

            save.difficulty = DifficultyControl.getDifficulty();
            save.powerUps = PowerupManager.getPowerUps();

            save.player = pData;
            save.stats = (StatsSaveData) StatsManager.Save();
            save.entityManager = (EntitiesSaveData) EntityManager.Save();
            save.quests = (QuestSaveData) QuestManager.Save();

            Json j = new Json();
            String d = j.toJson(save);
            out.write(d);
            out.close();
            saveData = save;
        }
        catch (Exception ignored) {
            int i = 0;
        }
    }
}
