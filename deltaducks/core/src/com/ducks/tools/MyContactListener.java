package com.ducks.tools;

import com.badlogic.gdx.physics.box2d.*;

public class MyContactListener implements ContactListener {

    private boolean playerHitsGround;

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

//        System.out.println(fa.getUserData()+", "+fb.getUserData());
        if(fa.getUserData() != null && fa.getUserData().equals("Sensor")){
            System.out.println("Sensor Fa");
            playerHitsGround = true;
        }
        if(fb.getUserData() != null && fb.getUserData().equals("Sensor")){
            System.out.println("Sensor Fb");
            playerHitsGround = true;

        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

//        System.out.println(fa.getUserData()+", "+fb.getUserData());
        if(fa.getUserData() != null && fa.getUserData().equals("Sensor")){
            System.out.println("Sensor Fa");
            playerHitsGround = false;
        }
        if(fb.getUserData() != null && fb.getUserData().equals("Sensor")){
            System.out.println("Sensor Fb");
            playerHitsGround = false;

        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean isPlayerHitsGround() {
        return playerHitsGround;
    }
}
