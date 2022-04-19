package com.ducks.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ducks.tools.IDrawable;
import java.util.ArrayList;

public final class RenderingManager {
    public static SpriteBatch batch;
    public static ArrayList<IDrawable> entities;

    public static void Initialize() {
        entities = new ArrayList<>();
    }

    public static int registerEntity(IDrawable entity) {
        entities.add(entity);
        return entities.size() - 1;
    }

    public static void removeEntity(int entityId) {
        entities.remove(entityId);
    }

    public static void render() {
        for (IDrawable Entity : entities) {
            Entity.draw();
        }
    }

    public static void update(float deltaTime) {
        for (IDrawable Entity : entities) {
            Entity.update(deltaTime);
        }
    }
}
