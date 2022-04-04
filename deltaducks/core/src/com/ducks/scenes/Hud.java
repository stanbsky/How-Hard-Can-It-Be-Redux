package com.ducks.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ducks.DeltaDucks;
import com.ducks.components.HealthBar;
import com.ducks.screens.MainGameScreen;

/***
 * HUD for the game
 */
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
    private HealthBar hpBar;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    /**
     * Constructor
     * @param batch to draw on the screen
     */
    public Hud(SpriteBatch batch) {
        worldTimer = 300;
        timeCount = 0;
        score = 0;
        gold = 0;
        health = 1f;

        hpBar = new HealthBar(173, 0,5f,172f,false, health, true);
        viewport = new FitViewport(DeltaDucks.VIRTUAL_WIDTH, DeltaDucks.VIRTUAL_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/futur.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;

        BitmapFont font = generator.generateFont(parameter);
        font.getData().setScale(.5f);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        expTagLabel = new Label("USER EXP", new Label.LabelStyle(font, Color.WHITE));
        expLabel = new Label(String.format("%06d", score), new Label.LabelStyle(font, Color.WHITE));

        goldTagLabel = new Label("GOLD", new Label.LabelStyle(font, Color.WHITE));
        goldLabel = new Label(String.format("%05d", gold), new Label.LabelStyle(font, Color.WHITE));

        timeLabel = new Label("TIME", new Label.LabelStyle(font, Color.WHITE));
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(font, Color.WHITE));

        table.add(expTagLabel).expandX().padTop(10);
        table.add(goldTagLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(expLabel).expandX();
        table.add(goldLabel).expandX();
        table.add(countdownLabel).expandX();
        stage.addActor(table);
    }

    /**
     * Draw the UI and health bar (HUD) every delta time interval
     * @param batch to draw on the screen
     */
    public void draw(SpriteBatch batch) {
        batch.setProjectionMatrix(stage.getCamera().combined);
        stage.draw();
        batch.begin();
//        Texture healthBar = MainGameScreen.resources.getTexture("blank");
//        if (health > .6f)
//            batch.setColor(Color.GREEN);
//        else if (health > .2f)
//            batch.setColor(Color.ORANGE);
//        else
//            batch.setColor(Color.RED);
//        batch.draw(healthBar, 173, 0, 5f, 160 * health + 12f);
//        batch.setColor(Color.WHITE);
        this.hpBar.render(batch);
        batch.end();
    }

    /**
     * Update the HUD every delta time interval
     * @param deltaTime
     */
    public void update(float deltaTime) {
        timeCount += deltaTime;
        hpBar.update(health);
        if(timeCount >= 1) {
            worldTimer--;
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }

    /**
     * Increase the score with certain value
     * @param value to add
     */
    public static void addScore(int value) {
        score += value;
        expLabel.setText(String.format("%06d", score));
    }

    /**
     * Increase the gold with certain value
     * @param value to add
     */
    public static void addGold(int value) {
        gold += value;
        goldLabel.setText(String.format("%06d", gold));
    }

    /**
     * Get the value of gold
     * @return gold
     */
    public static int getGold(){
        return gold;
    }

    /**
     * Get the value of score
     * @return score
     */
    public static int getScore(){
        return score;
    }

    /**
     * Get the value of health
     * @return health
     */
    public static float getHealth() {
        return health;
    }

    /**
     * Decrease the health (damage)
     */
    public static void decHealth() {
        health-=.2f;
    }

    /**
     * Get the world time life (clock)
     * @return time
     */
    public float getTimer() {
        return worldTimer;
    }

    /**
     * Dispose the unwanted object
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}
