package com.ducks.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ducks.DeltaDucks;
import com.ducks.entities.Bullet;

import java.util.ArrayList;

public class MainGameScreen implements Screen {

    public static final float SPEED = 100;

    public static final float SHIP_FRAME_DURATION = 0.5f;
    public static final int SHIP_WIDTH_PIXEL = 64;
    public static final int SHIP_HEIGHT_PIXEL = 64;
//    public static final int SHIP_WIDTH = SHIP_WIDTH_PIXEL * 2;
//    public static final int SHIP_HEIGHT = SHIP_HEIGHT_PIXEL * 2;
    public static final int SHIP_WIDTH = SHIP_WIDTH_PIXEL;
    public static final int SHIP_HEIGHT = SHIP_HEIGHT_PIXEL;

//    public static final float ROLL_TIMER_SWITCH_TIME = 0.25f;
    public static final float VERTICAL_ROLL_TIMER_SWITCH_TIME = 0.25f;
    public static final float HORIZONTAL_ROLL_TIMER_SWITCH_TIME = 0.25f;

    public static final float SHOOT_WAIT_TIME = 0.3f;



    Animation<TextureRegion>[] rolls;

    float x, y;
    int roll;
    float rollVerticalTimer;
    float rollHorizontalTimer;
    float stateTime;
    float shootTimer;

    DeltaDucks game;

    ArrayList<Bullet> bullets;

    public static final int UP_INDEX = 8;
    public static final int DOWN_INDEX = 0;
    public static final int LEFT_INDEX = 4;
    public static final int RIGHT_INDEX = 12;
    public static final int FIRST_INDEX = 0;
    public static final int LAST_INDEX = 15;

    int EX;

    public MainGameScreen(DeltaDucks game) {
        this.game = game;
    }

    @Override
    public void show() {
        x = Gdx.graphics.getWidth()/2 - SHIP_WIDTH/2;
        y = Gdx.graphics.getHeight()/2 - SHIP_HEIGHT/2;

        bullets = new ArrayList <Bullet> ();

        roll = 8;
        rolls = new Animation[16];

        TextureRegion [] rollSpriteSheet = TextureRegion.split(new Texture("game/strip.png"), SHIP_WIDTH_PIXEL, SHIP_HEIGHT_PIXEL)[0];

        rolls[0] = new Animation(SHIP_FRAME_DURATION, rollSpriteSheet[8]); // Down
        rolls[1] = new Animation(SHIP_FRAME_DURATION, rollSpriteSheet[7]);
        rolls[2] = new Animation(SHIP_FRAME_DURATION, rollSpriteSheet[6]);
        rolls[3] = new Animation(SHIP_FRAME_DURATION, rollSpriteSheet[5]);
        rolls[4] = new Animation(SHIP_FRAME_DURATION, rollSpriteSheet[4]); // Left
        rolls[5] = new Animation(SHIP_FRAME_DURATION, rollSpriteSheet[3]);
        rolls[6] = new Animation(SHIP_FRAME_DURATION, rollSpriteSheet[2]);
        rolls[7] = new Animation(SHIP_FRAME_DURATION, rollSpriteSheet[1]);
        rolls[8] = new Animation(SHIP_FRAME_DURATION, rollSpriteSheet[0]); // UP
        rolls[9] = new Animation(SHIP_FRAME_DURATION, rollSpriteSheet[15]);
        rolls[10] = new Animation(SHIP_FRAME_DURATION, rollSpriteSheet[14]);
        rolls[11] = new Animation(SHIP_FRAME_DURATION, rollSpriteSheet[13]);
        rolls[12] = new Animation(SHIP_FRAME_DURATION, rollSpriteSheet[12]); // Right
        rolls[13] = new Animation(SHIP_FRAME_DURATION, rollSpriteSheet[11]);
        rolls[14] = new Animation(SHIP_FRAME_DURATION, rollSpriteSheet[10]);
        rolls[15] = new Animation(SHIP_FRAME_DURATION, rollSpriteSheet[9]);

        EX = roll; // Dummy variable to look out for changes in the animation states (from 0 to 15)
    }

    @Override
    public void render(float delta) {
        //Shooting Code
        shootTimer += delta;
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && shootTimer >= SHOOT_WAIT_TIME) {
            shootTimer = 0;

//            int offset = 4;
//            if (roll == 1 || roll == 3)
//                offset = 8;
//            if (roll == 0 || roll == 4)
//                offset = 16;
            int state;
            if (roll == 0)
                state = 1;
            else if (roll < 4)
                state = 7;
            else if (roll == 4)
                state = 3;
            else if (roll < 8)
                state = 5;
            else if (roll == 8)
                state = 0;
            else if (roll < 12)
                state = 4;
            else if (roll == 12)
                state = 2;
            else
                state = 6;
            bullets.add(new Bullet(x + SHIP_WIDTH/2 - 3, y + SHIP_HEIGHT/2 - 12, state));
//            bullets.add(new Bullet(x + SHIP_WIDTH - offset));
        }
        // Update Bullets
        ArrayList <Bullet> bulletsToRemove = new ArrayList <Bullet> ();
        for (Bullet bullet : bullets) {
            bullet.update(delta);
            if (bullet.remove)
                bulletsToRemove.add(bullet);
        }

        bullets.removeAll(bulletsToRemove);

        // Movement Code
        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            y += SPEED * Gdx.graphics.getDeltaTime();
            if (y + SHIP_HEIGHT > Gdx.graphics.getHeight()) // Gdx.graphics.getWidth()
                y = Gdx.graphics.getHeight() - SHIP_HEIGHT;
            if (Gdx.input.isKeyPressed(Input.Keys.UP) && !Gdx.input.isKeyPressed(Input.Keys.DOWN) && roll != UP_INDEX) {
                rollVerticalTimer = 0;
                if (roll > DOWN_INDEX && roll < UP_INDEX) {
                    roll++;
                } else if (roll == FIRST_INDEX) {
                    roll = LAST_INDEX;
                } else if (roll <= LAST_INDEX) {
                    roll--;
                }
            }
            rollVerticalTimer += Gdx.graphics.getDeltaTime();
            if (Math.abs(rollVerticalTimer) > VERTICAL_ROLL_TIMER_SWITCH_TIME && roll != UP_INDEX) {
                rollVerticalTimer -= VERTICAL_ROLL_TIMER_SWITCH_TIME;
                if (roll > DOWN_INDEX && roll < UP_INDEX) {
                    roll++;
                } else if (roll == FIRST_INDEX) {
                    roll = LAST_INDEX;
                } else if (roll <= LAST_INDEX) {
                    roll--;
                }
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            y -= SPEED * Gdx.graphics.getDeltaTime();
            if (y < 0)
                y = 0;
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && !Gdx.input.isKeyPressed(Input.Keys.UP) && roll != DOWN_INDEX) {
                rollVerticalTimer = 0;
                if (roll <= UP_INDEX && roll > FIRST_INDEX) {
                    roll--;
                } else if (roll > UP_INDEX && roll < LAST_INDEX) {
                    roll++;
                } else if (roll == LAST_INDEX) {
                    roll = FIRST_INDEX;
                }
            }
            rollVerticalTimer -= Gdx.graphics.getDeltaTime();
            if (Math.abs(rollVerticalTimer) > VERTICAL_ROLL_TIMER_SWITCH_TIME && roll != DOWN_INDEX) {
                rollVerticalTimer -= VERTICAL_ROLL_TIMER_SWITCH_TIME;
                if (roll <= UP_INDEX && roll > FIRST_INDEX) {
                    roll--;
                } else if (roll > UP_INDEX && roll < LAST_INDEX) {
                    roll++;
                } else if (roll == LAST_INDEX) {
                    roll = FIRST_INDEX;
                }
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            x -= SPEED * Gdx.graphics.getDeltaTime();
            if (x < 0)
                x = 0;
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT) && roll != LEFT_INDEX) {
                rollHorizontalTimer = 0;
                if (roll >= DOWN_INDEX && roll < LEFT_INDEX) {
                    roll++;
                } else if (roll <= RIGHT_INDEX) {
                    roll--;
                } else if (roll == LAST_INDEX) {
                    roll = FIRST_INDEX;
                } else {
                    roll++;
                }
            }
            rollHorizontalTimer -= Gdx.graphics.getDeltaTime();
            if (Math.abs(rollHorizontalTimer) > HORIZONTAL_ROLL_TIMER_SWITCH_TIME && roll != LEFT_INDEX) {
                rollHorizontalTimer -= HORIZONTAL_ROLL_TIMER_SWITCH_TIME;
                if (roll >= DOWN_INDEX && roll < LEFT_INDEX) {
                    roll++;
                } else if (roll <= RIGHT_INDEX) {
                    roll--;
                } else if (roll == LAST_INDEX) {
                    roll = FIRST_INDEX;
                } else {
                    roll++;
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                roll = UP_INDEX-2;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                roll = LEFT_INDEX-2;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            x += SPEED * Gdx.graphics.getDeltaTime();
            if (x + SHIP_WIDTH > Gdx.graphics.getWidth()) // Gdx.graphics.getWidth()
                x = Gdx.graphics.getWidth() - SHIP_WIDTH;
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.LEFT) && roll != RIGHT_INDEX) {
                rollHorizontalTimer = 0;
                if (roll <= LAST_INDEX && roll > RIGHT_INDEX) {
                    roll--;
                } else if (roll <= LEFT_INDEX && roll > FIRST_INDEX) {
                    roll--;
                } else if (roll == FIRST_INDEX) {
                    roll = LAST_INDEX;
                } else {
                    roll++;
                }
            }
            rollHorizontalTimer += Gdx.graphics.getDeltaTime();
            if (Math.abs(rollHorizontalTimer) > HORIZONTAL_ROLL_TIMER_SWITCH_TIME && roll != RIGHT_INDEX) {
                rollHorizontalTimer -= HORIZONTAL_ROLL_TIMER_SWITCH_TIME;
                if (roll <= LAST_INDEX && roll > RIGHT_INDEX) {
                    roll--;
                } else if (roll <= LEFT_INDEX && roll > FIRST_INDEX) {
                    roll--;
                } else if (roll == FIRST_INDEX) {
                    roll = LAST_INDEX;
                } else {
                    roll++;
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                roll = UP_INDEX+2;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                roll = RIGHT_INDEX+2;
            }
        }
//        if(roll == -1)
//            roll = 0;
//        if(roll == 16)
//            roll = 15;
        if (EX != roll) {
//            System.out.println(roll);
            EX = roll;
        }

        stateTime += delta;

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        for(Bullet bullet : bullets) {
            bullet.render(game.batch);
        }

        game.batch.draw(rolls[roll].getKeyFrame(stateTime, true), x, y, SHIP_WIDTH, SHIP_HEIGHT);

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
