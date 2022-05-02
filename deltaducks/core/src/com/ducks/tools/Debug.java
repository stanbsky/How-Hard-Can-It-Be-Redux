package com.ducks.tools;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

/**
 * Used to print messages to console with a given frequency,
 * displaying the name of the class registering the message.
 * Not used in production due to java version errors
 */
@Deprecated
public final class Debug {
    private static int ticks = 0;
    private final static int tickFrequency = 240;
    private static StackWalker walker;
    private static ArrayList<String> messages;
    public static boolean box2Ddebug = false;
    private static Box2DDebugRenderer b2dr;

    public static void Initialize() {
        walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        messages = new ArrayList<>();
        b2dr = new Box2DDebugRenderer(false, false,
                false, false, false, false);
    }

    public static void debug(Object message) {
        messages.add(walker.getCallerClass() + " : " + message);
    }

    public static void update() {
        ticks++;
        if (ticks == tickFrequency) {
            ticks = 0;
            for (String message : messages) {
                System.out.println(message);
            }
        }
    }

    public static void render(World world, OrthographicCamera gameCam) {
        if (box2Ddebug)
            b2dr.render(world, gameCam.combined);
    }

    public static void dispose() {
        b2dr.dispose();
    }
}
