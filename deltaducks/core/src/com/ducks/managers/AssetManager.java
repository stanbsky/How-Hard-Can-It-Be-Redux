package com.ducks.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public final class AssetManager {
    public static TextureAtlas atlas;
    public static Skin ui;
    public static BitmapFont font;
    public static NinePatchDrawable button_up, button_down, button_over;

    public static void Initialize() {
        atlas = new TextureAtlas("all_assets.atlas");
        ui = new Skin(new TextureAtlas("ui_assets.atlas"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/OpenSans-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        font = generator.generateFont(parameter);

        NinePatch patch;
        patch = new NinePatch(AssetManager.ui.getRegion("button"), 17, 17, 17, 17);
        button_up = new NinePatchDrawable(patch);
        patch = new NinePatch(AssetManager.ui.getRegion("button_over"), 17, 17, 17, 17);
        button_over = new NinePatchDrawable(patch);
        patch = new NinePatch(AssetManager.ui.getRegion("button_down"), 17, 17, 17, 17);
        button_down = new NinePatchDrawable(patch);
    }
}