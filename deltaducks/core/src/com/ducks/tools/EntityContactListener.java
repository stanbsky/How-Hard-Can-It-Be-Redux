package com.ducks.tools;

import com.badlogic.gdx.physics.box2d.*;
import com.ducks.intangibles.EntityData;

public class EntityContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if (fa.getUserData() != null)
            ((EntityData) fa.getUserData()).addContact(fb);
        if (fb.getUserData() != null)
            ((EntityData) fb.getUserData()).addContact(fa);
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if (fa.getUserData() != null)
            ((EntityData) fa.getUserData()).addContact(fb);
        if (fb.getUserData() != null)
            ((EntityData) fb.getUserData()).addContact(fa);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
