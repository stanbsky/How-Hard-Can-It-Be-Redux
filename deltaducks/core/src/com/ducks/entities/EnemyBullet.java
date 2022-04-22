package com.ducks.entities;

import com.badlogic.gdx.math.Vector2;
import com.ducks.components.Texture;
import com.ducks.intangibles.EntityData;
import com.ducks.ui.Hud;

import static com.ducks.tools.FixtureFilter.*;
public class EnemyBullet extends Bullet {

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
        final float BULLET_SPEED = 130f;
        rigidBody.applyForce(direction, BULLET_SPEED);
    }

    @Override
    public void dispose() {
        super.dispose();
        Hud.addScore(50);
    }
}
