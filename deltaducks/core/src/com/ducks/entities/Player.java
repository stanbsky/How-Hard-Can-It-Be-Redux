package com.ducks.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.ducks.screens.MainGameScreen;

public class Player extends B2DSprite {

    private int numCrystals;
    private int totalCrystals;

    public Player(Body body) {
        super(body);

        MainGameScreen.resources.loadTexture("bunny.png", "badlogic");
        Texture texture = MainGameScreen.resources.getTexture("badlogic");

        TextureRegion[] sprites = TextureRegion.split(texture, 32, 32)[0];

        setAnimation(sprites, 1/12f);
    }

    public void collectCrystal(){ numCrystals++; }
    public int getNumCrystals(){ return numCrystals;}
    public void setTotalCrystals(int i) {totalCrystals=i;}
    public int getTotalCrystals() { return totalCrystals;}
}
