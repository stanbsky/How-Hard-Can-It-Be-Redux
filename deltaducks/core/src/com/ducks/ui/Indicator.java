package com.ducks.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.ducks.components.Shooter;
import com.ducks.components.Texture;
import com.ducks.entities.Entity;
import com.ducks.screens.MainGameScreen;
import com.ducks.tools.IDrawable;

import static com.ducks.DeltaDucks.scl;
import static com.ducks.screens.MainGameScreen.player;

public class Indicator implements IDrawable {

    private Texture texture;
    private Vector2 position;
    protected Entity target;
    private static Vector2 direction;
    private boolean cleanup = false;
    private boolean isAngry = false;

    private final float crosshairRadius = 1.6f;
    private int midX = Gdx.graphics.getWidth()/2;
    private int midY = Gdx.graphics.getHeight()/2;

    public Indicator(Entity target, String texture, float scale) {
        this.target = target;
        position = new Vector2(0, 0);
        this.texture = new Texture(texture, position, scl(scale));
    }

    public Indicator(Entity target, String texture) {
        this(target, texture, 25.6f);
    }

    public void update(float deltaTime) {
        updatePosition();
        this.texture.update(deltaTime, position);
    }

    public void draw() {
        this.texture.render();
    }

    public void dispose() {
        cleanup = true;
    }

    public boolean cleanup() { return cleanup; }

    public void setAngry(boolean status) {
        isAngry = status;
        texture.setFlashingColor(Color.CORAL);
    }

    /**
     * Transforms player's mouse location into the coordinates where the crosshair must be drawn
     */
    public void updatePosition() {
        // Get unit vector pointing to target
        direction = Shooter.getDirection(player, target);
        // Scale to fit the indicator distance from player
        direction = direction.scl(1f * crosshairRadius);
        // Offset the radius by the size of the ship body
        position.x = player.getPosition().x + direction.x;
        position.y = player.getPosition().y + direction.y;
    }
}
