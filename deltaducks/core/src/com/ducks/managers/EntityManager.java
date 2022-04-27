package com.ducks.managers;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.components.Shooter;
import com.ducks.entities.*;
import com.ducks.intangibles.DifficultyControl;
import com.ducks.tools.B2WorldCreator;
import com.ducks.tools.IDrawable;
import com.ducks.tools.IShooter;

import static com.ducks.screens.MainGameScreen.map;
import static com.ducks.screens.MainGameScreen.player;

public final class EntityManager {
    public static Array<Vector2> powerupSpawns;
    public static Array<Vector2> pirateSpawns;
    public static Array<Vector2> collegeSpawns;
    public static Array<Vector2> chestSpawns;
    public static Array<IDrawable> entities;
    private static int entitiesCount = 0;
    public static Array<College> colleges;
    public static Array<Pirate> pirates;
    public static Array<Powerup> powerups;
    private static Array<IDrawable> cleanup;

    // TODO: these belong in some kind of constants class - maybe play difficulty related?
    private static final Array<String> collegeNames =
            new Array<>(new String[]{"goodricke", "constantine", "halifax"});
    private static final Array<String> powerupNames =
            new Array<>(new String[]{"quickfire", "shield", "spray", "supersize", "bullet_hotshot"});
    private static final float pirateSpawnChance = DifficultyControl.getValue(0.1f, 0.2f, 0.4f);
    private static final float powerupSpawnChance = DifficultyControl.getValue(0.7f, 0.5f, 0.4f);

    public static void Initialize() {
        entities = new Array<>();
    }

    /**
     * Separated from Initialize to simplify testing
     */
    public static void spawnEntities() {
        powerupSpawns = getListOfSpawns("powerups");
        pirateSpawns = getListOfSpawns("pirates");
        collegeSpawns = getListOfSpawns("colleges");
        collegeSpawns.shuffle();
        chestSpawns = getListOfSpawns("chests");
        spawnColleges();
        spawnPirates();
        spawnPowerups();
    }

    public static void buildWorldMap(World world) {
        new B2WorldCreator(world);
    }

    public static int registerEntity(IDrawable entity) {
        entities.add(entity);
        return entitiesCount++;
    }

    public static void render() {
        for (IDrawable entity : entities) {
            entity.draw();
        }
    }

    public static void update(float deltaTime) {
        cleanup = new Array<>();
        for (IDrawable entity : entities) {
            entity.update(deltaTime);
            if (entity.cleanup()) {
                cleanup.add(entity);
                entity.dispose();
            }

        }
        entities.removeAll(cleanup, true);
    }

    public static Array<Vector2> getListOfSpawns(String type) {
        Array<Vector2> spawns = new Array<>();
        Rectangle rectangle;
        Vector2 location;
        for (MapObject object : map.getLayers().get(type).getObjects().getByType(RectangleMapObject.class)) {
            rectangle = ((RectangleMapObject) object).getRectangle();
            location = new Vector2(rectangle.getX(), rectangle.getY()).scl(DeltaDucks.TILEED_MAP_SCALE);
            spawns.add(location);
        }
        return spawns;
    }

    // PIRATE FUNCTIONS

    public static boolean livingPiratesExist() { return !pirates.isEmpty(); }

    public static void killPirate (Pirate pirate) {
        pirates.removeValue(pirate, true);
    }

    private static void spawnPirates() {
        Pirate pirate;
        pirates = new Array<>();

        for (Vector2 spawn : pirateSpawns) {
            if (Math.random() > pirateSpawnChance)
                continue;
            pirate = new Pirate(collegeNames.random(), spawn);
            registerEntity(pirate);
            pirates.add(pirate);
//            pirate.setId(pirates.size - 1);
        }
    }

    // COLLEGE FUNCTIONS

    public static boolean livingCollegesExist() {
        for (College college : colleges) {
            if (college.isAlive()) {
                return true;
            }
        }
        return false;
    }

    public static Array<Vector2> getCollegeCoordinates() {
        Array <Vector2> coordinates = new Array <Vector2>();
        for (int i = 0; i < colleges.size; i++) {
            coordinates.add(((Entity)entities.get(i)).getPosition());
        }
        return coordinates;
    }

    private static void spawnColleges() {
        String name;
        College college;
        colleges = new Array<>(3);
        Array<String> names = new Array<>(collegeNames);
        names.shuffle();

        for (int i = 0; i < 3; i++) {
            name = names.pop();
            college = new College(collegeSpawns.get(i), name);
            registerEntity(college);
            colleges.insert(i, college);
        }
    }

    // BULLET FUNCTIONS

    public static void spawnBullet(IShooter shooter) {
        if (shooter.ready()) {
            shooter.resetShootTimer();
            //TODO: Add variable score, somehow
            registerEntity(new EnemyBullet(shooter.getPosition(),
                    Shooter.getDirection(shooter, player)));
        }
    }

    public static void spawnBullet() {
        if (PowerupManager.multishotActive()) {
            registerEntity(new PlayerBullet());
            registerEntity(new PlayerBullet(30f));
            registerEntity(new PlayerBullet(-30f));
        } else {
            registerEntity(new PlayerBullet());
        }
    }

    // POWERUP FUNCTIONS

    private static void spawnPowerups() {
        Powerup powerup;

        powerups = new Array<>();

        for (Vector2 spawn : powerupSpawns) {

            if (Math.random() > powerupSpawnChance)
                continue;
            powerup = new Powerup(spawn, powerupNames.random());
            registerEntity(powerup);
            powerups.add(powerup);
        }
    }
}
