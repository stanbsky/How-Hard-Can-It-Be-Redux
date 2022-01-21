package com.ducks.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ducks.DeltaDucks;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.foregroundFPS = 60;
		config.title = "Delta Ducks";
		config.width = DeltaDucks.WIDTH;
		config.height = DeltaDucks.HEIGHT;
//		config.resizable = false;

		new LwjglApplication(new DeltaDucks(), config);
	}
}
