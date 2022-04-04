package com.ducks.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.ducks.components.Texture;

import static com.ducks.DeltaDucks.scl;

/**
 * Crosshair Class for Box2D Body and Sprite
 */
public class Crosshair {

    private Ship player;
    private OrthographicCamera gameCam;
    private Texture texture;
    private static Vector2 direction;
    private Vector2 position;

    private float crosshairRadius = 1.2f;


    /**
     * Constructor
     * @param player Box2D object of player
     * @param gameCam OrthographicCamera
     */
    public Crosshair(Ship player, OrthographicCamera gameCam) {
        this.player = player;
        this.gameCam = gameCam;

        position = new Vector2(0, 0);
        this.texture = new Texture("crosshair256", position, scl(25.6f));
    }

    /**
     * Update the crosshair every delta time interval
     * @param deltaTime of the game
     */
    public void update(float deltaTime) {
        updatePosition();
        this.texture.update(deltaTime, position);
    }

    public void draw(SpriteBatch batch) {
        this.texture.render(batch);
    }

    /**
     * Transforms player's mouse location into the coordinates where the crosshair must be drawn
     * @return world coordinate Vector2 for drawing the crosshair texture
     */
    public void updatePosition() {
        // Get mouse coordinates on screen
        Vector3 loc = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        // Transform mouse coordinates into world coordinates
        gameCam.unproject(loc, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        float x = player.b2body.getPosition().x - loc.x - this.texture.getWidth()/2 +
                player.b2body.getFixtureList().get(0).getShape().getRadius();
        float y = player.b2body.getPosition().y - loc.y - this.texture.getHeight()/2 +
                player.b2body.getFixtureList().get(0).getShape().getRadius();
        // Scale coordinates to fit the radius around the ship
        direction = new Vector2(x, y).nor().scl(-1f * crosshairRadius);
        // Offset the radius by the size of the ship body
        position.x = player.b2body.getPosition().x + direction.x;
        position.y = player.b2body.getPosition().y + direction.y;
    }

    /**
     * Returns the direction vector for the crosshair
     * @return Vector2
     */
    public static Vector2 getCrosshairDirection() {
        return direction;
    }

    /**
     * get Unit (Directional) Vector between two Vectors (points)
     * @param body vector (measured from)
     * @param target vector (measured to)
     * @return Vector2
     */
    public static Vector2 getDirection(Vector2 body, Vector2 target) {
        //TODO: this does not belong in the Crosshair class
        return new Vector2(body.x - target.x, body.y - target.y).nor().scl(-1);
    }
}