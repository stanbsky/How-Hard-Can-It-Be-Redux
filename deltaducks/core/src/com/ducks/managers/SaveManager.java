package com.ducks.managers;

import com.badlogic.gdx.utils.Json;
import com.ducks.tools.SaveData;
import com.ducks.ui.Hud;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class SaveManager {
    public static SaveData saveData;
    private static final Path SAVE_LOCATION = Paths.get("./Saves/Save.json");
    public static void Initialize() {
        saveData = null;
        LoadSave();
    }
    private static void LoadSave() {
        try {
            if(Files.exists(SAVE_LOCATION)) {
                File myObj = new File(SAVE_LOCATION.toString());
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String txt = myReader.nextLine();
                    Json j = new Json();
                    saveData = j.fromJson(SaveData.class, txt);
                }
                myReader.close();
            }
        }
        catch (Exception ignored) {

        }
    }

    public static void Save(Hud hud) {
        try {
            FileWriter out = new FileWriter(SAVE_LOCATION.toString());

            SaveData save = new SaveData();
            save.gold = Hud.getGold();
            save.health = Hud.getHealth();
            save.xp = Hud.getScore();
            save.time = hud.getTimer();

            Json j = new Json();
            String d = j.toJson(save);
            out.write(d);
            out.close();
            saveData = save;
        }
        catch (Exception ignored) {

        }
    }
}
