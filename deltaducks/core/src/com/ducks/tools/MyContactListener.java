package com.ducks.tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.ducks.DeltaDucks;
import com.ducks.scenes.Subtitle;
import com.ducks.sprites.Ship;

public class MyContactListener implements ContactListener {

    private boolean playerHitsGround;
    Ship player;
    Subtitle subtitle;

    public MyContactListener(Ship player, Subtitle subtitle) {
        this.player = player;
        this.subtitle = subtitle;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        System.out.println(fa.getUserData()+", "+fb.getUserData());
        if(fa.getUserData() != null && fa.getUserData().equals("Sensor")){
            System.out.println("Sensor Fa");
            playerHitsGround = true;
        }
        if(fb.getUserData() != null && fb.getUserData().equals("Sensor")){
            System.out.println("Sensor Fb");
            playerHitsGround = true;
        }

        if(checkCollision(fa, fb, "Player", "Monster Sensor")) {

            player.b2body.applyLinearImpulse(new Vector2(
                    player.b2body.getPosition().x - 10 / DeltaDucks.PIXEL_PER_METER, player.b2body.getPosition().y - 32*4 / DeltaDucks.PIXEL_PER_METER
            ), player.b2body.getWorldCenter(), true);
        }

        if(checkCollision(fa, fb, "Player", "College Sensor")) {
            if(fa.getUserData() != null && fa.getUserData().equals("College Sensor")){
                fa.setUserData("College Sensor Attack");
            } else {
                fb.setUserData("College Sensor Attack");
            }
            subtitle.setSubtitle("Derwent College");
        }

        if(checkCollision(fa, fb, "Pirate", "Bullet Alive")) {
            if(fa.getUserData() != null && fa.getUserData().equals("Pirate")){
                fa.setUserData("Pirate Dead");
            } else {
                fb.setUserData("Pirate Dead");
            }
        }

        if(fa.getUserData() != null && fa.getUserData().equals("Bullet Alive")){
            fa.setUserData("Bullet Dead");
        }
        if(fb.getUserData() != null && fb.getUserData().equals("Bullet Alive")){
            fb.setUserData("Bullet Dead");
        }

        if(fa.getUserData() != null && fa.getUserData().equals("Cannon Alive")){
            fa.setUserData("Cannon Dead");
        }
        if(fb.getUserData() != null && fb.getUserData().equals("Cannon Alive")){
            fb.setUserData("Cannon Dead");
        }

    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(checkCollision(fa, fb, "Player", "College Sensor Attack")) {
            if(fa.getUserData() != null && fa.getUserData().equals("College Sensor Attack")){
                fa.setUserData("College Sensor");
            } else {
                fb.setUserData("College Sensor");
            }
            subtitle.removeSubtitle();
        }

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

    public boolean checkCollision(Fixture fa, Fixture fb, String A, String B) {
        return  ( (fa.getUserData() != null && fa.getUserData().equals(A))
                || (fb.getUserData() != null && fb.getUserData().equals(A)) )
                &&
                ( (fa.getUserData() != null && fa.getUserData().equals(B))
                || (fb.getUserData() != null && fb.getUserData().equals(B)) );
    }

}
