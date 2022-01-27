package com.ducks.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ducks.DeltaDucks;

import java.awt.image.BufferedImage;

public class Minimap {
    private float ratio = .1f;

    public OrthographicCamera gameCam;
    private Pixmap pixmap;

    private float map_width;
    private float map_height;

    public Minimap(OrthographicCamera gameCam, int width, int height) {
        this.gameCam = gameCam;
        map_width = width / DeltaDucks.PIXEL_PER_METER;
        map_height = height / DeltaDucks.PIXEL_PER_METER;
    }

    public void update(Body b2body) {
        int width = Math.round(map_width * ratio * DeltaDucks.PIXEL_PER_METER);
        int height = Math.round(map_height * ratio * DeltaDucks.PIXEL_PER_METER);

        int outline_width = Math.round(gameCam.viewportWidth * ratio * DeltaDucks.PIXEL_PER_METER);
        int outline_height = Math.round(gameCam.viewportHeight * ratio * DeltaDucks.PIXEL_PER_METER);
        int outline_x = Math.round((gameCam.position.x - gameCam.viewportWidth/2) * ratio * DeltaDucks.PIXEL_PER_METER);
        int outline_y = height - Math.round((gameCam.position.y) * ratio * DeltaDucks.PIXEL_PER_METER) - outline_height/2;

        int player_radius = 5;
        int player_x = Math.round(b2body.getPosition().x * ratio * DeltaDucks.PIXEL_PER_METER) + player_radius/2;
        int player_y = height - Math.round(b2body.getPosition().y * ratio * DeltaDucks.PIXEL_PER_METER) - player_radius/2;

        pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

        pixmap.setColor(new Color(0.1f, 0.1f, 0.1f, .3f));
        pixmap.fillRectangle(0, 0, width, height);

        pixmap.setColor(new Color(1f, 1f, 1f, .8f));
        pixmap.drawRectangle(outline_x, outline_y, outline_width, outline_height);

        pixmap.setColor(new Color(1f, 0.1f, 0.1f, .3f));
        pixmap.fillCircle(player_x, player_y, player_radius);
    }
    public void draw(SpriteBatch batch) {
        batch.draw(new Texture(pixmap), gameCam.position.x - gameCam.viewportWidth/2, gameCam.position.y  - gameCam.viewportHeight/2, pixmap.getWidth() / DeltaDucks.PIXEL_PER_METER, pixmap.getHeight() / DeltaDucks.PIXEL_PER_METER);
    }
}
