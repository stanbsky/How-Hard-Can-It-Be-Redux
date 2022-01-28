package sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.ducks.DeltaDucks;
import com.ducks.screens.MainGameScreen;

public class Crosshair extends Sprite {
    public World world;
    Ship player;

    private Animation<TextureRegion> wormIdle;

    private final int PIXEL_WORM_WIDTH = 200;
    private final int PIXEL_WORM_HEIGHT = 200;

    private final float WORM_WIDTH = PIXEL_WORM_WIDTH * .2f;
    private final float WORM_HEIGHT = PIXEL_WORM_HEIGHT * .2f;

    float stateTime;

    private final Vector2 mouseInWorld2D = new Vector2();
    private final Vector3 mouseInWorld3D = new Vector3();
    private OrthographicCamera gameCam;

    float angle = 45f;


    public Crosshair(World world, MainGameScreen screen, Ship player, OrthographicCamera gameCam) {
        super(MainGameScreen.resources.getTexture("crosshair"));
        this.world = world;
        this.player = player;
        this.gameCam = gameCam;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i=0; i<1; i++) {
            frames.add(new TextureRegion(getTexture(), i * PIXEL_WORM_WIDTH, 0, PIXEL_WORM_WIDTH, PIXEL_WORM_HEIGHT));
        }
        wormIdle = new Animation(0.1f, frames);
        frames.clear();
//        setX(player.b2body.getPosition().x + 1);
//        setY(player.b2body.getPosition().y + 1);
        setBounds(player.b2body.getPosition().x + 1, player.b2body.getPosition().y + 1, WORM_WIDTH / DeltaDucks.PIXEL_PER_METER, WORM_HEIGHT / DeltaDucks.PIXEL_PER_METER);
        setRegion(wormIdle.getKeyFrame(stateTime, true));
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;


        mouseInWorld3D.x = Gdx.input.getX();
        mouseInWorld3D.y = Gdx.input.getY();
        mouseInWorld3D.z = 0;
        gameCam.unproject(mouseInWorld3D);
        mouseInWorld2D.x = mouseInWorld3D.x;
        mouseInWorld2D.y = mouseInWorld3D.y;

        Vector2 body = new Vector2(player.b2body.getPosition().x, player.b2body.getPosition().y);
        Vector2 mouse = new Vector2(mouseInWorld2D.x, mouseInWorld2D.y);

        float m1 = player.b2body.getPosition().y / player.b2body.getPosition().x;
        float m2 = mouseInWorld2D.y / mouseInWorld2D.x;
//        System.out.println(mouseInWorld2D + " " + player.b2body.getPosition());
//        System.out.println(Math.toDegrees(Math.atan((m1-m2)/(1+m1*m2))));
        Vector2 crosshairVector = new Vector2(mouseInWorld2D.x, mouseInWorld2D.y);
        Vector2 playerVector = new Vector2(player.b2body.getPosition().x, player.b2body.getPosition().y);
        Vector2 distanceVector = new Vector2(1, 0);

        distanceVector.setAngleDeg(crosshairVector.angleDeg(playerVector));
        crosshairVector = playerVector.add(distanceVector);

//        setPosition ( crosshairVector.x ,  crosshairVector.y );
        setPosition ( mouseInWorld2D.x - getWidth()/2,  mouseInWorld2D.y - getHeight()/2);
    }
}
