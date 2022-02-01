package com.ducks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.ducks.screens.FinalStorylineScreen;
import com.ducks.screens.InitialStorylineScreen;
import com.ducks.screens.MainGameScreen;
import com.ducks.screens.MainMenuScreen;

public class DeltaDucks extends Game {

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;

	public static final int VIRTUAL_WIDTH = 480*2; // 400
	public static final int VIRTUAL_HEIGHT = 480; // 208


	public static final float PIXEL_PER_METER = 100;
	public static final float TILEED_MAP_SCALE = .3f;

	public static final short BIT_SEA = 2;
	public static final short BIT_LAND = 4;
	public static final short BIT_PLAYER = 8;
	public static final short BIT_PIRATES = 16;
	public static final short BIT_MONSTERS = 32;
	public static final short BIT_BOUNDARY = 64;
	public static final short BIT_BULLETS = 124;
	public static final short BIT_CANNONS = 248;
	public static final short BIT_COLLEGES = 496;

	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MainGameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
