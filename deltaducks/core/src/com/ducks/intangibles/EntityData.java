package com.ducks.intangibles;

import com.badlogic.gdx.physics.box2d.Fixture;

import java.util.Stack;

public class EntityData {

    private Stack<Fixture> contacts;
    private short category;
    private String name;

    public EntityData(short category) {
        this.category = category;
        contacts = new Stack<>();
    }

    public EntityData(short category, String name) {
        this(category);
        setName(name);
    }

    public void addContact(Fixture fa) {
        contacts.push(fa);
    }

    public boolean hasContacts() {
        return !contacts.isEmpty();
    }

    public Fixture getContact() {
        return contacts.pop();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(short category) {
        return this.category == category;
    }

    public static boolean equals(Fixture fixture, short category) {
        if (fixture.getUserData() != null)
            return ((EntityData) fixture.getUserData()).equals(category);
        return false;
    }
}
