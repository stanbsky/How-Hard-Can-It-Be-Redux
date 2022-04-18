package com.ducks.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.ducks.components.Texture;
import com.ducks.entities.Entity;
import com.ducks.screens.MainGameScreen;
import com.ducks.tools.Debug;
import com.ducks.tools.IDrawable;

import static com.ducks.DeltaDucks.scl;
import static com.ducks.screens.MainGameScreen.getPlayerPosition;

public class Indicator implements IDrawable {

    private Texture texture;
    private Vector2 position;
    protected TextureAtlas atlas;
    protected Entity target;
    private static Vector2 direction;

    private final float crosshairRadius = 1.6f;
    private int midX = Gdx.graphics.getWidth()/2;
    private int midY = Gdx.graphics.getHeight()/2;

    public Indicator(Entity target, String texture) {
        this(target, texture, MainGameScreen.getAtlas());
    }

    public Indicator(Entity target, String texture, TextureAtlas atlas) {
        this.atlas = atlas;
        this.target = target;
        position = new Vector2(0, 0);
        this.texture = new Texture(texture, position, scl(25.6f));
    }

    public void update(float deltaTime) {
        updatePosition();
        this.texture.update(deltaTime, position);
    }

    public void draw(SpriteBatch batch) {
        this.texture.render(batch);
    }

    /**
     * Transforms player's mouse location into the coordinates where the crosshair must be drawn
     */
    public void updatePosition() {
        midX = Gdx.graphics.getWidth()/2;
        midY = Gdx.graphics.getHeight()/2;
        Debug.debug(target.getPosition().scl(100f));
        // Transform mouse coordinates into world coordinates
        float x = midX - target.getPosition().scl(100f).x;
        float y = -midY + target.getPosition().scl(100f).y;
        // Scale coordinates to fit the radius around the ship
        direction = new Vector2(x, y).nor().scl(1f * crosshairRadius);
        Debug.debug(direction);
        // Offset the radius by the size of the ship body
        position.x = getPlayerPosition().x + direction.x;
        position.y = getPlayerPosition().y + direction.y;
        Debug.debug(position);
    }
}
