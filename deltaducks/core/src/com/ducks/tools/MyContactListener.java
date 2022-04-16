package com.ducks.tools;

import com.badlogic.gdx.physics.box2d.*;
import com.ducks.entities.Entity;
import com.ducks.intangibles.EntityData;
import com.ducks.ui.Hud;
import com.ducks.ui.Subtitle;
import com.ducks.entities.Ship;

/***
 * Listen to any collision in Box2D world among any 2 Box2D Collisive Body
 */
public class MyContactListener implements ContactListener {

    Ship player;
    Subtitle subtitle;

    /**
     * Constructor
     * @param player Box2D body
     * @param subtitle bottom text
     */
    public MyContactListener(Ship player, Subtitle subtitle) {
        this.player = player;
        this.subtitle = subtitle;
    }

    /**
     * Triggers when two Box2D body collides
     * @param contact Object of bodies in contact
     */
    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();


        // TODO: figure out if this Monster thing is totally unimplemented...
//        if(checkCollision(fa, fb, "Player", "Monster Sensor")) {
//            player.b2body.applyLinearImpulse(new Vector2(
//                    player.b2body.getPosition().x - 10 / DeltaDucks.PIXEL_PER_METER, player.b2body.getPosition().y - 32*4 / DeltaDucks.PIXEL_PER_METER
//            ), player.b2body.getWorldCenter(), true);
//        }

        if(checkCollision(fa, fb, "Player", "College Sensor")) {
            if(checkFixture(fa, "College Sensor")){
                fa.setUserData("College Sensor Attack");
            } else {
                fb.setUserData("College Sensor Attack");
            }
            subtitle.setSubtitle("Enemy College Nearby");
        }

        if(checkCollision(fa, fb, "Player", "Pirate Ship Sensor")) {
            if(checkFixture(fa, "Pirate Ship Sensor")){
                fa.setUserData("Pirate Ship Sensor Attack");
            } else {
                fb.setUserData("Pirate Ship Sensor Attack");
            }
        }

        wasPirateAttacked(fa, fb);
        if(checkCollision(fa, fb, "Pirate", "Bullet")) {
            if(checkFixture(fa, "Pirate")){
                ((EntityData) fa.getUserData()).isAlive = false;
            } else {
                ((EntityData) fb.getUserData()).isAlive = false;
            }
        }
        if(checkCollision(fa, fb, "College", "Bullet")) {
            if(checkFixture(fa, "Bullet")){
                ((EntityData) fa.getUserData()).isAlive = false;
            } else {
                ((EntityData) fb.getUserData()).isAlive = false;
            }
            if(checkFixture(fa, "College")){
                ((EntityData) fa.getUserData()).hit();
            } else {
                ((EntityData) fb.getUserData()).hit();
            }
        }


        if(checkCollision(fa, fb, "Player", "Cannon")) {
            Hud.decHealth();
        }

        if(checkFixture(fa, "Cannon")){
            ((EntityData) fb.getUserData()).isAlive = false;
        }
        if(checkFixture(fb, "Cannon ")){
            ((EntityData) fb.getUserData()).isAlive = false;
        }

    }

    private void wasPirateAttacked(Fixture fa, Fixture fb) {
        Fixture pirate = null;
        Fixture bullet = null;
        if (match(fa, "Pirate"))
            pirate = fa;
        else if (match(fb, "Pirate"))
            pirate = fb;
        else
            return;
        if (match(fa, "Bullet"))
            pirate = fa;
        else if (match(fb, "Bullet"))
            pirate = fb;
        else
            return;
        ((EntityData) pirate.getUserData()).isAlive = false;
        ((EntityData) bullet.getUserData()).isAlive = false;
    }

    private boolean match(Fixture fa, String name) {
        return ((EntityData) fa.getUserData()).equals(name);
    }
    /**
     * Triggers after two Box2D body collides
     * @param contact Object of bodies who was contact
     */
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

        if(checkCollision(fa, fb, "Player", "Pirate Ship Sensor Attack")) {
            if(checkFixture(fa,"Pirate Ship Sensor Attack")){
                fa.setUserData("Pirate Ship Sensor");
            } else {
                fb.setUserData("Pirate Ship Sensor");
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    /**
     *  Check for collision
     * @param fa Fixture of body A
     * @param fb Fixture of body A
     * @param A UserData of body A
     * @param B UserData of body B
     * @return true if any two body corresponding to UserData collides
     */
    public boolean checkCollision(Fixture fa, Fixture fb, String A, String B) {
        return  ( (fa.getUserData() != null && fa.getUserData().equals(A))
                || (fb.getUserData() != null && fb.getUserData().equals(A)) )
                &&
                ( (fa.getUserData() != null && fa.getUserData().equals(B))
                || (fb.getUserData() != null && fb.getUserData().equals(B)) );
    }

    /**
     * Check for presence of body
     * @param f Fixture of a body
     * @param target UserData of a body
     * @return true of body Fixture have got same UserData
     */
    public boolean checkFixture(Fixture f, String target) {
        return f.getUserData() != null && f.getUserData().equals(target);
    }

}
