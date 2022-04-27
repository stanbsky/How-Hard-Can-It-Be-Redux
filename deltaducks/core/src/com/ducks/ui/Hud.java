package com.ducks.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ducks.DeltaDucks;
import com.ducks.components.HealthBar;
import com.ducks.entities.Player;
import com.ducks.intangibles.DifficultyControl;

import static com.ducks.DeltaDucks.batch;
import static com.ducks.managers.AssetManager.ui;

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

    private static Label countdownLabel;
    private static Image expSymbol;
    private static Label expLabel;
    private static Image timeSymbol;
    private static Label timeLabel;
    private static Image goldSymbol;
    private static Label goldTagLabel;
    private static Label goldLabel;
    private static Label expTagLabel;

    private HealthBar hpBar;

    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    /**
     * Constructor
     */
    public Hud() {
        worldTimer = 300;
        timeCount = 0;
        score = 0;
        gold = 0;

        hpBar = new HealthBar(173, 0,5f,172f,false, Player.getHealth(), true);
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
        table.defaults().expandX().padTop(10);

        expSymbol = new Image(ui.newDrawable("trophy"));
        expTagLabel = new Label("USER EXP", new Label.LabelStyle(font, Color.WHITE));
        expLabel = new Label(String.format("%d", score), new Label.LabelStyle(font, Color.WHITE));

        goldSymbol = new Image(ui.newDrawable("coin2"));
        goldTagLabel = new Label("GOLD", new Label.LabelStyle(font, Color.WHITE));
        goldLabel = new Label(String.format("%d", gold), new Label.LabelStyle(font, Color.WHITE));

        timeSymbol = new Image(ui.newDrawable("time"));
        timeLabel = new Label("TIME", new Label.LabelStyle(font, Color.WHITE));
        countdownLabel = new Label(String.format("%d", worldTimer), new Label.LabelStyle(font, Color.WHITE));

        // Exp

        Table expTable = new Table();
        expTable.add(expTagLabel).colspan(2);
        expTable.row();
        expTable.add(expLabel);

        Table expTable2 = new Table();
        expTable2.defaults().size(50);
        expTable2.add(expSymbol).padRight(4);
        expTable2.add(expTable);

        // Gold

        Table goldTable = new Table();
        goldTable.add(goldTagLabel).colspan(2);
        goldTable.row();
        goldTable.add(goldLabel);

        Table goldTable2 = new Table();
        goldTable2.defaults().size(50);
        goldTable2.add(goldSymbol);
        goldTable2.add(goldTable);

        // Time

        Table timeTable = new Table();
        timeTable.add(timeLabel).colspan(2);
        timeTable.row();
        timeTable.add(countdownLabel);

        Table timeTable2 = new Table();
        timeTable2.defaults().size(50);
        timeTable2.add(timeSymbol);
        timeTable2.add(timeTable);

        table.add(expTable2);
        table.add(goldTable2);
        table.add(timeTable2);

        stage.addActor(table);
    }

    /**
     * Draw the UI and health bar (HUD) every delta time interval
     */
    public void draw() {
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
        this.hpBar.render();
        batch.end();
    }

    /**
     * Update the HUD every delta time interval
     * @param deltaTime
     */
    public void update(float deltaTime) {
        timeCount += deltaTime;
        hpBar.update(Player.getHealth());
        if(timeCount >= 1) {
            worldTimer--;
            countdownLabel.setText(String.format("%d", worldTimer));
            timeCount = 0;
        }
    }

    /**
     * Increase the score with certain value
     * @param value to add
     */
    public static void addScore(int value) {
        score += value;
        expLabel.setText(String.format("%d", score));
    }

    /**
     * Increase the gold with certain value
     * @param value to add
     */
    public static void addGold(int value) {
        gold += value;
        goldLabel.setText(String.format("%d", gold));
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
//    public static float getHealth() {
//        return health;
//    }

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
