package com.ducks.entities;

import com.badlogic.gdx.math.Vector2;
import com.ducks.components.Texture;
import com.ducks.intangibles.DifficultyControl;
import com.ducks.intangibles.EntityData;
import com.ducks.managers.StatsManager;

import static com.ducks.tools.FixtureFilter.*;
public class EnemyBullet extends Bullet {

    public EnemyBullet(Vector2 position, Vector2 direction, float offset) {
        this(position, direction.cpy().rotateDeg(offset));
        System.out.println(direction.cpy().rotateDeg(offset));
    }

    /**
     * Constructor
     */
    public EnemyBullet(Vector2 position, Vector2 direction) {
//        System.out.println(position);
        texture = new Texture("bullet_college", position, radius);
        category = ENEMY_BULLET;
        mask = MASK_ALL - ENEMY;
        data = new EntityData(category);
        defineBullet(position);
        setData(data);
        final float BULLET_SPEED = DifficultyControl.getValue(120f, 160f, 200f);
        rigidBody.applyForce(direction, BULLET_SPEED);
    }

    @Override
    public void dispose() {
        super.dispose();
        StatsManager.addScore(50);
    }
}
