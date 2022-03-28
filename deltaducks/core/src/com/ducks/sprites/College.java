package com.ducks.sprites;

//import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.components.Texture;
import com.ducks.entities.ListOfCannons;
import com.ducks.scenes.Hud;
import com.ducks.screens.MainGameScreen;

/***
 * College Class for Box2D Body and Sprite
 */
public class College {
    public World world;

    private Animation<TextureRegion> collegeIdle;
    private Animation<TextureRegion> collegeDestroyed;

    private final int PIXEL_COLLEGE_WIDTH = 2560;
    public static final int PIXEL_COLLEGE_HEIGHT = 2560;

    private final float COLLEGE_WIDTH = PIXEL_COLLEGE_WIDTH * .1f;
    public static final float COLLEGE_HEIGHT = PIXEL_COLLEGE_HEIGHT * .1f;
    private final float SCALE = 1f;

    public final float OUTER_RADIUS = 4f;

    float stateTime;

    public Body collegeBody;
    ListOfCannons cannons;

    public enum CollegeName {DERWENT, CONSTANTINE, GOODRICK, HALIFAX};
    private CollegeName collegeName;

    public float health;
//    Texture healthBar;
    Texture texture;
    private Vector2 position;

    /**
     * Constructor
     * @param world Box2D world
     * @param screen Game Screen
     * @param spawn_x X coordinate of the college
     * @param spawn_y Y coordinate of the college
     * @param spawn_radius Radius of the college Box2D body
     * @param collegeName Name of the College
     * @param cannons Cannons class to spawn and add Cannon round
     */
    public College(World world, MainGameScreen screen, float spawn_x, float spawn_y, float spawn_radius, String collegeName, ListOfCannons cannons) {
//        super(MainGameScreen.resources.getTexture(collegeName));
        this.world = world;
//        this.collegeName = collegeName;
        this.cannons = cannons;

        health = 1f;
//        healthBar = MainGameScreen.resources.getTexture("blank");

        Array<TextureRegion> frames = new Array<TextureRegion>();
//        for(int i=0; i<1; i++) {
//            frames.add(new TextureRegion(getTexture(), i * PIXEL_COLLEGE_WIDTH, 0, PIXEL_COLLEGE_WIDTH, PIXEL_COLLEGE_HEIGHT));
//        }

//        frames.add(new TextureRegion(getTexture(), 0, 0, PIXEL_COLLEGE_WIDTH, PIXEL_COLLEGE_HEIGHT));
        this.position = new Vector2(spawn_x, spawn_y);
        //TODO: change to AtlasRegion
        this.texture = new Texture(collegeName, this.position, SCALE);
        //this.alive = new Texture(collegeName, this.position, PIXEL_COLLEGE_WIDTH, PIXEL_COLLEGE_HEIGHT);
//        collegeIdle = new Animation(0.1f, frames);
//        frames.clear();
//        for(int i=0; i<1; i++) {
//            frames.add(new TextureRegion(MainGameScreen.resources.getTexture("college destroyed"), i * PIXEL_COLLEGE_WIDTH, 0, PIXEL_COLLEGE_WIDTH, PIXEL_COLLEGE_HEIGHT));
//        }
//        frames.add(new TextureRegion(MainGameScreen.resources.getTexture("college destroyed"), 0, 0, PIXEL_COLLEGE_WIDTH, PIXEL_COLLEGE_HEIGHT));
//        collegeDestroyed = new Animation(0.1f, frames);
//        frames.clear();


        defineCollege(spawn_x, spawn_y, spawn_radius);
//        setBounds((spawn_x - COLLEGE_WIDTH / 2f) / DeltaDucks.PIXEL_PER_METER, (spawn_y - COLLEGE_HEIGHT / 2f) / DeltaDucks.PIXEL_PER_METER, COLLEGE_WIDTH / DeltaDucks.PIXEL_PER_METER, COLLEGE_HEIGHT / DeltaDucks.PIXEL_PER_METER);
//        setRegion(collegeIdle.getKeyFrame(stateTime, true));
    }

    /**
     * Update the bullet every delta time interval
     * @param deltaTime of the game
     */
    public void update(float deltaTime) {
        stateTime += deltaTime;
        if((health <= 0f) && (health > -100f)) {
//            setRegion(collegeDestroyed.getKeyFrame(stateTime, true));
            //TODO: change to AtlasRegion
            this.texture = new Texture("destroyed", this.position, SCALE);
            this.texture.update(deltaTime, collegeBody.getPosition());
            health = -100f;
        } else {
//            setRegion(collegeIdle.getKeyFrame(stateTime, true));
            //TODO: change to AtlasRegion
            this.texture.update(deltaTime, collegeBody.getPosition());
            if(collegeBody.getFixtureList().get(1).getUserData().toString().contains("Attack")) {
                cannons.spawnCannon(this);
            }
            if(collegeBody.getFixtureList().get(0).getUserData().toString().contains("Damage")) {
                collegeBody.getFixtureList().get(0).setUserData("College");
                health-=.2f;
            }
        }
    }

    /**
     * draw the sprite of college and health bar on the game screen
     * @param batch to draw on the screen
     */
    public void extendedDraw(SpriteBatch batch) {
        //TODO: change to AtlasRegion
        this.texture.render(batch);
        //TODO: health bar needs drawing
//        if(health >= 0f) {
//            if (health > .6f)
//                batch.setColor(Color.GREEN);
//            else if (health > .2f)
//                batch.setColor(Color.ORANGE);
//            else
//                batch.setColor(Color.RED);
//            batch.draw(healthBar, collegeBody.getPosition().x - .5f * health/2 - .1f/2, collegeBody.getPosition().y + collegeBody.getFixtureList().get(0).getShape().getRadius() + .05f, .5f * health + .1f, .05f);
//            batch.setColor(Color.WHITE);

//        }
    }

    /**
     * Define the Box2D body and fixture and map it onto the Box2D world
     * @param x coordinate of Box2D body
     * @param y coordinate of Box2D body
     * @param radius radius of Box2D body
     */
    public void defineCollege(float x, float y, float radius) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x / DeltaDucks.PIXEL_PER_METER, y / DeltaDucks.PIXEL_PER_METER);
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.linearDamping = 1f;
        collegeBody = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius / DeltaDucks.PIXEL_PER_METER);

        fdef.shape = shape;
        fdef.filter.categoryBits = DeltaDucks.BIT_COLLEGES;
        fdef.filter.maskBits = DeltaDucks.BIT_PLAYER | DeltaDucks.BIT_BULLETS;
        fdef.restitution = 0.2f;
        collegeBody.createFixture(fdef).setUserData("College");

        PolygonShape polyShape = new PolygonShape();
        polyShape.setAsBox(radius * OUTER_RADIUS / DeltaDucks.PIXEL_PER_METER, radius * OUTER_RADIUS / DeltaDucks.PIXEL_PER_METER, new Vector2(0, -5 / DeltaDucks.PIXEL_PER_METER), 0);
        fdef.shape = polyShape;
        fdef.filter.categoryBits = DeltaDucks.BIT_COLLEGES;
        fdef.filter.maskBits = DeltaDucks.BIT_PLAYER;
        fdef.isSensor = true;
        collegeBody.createFixture(fdef).setUserData("College Sensor");
    }

    /**
     * Gain gold and EXP if colleges get destroyed
     */
    public void dispose() {
        Hud.addGold(1000);
        Hud.addScore(10000);
    }


}
