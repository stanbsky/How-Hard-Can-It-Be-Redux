package com.ducks.managers;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.ducks.tools.SaveData;
import com.ducks.ui.Hud;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class SaveManager {
    private static boolean inited = false;
    private static HashMap<String, SaveData> data;
    private static final Path SAVE_LOCATION = Paths.get("./Saves");
    public static void Initialize() {
        inited = true;
        data = new HashMap<>();
        LoadSaves();
    }
    public static void LoadSaves() {
        if(!Files.exists(SAVE_LOCATION)) {
            try{
                Files.createDirectories(SAVE_LOCATION);
            }
            catch (Exception e){

            }
        }

        File folder = new File(String.valueOf(SAVE_LOCATION));

        File[] listOfFiles = folder.listFiles();

        assert listOfFiles != null;

        for (File file : listOfFiles) {
            if (file.isFile()) {
                String name = file.getName();
                name = name.substring(0, name.length() - 6);
                try{
                    File myObj = new File(file.getPath());
                    Scanner myReader = new Scanner(myObj);
                    while (myReader.hasNextLine()) {
                        String txt = myReader.nextLine();
                        Json j = new Json();
                        SaveData save = j.fromJson(SaveData.class, txt);
                        int i = 0;
                        data.put(name, save);
                    }
                    myReader.close();
                }
                catch (Exception e) {

                }
            }
        }
    }

    public static void Save(Hud hud) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy, HH-mm");

        String fileName = String.format("/Save " +  (data.size() + 1) + " " + now.format(formatter) + ".json");
        try {
            FileWriter out = new FileWriter(SAVE_LOCATION.toString() + fileName);
            SaveData save = new SaveData();
            save.gold = Hud.getGold();
            save.health = Hud.getHealth();
            save.xp = Hud.getScore();
            save.time = hud.getTimer();
            Json j = new Json();
            String d = j.toJson(save);
            out.write(d);
            out.close();
        }
        catch (Exception e) {

        }
    }

    private static void tryInit() {
        if(!inited) {
            Initialize();
        }
    }
}
