package com.ducks.tools;

import com.badlogic.gdx.physics.box2d.*;
import com.ducks.intangibles.EntityData;

/**
 * Listens for contacts from box2d
 */
public class EntityContactListener implements ContactListener {
    /**
     * Called once collision is detected before resolution
     * @param contact the contact data
     */
    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if (fa.getUserData() != null)
            ((EntityData) fa.getUserData()).addContact(fb);
        if (fb.getUserData() != null)
            ((EntityData) fb.getUserData()).addContact(fa);
    }

    /**
     * Called once collision is detected after resolution
     * @param contact the contact data
     */
    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if (fa.getUserData() != null)
            ((EntityData) fa.getUserData()).addContact(fb);
        if (fb.getUserData() != null)
            ((EntityData) fb.getUserData()).addContact(fa);
    }

    /**
     * Called once collision is detected before solving
     * @param contact the contact data
     * @param oldManifold the collision manifold
     */
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    /**
     * Called once collision is detected after solving
     * @param contact the contact data
     * @param impulse the resolution infomation
     */
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
