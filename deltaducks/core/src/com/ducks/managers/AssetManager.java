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
    public static BitmapFont plainFont;
    public static BitmapFont pixelFont;
    public static BitmapFont newFont;
    public static NinePatchDrawable button_up, button_down, button_over, ui_background, topbar_background;

    public static void Initialize() {
        atlas = new TextureAtlas("all_assets.atlas");
        ui = new Skin(new TextureAtlas("ui_assets.atlas"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/OpenSans-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        plainFont = generator.generateFont(parameter);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/boy.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        pixelFont = generator.generateFont(parameter);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font/DrawveticaMini.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 60;
        newFont = generator.generateFont(parameter);

        NinePatch patch;
        patch = new NinePatch(AssetManager.ui.getRegion("button"), 17, 17, 17, 17);
        button_up = new NinePatchDrawable(patch);
        ui_background = new NinePatchDrawable(patch);
        topbar_background = new NinePatchDrawable(patch);
        patch = new NinePatch(AssetManager.ui.getRegion("button_over"), 17, 17, 17, 17);
        button_over = new NinePatchDrawable(patch);
        patch = new NinePatch(AssetManager.ui.getRegion("button_down"), 17, 17, 17, 17);
        button_down = new NinePatchDrawable(patch);
    }
}
