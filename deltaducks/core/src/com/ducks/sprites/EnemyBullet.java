package com.ducks.sprites;

import com.badlogic.gdx.math.Vector2;
import com.ducks.DeltaDucks;
import com.ducks.components.Texture;
import static com.ducks.tools.FixtureFilter.*;
public class EnemyBullet extends Bullet {

    /**
     * Constructor
     */
    public EnemyBullet(Vector2 position, Vector2 direction) {
        this.texture = new Texture("bullet_college", position, radius);
        this.category = ENEMY_BULLET;
        this.mask = MASK_ALL - ENEMY;
        defineBullet(position);
        setData("Cannon Alive");
        final float BULLET_SPEED = 130f;
        this.rigidBody.applyForce(direction, BULLET_SPEED);
    }
}
