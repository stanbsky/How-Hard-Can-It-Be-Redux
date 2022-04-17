package com.ducks.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ducks.entities.Entity;

import java.util.ArrayList;

public final class RenderingManager {
    public static SpriteBatch batch;
    public static ArrayList<Entity> entities;

    public static void Initialize(SpriteBatch spriteBatch) {
        batch = spriteBatch;
        entities = new ArrayList<>();
    }

    public static int registerEntity(Entity entity) {
        entities.add(entity);
        return entities.size() - 1;
    }

    public static void removeEntity(int entityId) {
        entities.remove(entityId);
    }

    public static void render() {
        for (Entity entity : entities) {
            entity.draw(batch);
        }
    }
}
