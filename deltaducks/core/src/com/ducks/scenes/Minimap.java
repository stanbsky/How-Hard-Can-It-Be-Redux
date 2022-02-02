package com.ducks.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Disposable;
import com.ducks.DeltaDucks;
import com.ducks.entities.ListOfCannons;
import com.ducks.entities.ListOfColleges;
import com.ducks.sprites.College;

public class Minimap implements Disposable {
    private float ratio = .045f;

    public OrthographicCamera gameCam;
    private Pixmap pixmap;

    private float map_width;
    private float map_height;

    public Minimap(OrthographicCamera gameCam, int width, int height) {
        this.gameCam = gameCam;
        map_width = width / DeltaDucks.PIXEL_PER_METER;
        map_height = height / DeltaDucks.PIXEL_PER_METER;
    }

    public void update(Body b2body, ListOfColleges colleges) {
        int width = Math.round(map_width * ratio * DeltaDucks.PIXEL_PER_METER * .3f);
        int height = Math.round(map_height * ratio * DeltaDucks.PIXEL_PER_METER * .3f);

        int outline_width = Math.round(gameCam.viewportWidth * ratio * DeltaDucks.PIXEL_PER_METER);
        int outline_height = Math.round(gameCam.viewportHeight * ratio * DeltaDucks.PIXEL_PER_METER);
        int outline_x = Math.round((gameCam.position.x - gameCam.viewportWidth/2) * ratio * DeltaDucks.PIXEL_PER_METER);
        int outline_y = height - Math.round((gameCam.position.y) * ratio * DeltaDucks.PIXEL_PER_METER) - outline_height/2;

        int player_radius = 4;
        int player_x = Math.round(b2body.getPosition().x * ratio * DeltaDucks.PIXEL_PER_METER) + player_radius/2;
        int player_y = height - Math.round(b2body.getPosition().y * ratio * DeltaDucks.PIXEL_PER_METER) - player_radius/2;

        pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);

        pixmap.setColor(new Color(0.1f, 0.1f, 0.1f, .3f));
        pixmap.fillRectangle(0, 0, width, height);

        pixmap.setColor(new Color(1f, 1f, 1f, .8f));
        pixmap.drawRectangle(outline_x, outline_y, outline_width, outline_height);

        pixmap.setColor(new Color(.1f, 0.1f, 1f, .3f));
        pixmap.fillCircle(player_x, player_y, player_radius);

        int counter=0;
        for(Vector2 coordinates : colleges.getCoordinates()) {
            if(colleges.collegeBodies.get(counter++).health>0f) {
                pixmap.setColor(new Color(1f, 0.1f, 0.1f, .3f));
                int coord_x = Math.round(coordinates.x * ratio *  DeltaDucks.PIXEL_PER_METER);
                int coord_y = height - Math.round(coordinates.y * ratio *  DeltaDucks.PIXEL_PER_METER);
                pixmap.fillCircle(coord_x, coord_y, player_radius);
            }
        }
    }
    public void draw(SpriteBatch batch) {
        batch.draw(new Texture(pixmap), gameCam.position.x - gameCam.viewportWidth/2, gameCam.position.y  - gameCam.viewportHeight/2, pixmap.getWidth() / DeltaDucks.PIXEL_PER_METER, pixmap.getHeight() / DeltaDucks.PIXEL_PER_METER);
    }

    @Override
    public void dispose() {
        pixmap.dispose();
    }
}
