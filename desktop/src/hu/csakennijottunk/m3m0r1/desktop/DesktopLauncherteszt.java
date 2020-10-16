package hu.csakennijottunk.m3m0r1.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import hu.csakennijottunk.m3m0r1.MyGdxGame;
import hu.csakennijottunk.m3m0r1.MyGdxGameTeszt;

public class DesktopLauncherteszt {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MyGdxGameTeszt(), config);
	}
}
