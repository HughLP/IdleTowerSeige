package com.mygdx.idletowerseige.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.idletowerseige.IdleTowerSeige;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = IdleTowerSeige.WIDTH;
		config.height = IdleTowerSeige.HEIGHT;
		config.title = IdleTowerSeige.TITLE;
		new LwjglApplication(new IdleTowerSeige(), config);
	}
}
