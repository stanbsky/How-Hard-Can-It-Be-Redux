package com.ducks.intangibles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import static com.ducks.DeltaDucks.batch;
import static com.ducks.managers.AssetManager.atlas;

public class ParticleTest {

    ParticleEffectPool rainEffectPool;
    Array<ParticleEffectPool.PooledEffect> effects = new Array();

    public ParticleTest(Vector2 location) {
        ParticleEffect rainEffect = new ParticleEffect();
        rainEffect.load(Gdx.files.internal("particles/pp_rain-cinematic.p"),
                atlas);

        rainEffect.setEmittersCleanUpBlendFunction(false);

        rainEffectPool = new ParticleEffectPool(rainEffect, 500, 1000);

        ParticleEffectPool.PooledEffect effect = rainEffectPool.obtain();
        effect.setPosition(location.x, location.y);
//        effect.scaleEffect(0.7f);
        effects.add(effect);
        System.out.println(location);
    }

    public void draw(float deltaTime) {
        batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
        // Update and draw effects:
        for (int i = effects.size - 1; i >= 0; i--) {
            ParticleEffectPool.PooledEffect effect = effects.get(i);
            effect.draw(batch, deltaTime);
            if (effect.isComplete()) {
                effect.free();
                effects.removeIndex(i);
            }
        }
    }
}
