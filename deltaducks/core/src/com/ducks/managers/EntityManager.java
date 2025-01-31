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
import com.ducks.tools.Saving.*;

import static com.ducks.DeltaDucks.PIXEL_PER_METER;
import static com.ducks.screens.MainGameScreen.map;
import static com.ducks.screens.MainGameScreen.player;

public final class EntityManager {
    public static Array<Vector2> powerupSpawns;
    public static Array<Vector2> pirateSpawns;
    public static Array<Vector2> collegeSpawns;
    public static Array<Vector2> chestSpawns;
    public static Array<Vector2> whirlpoolSpawns;
    public static int whirlpoolNo;
    private static int whirlpoolTime = 0;
    public static Array<IDrawable> entities;
    public static Array<College> colleges;
    public static Array<Pirate> pirates;
    public static Array<Powerup> powerups;
    private static Array<IDrawable> cleanup;

    // TODO: these belong in some kind of constants class - maybe play difficulty related?
    public static final Array<String> collegeNames =
            new Array<>(new String[]{"goodricke", "constantine", "halifax"});
    private static final Array<String> powerupNames =
            new Array<>(new String[]{"quickfire", "shield", "spray", "supersize", "bullet_hotshot"});
    private static float pirateSpawnChance;
    private static float powerupSpawnChance;

    /**
     * Set/reset the difficulty values
     */
    public static void Initialize() {
        entities = new Array<>();
        pirateSpawnChance = DifficultyControl.getValue(0.1f, 0.2f, 0.4f);
        powerupSpawnChance = DifficultyControl.getValue(0.8f, 0.6f, 0.5f);
    }

    /**
     * Spawn all entities needed at the start of each game
     * Separated from Initialize to simplify testing
     */
    public static void spawnEntities() {
        powerupSpawns = getListOfSpawns("powerups");
        pirateSpawns = getListOfSpawns("pirates");
        collegeSpawns = getListOfSpawns("colleges");
        collegeSpawns.shuffle();
        chestSpawns = getListOfSpawns("chests");
        whirlpoolSpawns = getListOfSpawns("whirlpools");
        whirlpoolSpawns.shuffle();
        whirlpoolNo = 0;
        spawnNextWhirlpool();
        if(SaveManager.LoadSave) {
            powerups = new Array<>();
            pirates = new Array<>();
            colleges = new Array<>(3);
            Load(SaveManager.saveData.entityManager);
        } else {
            spawnPowerups();
            spawnColleges();
            spawnPirates();
        }
    }

    public static void buildWorldMap(World world) {
        new B2WorldCreator(world);
    }

    public static void registerEntity(IDrawable entity) {
        entities.add(entity);
    }

    public static void registerBackgroundEntity(IDrawable entity) {
        entities.insert(0, entity);
    }

    /**
     * Render entities from the bottom of the array to the top
     */
    public static void render() {
        for (IDrawable entity : entities) {
            entity.draw();
        }
    }

    /**
     * Updates the entities
     * Removes entities from the list with the cleanup flag
     * Summons a new whirlpool if it has been enough time
     * @param deltaTime of game
     */
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

        if (whirlpoolTime++ >= 40 * 60) {
            spawnNextWhirlpool();
            whirlpoolTime = 0;
        }
    }

    /**
     * Returns an array containing rectangles from the map
     * @param type of entity to spawn
     * @return list of locations for entities to spawn in
     */
    public static Array<Vector2> getListOfSpawns(String type) {
        Array<Vector2> spawns = new Array<>();
        Rectangle rectangle;
        Vector2 location;
        for (RectangleMapObject object : map.getLayers().get(type).getObjects().getByType(RectangleMapObject.class)) {
            rectangle = object.getRectangle();
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
        }
    }

    // COLLEGE FUNCTIONS

    /**
     * @return if any of the three colleges are still alive
     */
    public static boolean livingCollegesExist() {
        for (College college : colleges) {
            if (college.isAlive()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Spawns 3 colleges on random islands
     */
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

    /**
     * Spawns a bullet with location and momentum
     * @param shooter interface for the class shooting
     */
    public static void spawnBullet(IShooter shooter) {
        if (shooter.ready()) {
            shooter.resetShootTimer();
            registerEntity(new EnemyBullet(shooter.getPosition(),
                    Shooter.getDirection(shooter, player)));
        }
    }

    /**
     * Spawns three bullets if the boss is ready to shoot it
     * @param boss interface for the boss shooting
     */
    public static void spawnBossShot(IShooter boss) {
        if (boss.ready()) {
            boss.resetShootTimer();
            registerEntity(new EnemyBullet(boss.getPosition(),
                    Shooter.getDirection(boss, player)));
            registerEntity(new EnemyBullet(boss.getPosition(),
                    Shooter.getDirection(boss, player), 30f));
            registerEntity(new EnemyBullet(boss.getPosition(),
                    Shooter.getDirection(boss, player), -30f));
        }
    }

    /**
     * Shoots a bullet for the player
     * Shoots three bullets if the powerup multishot is active
     */
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

    public static void killPowerup (Powerup powerup) {
        powerups.removeValue(powerup, true);
    }

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

    // WHRILPOOL FUNCTIONS

    /**
     * Spawns the next whirlpool sequentially
     */
    public static void spawnNextWhirlpool() {
        Whirlpool whirlpool;
        whirlpool = new Whirlpool(whirlpoolSpawns.get(whirlpoolNo++ % whirlpoolSpawns.size));
        registerBackgroundEntity(whirlpool);
    }

    // SAVING FUNCTIONS

    /**
     * Collects college, pirate, and powerup data
     * @return data for saving
     */
    public static ISaveData Save() {
        EntitiesSaveData save = new EntitiesSaveData();
        for(College c : colleges) {
            CollageSaveData data = new CollageSaveData();
            data.name = c.name;
            data.health = c.health;
            data.position = c.getPosition().scl(PIXEL_PER_METER);
            save.collages.add(data);
        }
        for(Pirate p : pirates) {
            PirateSaveData data = new PirateSaveData();
            data.college = p.collegeName;
            data.position = p.getPosition().scl(PIXEL_PER_METER);
            save.pirates.add(data);
        }
        for(Powerup p : powerups) {
            PowerupSaveData data = new PowerupSaveData();
            data.powerup = p.name;
            data.position = p.getPosition().scl(PIXEL_PER_METER);
            save.powerups.add(data);
        }
        return save;
    }

    /**
     * Spawns entities with saved data, placing them in the correct positions
     * @param data used to give to entities when spawned
     */
    public static void Load(ISaveData data) {
        EntitiesSaveData save = (EntitiesSaveData) data;
        for(CollageSaveData c : save.collages) {
            College coll = new College(c.position, c.name);
            coll.health = c.health;

            registerEntity(coll);
            colleges.add(coll);
        }
        for(PirateSaveData p : save.pirates) {
            Pirate pirate = new Pirate(p.college, p.position);
            registerEntity(pirate);
            pirates.add(pirate);
        }
        for(PowerupSaveData p : save.powerups) {
            Powerup powerup = new Powerup(p.position, p.powerup);
            registerEntity(powerup);
            powerups.add(powerup);
        }
    }
}
