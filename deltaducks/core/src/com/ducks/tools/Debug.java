package com.ducks.tools;


import java.util.ArrayList;

/**
 * Used to print messages to console with a given frequency,
 * displaying the name of the class registering the message.
 */
public final class Debug {
    private static int ticks = 0;
    private final static int tickFrequency = 240;
    private static StackWalker walker;
    private static ArrayList<String> messages;

    public static void Initialize() {
        walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
        messages = new ArrayList<>();
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
}
