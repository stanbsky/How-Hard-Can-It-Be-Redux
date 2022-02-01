package com.ducks.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

public class Subtitle implements Disposable {
    public Stage stage;
    public Viewport viewport;

    private String subtitle;
    private Label subtitleLabel;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    private float stateTimer;
    private float tolerateTime;
    private int state;

    public Subtitle(SpriteBatch batch) {

        viewport = new FitViewport(DeltaDucks.VIRTUAL_WIDTH, DeltaDucks.VIRTUAL_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/OpenSans-Regular.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;

        BitmapFont font = generator.generateFont(parameter);
        font.getData().setScale(.5f);
        subtitleLabel = new Label(subtitle, new Label.LabelStyle( font , Color.BLACK));


        table.add(subtitleLabel).expandX().padTop(DeltaDucks.VIRTUAL_HEIGHT * .9f);
//        table.row();
//        table.add(timeLabel).expandX();

        stage.addActor(table);

        tolerateTime = 5;
    }

    public void update(float deltaTime) {
        stateTimer += deltaTime;
        if(stateTimer >= tolerateTime) {
            stateTimer = 0;
            state++;
        }
        switch (state) {
            case 0:
                subtitle = "use W A S D to move around";
                subtitleLabel.setText(subtitle);
                break;
            case 1:
                subtitle = "use mouse to move around the cursor";
                subtitleLabel.setText(subtitle);
                break;
            case 2:
                subtitle = "use left mouse click to shoot";
                subtitleLabel.setText(subtitle);
                break;
            case 3:
                subtitle = "fight the colleges to win";
                subtitleLabel.setText(subtitle);
                break;
            case 4:
                subtitle = "Good Luck!";
                subtitleLabel.setText(subtitle);
                break;
            case 5:
                subtitle = "";
                subtitleLabel.setText(subtitle);
                break;
            default:
                subtitleLabel.setText(subtitle);
                break;
        }
    }

    public void setSubtitle(String sub) {
        this.subtitle = sub;
        subtitleLabel.setText(subtitle);
    }

    public void removeSubtitle() {
        this.subtitle = "";
        subtitleLabel.setText(subtitle);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
