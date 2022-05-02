package com.ducks.tools;

/**
 * Defines which fixtures are which
 */
public final class FixtureFilter {
    public static final short SCENERY = 0x1;
    public static final short PLAYER = 0x2;
    public static final short PLAYER_BULLET = 0x4;
    public static final short ENEMY = 0x8;
    public static final short ENEMY_BULLET = 0x10;
    public static final short MASK_ALL = -1;
}
