package com.ducks.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import static com.ducks.DeltaDucks.batch;
import static com.ducks.managers.AssetManager.atlas;

public class Texture {

    public boolean angryFlashing = false;
    protected Color renderColor = Color.WHITE;
    float stateTime;
    TextureRegion frame;
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    private int colourTicks = 0;
    private Color flashingColor;
    private boolean rotate = false;
    private float rotationFrequency = 1f;
    private float rotationDegrees;
    private float currentRotation = 0f;

    /**
     * Creating new texture
     * @param name of texture
     * @param pos of texture
     * @param radius of texture
     * @param rotationDegrees of texture
     * @param rotationFrequency speed of rotation
     */
    public Texture(String name, Vector2 pos, float radius, float rotationDegrees, float rotationFrequency) {
        rotate = rotationDegrees != 0;
        this.rotationDegrees = rotationDegrees;
        this.rotationFrequency = rotationFrequency;

        frame = atlas.findRegion(name);

        width = height = radius * 2;
        x = pos.x - this.width/2;
        y = pos.y - this.height/2;
    }

    /**
     * Create new texture without rotation
     * @param name of texture
     * @param pos of texture
     * @param radius of texture
     */
    public Texture(String name, Vector2 pos, float radius) {
        this(name, pos, radius, 0f, 0f);
    }

    public Texture() {
    }

    /**
     * Update texture
     * @param deltaTime of game
     * @param pos of texture
     */
    public void update(float deltaTime, Vector2 pos) {
        stateTime += deltaTime;
        updatePosition(pos);
        updateRenderColor();
    }

    /**
     * Set new texture position
     * @param pos of texture
     */
    public void updatePosition(Vector2 pos) {
        x = pos.x - width/2;
        y = pos.y - height/2;
    }

    /**
     * Renders texture
     */
    public void render() {
        batch.setColor(renderColor);
        if (!rotate) {
            batch.draw(this.frame, this.x, this.y, width, height);
        } else {
            updateRotationDegrees();
            batch.draw(this.frame, this.x, this.y, width/2, height/2, width, height,
                    1f, 1f, -currentRotation);
        }
        batch.setColor(Color.WHITE);
    }

    /**
     * Sets the rotation
     */
    private void updateRotationDegrees() {
        if (stateTime > rotationFrequency) {
            stateTime = 0;
            currentRotation += rotationDegrees;
            currentRotation = currentRotation == 360 ? 0 : currentRotation;
        }
    }

    /**
     * Updates color filter
     */
    public void updateRenderColor() {
        if (angryFlashing) {
            colourTicks++;
            if (colourTicks % 13 == 0) {
                renderColor = flashingColor;
            } else if (colourTicks % 30 == 0) {
                renderColor = Color.WHITE;
                colourTicks = 0;
            }
        }
    }

    public void changeSize (float multiplier) {
        width = width * multiplier;
        height = height * multiplier;
    }

    public void setFlashingColor(Color color) {
        flashingColor = color;
        angryFlashing = true;
    }

    public void setColor(float r, float g, float b, float a) {
        setColor(new Color(r, g, b, a));
    }

    public void setColor(Color color) {
        renderColor = color;
    }

    public void removeColor() {
        renderColor = Color.WHITE;
    }

    public Vector2 getPosition() {
        return new Vector2(x, y);
    }
    public void setPosition(Vector2 pos) {
        x = pos.x;
        y = pos.y;
    }
}
