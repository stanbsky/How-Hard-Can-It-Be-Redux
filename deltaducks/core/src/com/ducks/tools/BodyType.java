package com.ducks.tools;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

public enum BodyType {
    /**
     * Has infinite mass doesn't move at all
     */
    Static(BodyDef.BodyType.StaticBody),
    /**
     * Effected by all forces (like every thing in real life)
     */
    Dynamic(BodyDef.BodyType.DynamicBody),
    /**
     * Not effected by forces but can move
     */
    Kinematic(BodyDef.BodyType.KinematicBody);

    private BodyDef.BodyType type;

    public BodyDef.BodyType getType() {
        return this.type;
    }

    private BodyType(BodyDef.BodyType type) {
        this.type = type;
    }
}
