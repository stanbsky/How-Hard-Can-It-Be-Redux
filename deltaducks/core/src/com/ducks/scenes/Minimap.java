package com.ducks.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ducks.DeltaDucks;

import java.awt.image.BufferedImage;

public class Minimap {
    double ratio;

    Rectangle cameraViewBounds;
    BufferedImage mapImage;
    Color color;

    OrthographicCamera gameCam;
    Pixmap pixmap;

    public Minimap(OrthographicCamera gameCam) {
        this.gameCam = gameCam;
        pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        color = new Color(0.1f, 0.1f, 0.1f, .3f);
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, 1, 1);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(new Texture(pixmap), gameCam.position.x - gameCam.viewportWidth/2, gameCam.position.y  - gameCam.viewportHeight/2);
    }
}
