package client;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
//import com.google.gwt.core.client.GWT;


/**
 * Main dla desktopowej wersji bibliotek libgdx
 * @author erinu
 *
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title="Texas";
		config.height = 600;
		config.width = 800;
		GUIlibgdx app = new GUIlibgdx();
		new LwjglApplication(app, config);
	}
}
