package com.ducks.intangibles;

public class DifficultyControl {

    private static int difficulty = 2;

    public DifficultyControl () {
    }

    public static int getDifficulty() {
        System.out.println(difficulty);
        return difficulty; }
}
