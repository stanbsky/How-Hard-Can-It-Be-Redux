package com.ducks.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ducks.tools.CollisionRect;

public class Bullet {

    public static final int SPEED = 500;
    public static final int DEFAULT_Y = 40;
    public static final int WIDTH = 3;
    public static final int HEIGHT = 12;

    private static Texture texture;

    float x, y;
    int state;

    CollisionRect rect;

    public boolean remove = false;

    public Bullet (float x, float y, int state) {
        this.x = x;
        this.y = y;
        this.state = state;

        this.rect = new CollisionRect(x, y, WIDTH, HEIGHT);

        if (texture == null) {
            texture = new Texture("game/bullet.png");
        }
    }

    public void update(float deltaTime) {
        if (state == 0) { // North (Up)
            y += SPEED * deltaTime;
        } else if (state == 1) { // South (Down)
            y -= SPEED * deltaTime;
        } else if (state == 2) { // East (Right)
            x += SPEED * deltaTime;
        } else if (state == 3) { // West (Left)
            x -= SPEED * deltaTime;
        } else if (state == 4) { // NorthEast
            x += SPEED * deltaTime;
            y += SPEED * deltaTime;
        } else if (state == 5) { // NorthWest
            x -= SPEED * deltaTime;
            y += SPEED * deltaTime;
        } else if (state == 6) { // SouthEast
            x += SPEED * deltaTime;
            y -= SPEED * deltaTime;
        } else if (state == 7) { // SouthWest
            x -= SPEED * deltaTime;
            y -= SPEED * deltaTime;
        }
        if (y > Gdx.graphics.getHeight() || x > Gdx.graphics.getWidth() || y + HEIGHT < 0 || x + WIDTH < 0) {
            remove = true;
        }
        rect.move(x, y);
    }

    public void render(SpriteBatch batch) {
        float rotation = 0f;
        if (state == 2 || state == 3)
            rotation = 90f;
        if (state == 4 || state == 7)
            rotation = 135f;
        if (state == 5 || state == 6)
            rotation = 45f;

        batch.draw(texture, x, y, 0, 0, WIDTH, HEIGHT, 1, 1, rotation, 1, 1, WIDTH, HEIGHT, false, false);

    }

    public CollisionRect getCollisionRect () {
        return rect;
    }

}
