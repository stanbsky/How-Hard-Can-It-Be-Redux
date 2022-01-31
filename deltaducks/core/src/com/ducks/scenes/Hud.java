package com.ducks.scenes;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ducks.DeltaDucks;
import com.ducks.screens.MainGameScreen;

public class Hud implements Disposable {
    public Stage stage;
    public Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private static Integer score;
    private static Integer gold;

    private Label countdownLabel;
    private static Label expLabel;
    private Label timeLabel;
    private Label goldTagLabel;
    private static Label goldLabel;
    private Label expTagLabel;

    private static float health;

    public Hud(SpriteBatch batch) {
        worldTimer = 300;
        timeCount = 0;
        score = 0;
        gold = 0;
        health = 1f;

        viewport = new FitViewport(DeltaDucks.VIRTUAL_WIDTH, DeltaDucks.VIRTUAL_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        expTagLabel = new Label("USER EXP", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        expLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        goldTagLabel = new Label("GOLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        goldLabel = new Label(String.format("%05d", gold), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(expTagLabel).expandX().padTop(10);
        table.add(goldTagLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(expLabel).expandX();
        table.add(goldLabel).expandX();
        table.add(countdownLabel).expandX();
        stage.addActor(table);
    }

    public void draw(SpriteBatch batch) {
        batch.setProjectionMatrix(stage.getCamera().combined);
        stage.draw();
        batch.begin();
        Texture healthBar = MainGameScreen.resources.getTexture("blank");
        if (health > .6f)
            batch.setColor(Color.GREEN);
        else if (health > .2f)
            batch.setColor(Color.ORANGE);
        else
            batch.setColor(Color.RED);
        batch.draw(healthBar, 160, 0, 5f, 150 * health + 10f);
        batch.setColor(Color.WHITE);
        batch.end();
    }

    public void update(float deltaTime) {
        timeCount += deltaTime;
        if(timeCount >= 1) {
            worldTimer--;
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }

    public static void addScore(int value) {
        score += value;
        expLabel.setText(String.format("%06d", score));
    }

    public static void addGold(int value) {
        gold += value;
        goldLabel.setText(String.format("%06d", gold));
    }

    public static float getHealth() {
        return health;
    }

    public static void decHealth() {
        health-=.2f;
    }


    public float getTimer() {
        return worldTimer;
    }
    @Override
    public void dispose() {
        stage.dispose();
    }
}
