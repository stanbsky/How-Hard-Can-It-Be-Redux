package com.ducks.tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.ducks.DeltaDucks;
import com.ducks.scenes.Hud;
import com.ducks.scenes.Subtitle;
import com.ducks.sprites.Ship;

public class MyContactListener implements ContactListener {

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

//        System.out.println(fa.getUserData()+", "+fb.getUserData());

        if(checkCollision(fa, fb, "Player", "Monster Sensor")) {
            player.b2body.applyLinearImpulse(new Vector2(
                    player.b2body.getPosition().x - 10 / DeltaDucks.PIXEL_PER_METER, player.b2body.getPosition().y - 32*4 / DeltaDucks.PIXEL_PER_METER
            ), player.b2body.getWorldCenter(), true);
        }

        if(checkCollision(fa, fb, "Player", "College Sensor")) {
            if(checkFixture(fa, "College Sensor")){
                fa.setUserData("College Sensor Attack");
            } else {
                fb.setUserData("College Sensor Attack");
            }
            subtitle.setSubtitle("Attack The College");
        }

        if(checkCollision(fa, fb, "Pirate", "Bullet Alive")) {
            if(checkFixture(fa, "Pirate")){
                fa.setUserData("Pirate Dead");
            } else {
                fb.setUserData("Pirate Dead");
            }
        }
        if(checkCollision(fa, fb, "College", "Bullet Alive")) {
            if(checkFixture(fa, "Bullet Alive")){
                fa.setUserData("Bullet Dead");
            } else {
                fb.setUserData("Bullet Dead");
            }
            if(checkFixture(fa, "College")){
                fa.setUserData("College Damage");
            } else {
                fb.setUserData("College Damage");
            }
        }


        if(checkCollision(fa, fb, "Player", "Cannon Alive")) {
            Hud.decHealth();
        }

        if(checkFixture(fa, "Cannon Alive")){
            fa.setUserData("Cannon Dead");
        }
        if(checkFixture(fb, "Cannon Alive")){
            fb.setUserData("Cannon Dead");
        }

    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(checkCollision(fa, fb, "Player", "College Sensor Attack")) {
            if(checkFixture(fa,"College Sensor Attack")){
                fa.setUserData("College Sensor");
            } else {
                fb.setUserData("College Sensor");
            }
            subtitle.removeSubtitle();
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean checkCollision(Fixture fa, Fixture fb, String A, String B) {
        return  ( (fa.getUserData() != null && fa.getUserData().equals(A))
                || (fb.getUserData() != null && fb.getUserData().equals(A)) )
                &&
                ( (fa.getUserData() != null && fa.getUserData().equals(B))
                || (fb.getUserData() != null && fb.getUserData().equals(B)) );
    }

    public boolean checkFixture(Fixture f, String target) {
        return f.getUserData() != null && f.getUserData().equals(target);
    }

}
