package com.ducks.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import static com.ducks.tools.InputParser.Direction.*;

import java.util.ArrayList;

public final class InputParser {

    public enum Direction {
        NORTH, EAST, SOUTH, WEST;
    }

    public static ArrayList<Direction> parseInput() {
        ArrayList<Direction> directions = new ArrayList<>();
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            directions.add(NORTH);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            directions.add(SOUTH);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            directions.add(WEST);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            directions.add(EAST);
        }
        return directions;
    }

    public static ArrayList<Direction> fakeInput(float cutoff) {
        ArrayList<Direction> directions = new ArrayList<>();
        if (Math.random() > cutoff) {
            directions.add(NORTH);
        }
        if (Math.random() > cutoff) {
            directions.add(SOUTH);
        }
        if (Math.random() > cutoff) {
            directions.add(WEST);
        }
        if (Math.random() > cutoff) {
            directions.add(EAST);
        }
        return directions;
    }
}
