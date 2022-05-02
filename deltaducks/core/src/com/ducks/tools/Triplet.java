package com.ducks.tools;

/**
 * A 3 tuple
 * @param <X> type 1
 * @param <Y> type 2
 * @param <Z> type 3
 */
public class Triplet<X, Y, Z> {
    /**
     * Element 1
     */
    public final X x;
    /**
     * Element 2
     */
    public final Y y;
    /**
     * Element 3
     */
    public final Z z;

    /**
     * Inits to the given values
     * @param x Value 1
     * @param y Value 2
     * @param z Value 3
     */
    public Triplet(X x, Y y, Z z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
