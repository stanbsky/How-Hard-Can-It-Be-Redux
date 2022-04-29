package com.ducks.intangibles;

public class DifficultyControl {

    private static int difficulty = 1;

    public static <T> T getValue(T easy, T medium, T hard) {
        return ((difficulty == 0) ? easy : ( (difficulty == 1) ? medium : hard));
    }

    public static int getDifficulty() { return difficulty; }

    public static void setDifficulty(int diff) { difficulty = diff; }
}
