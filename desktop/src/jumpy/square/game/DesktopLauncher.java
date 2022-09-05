package jumpy.square.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import jumpy.square.game.Game;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	static float WIN_WIDTH = 600;
	static float WIN_HEIGHT = 600;

	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode((int)WIN_WIDTH, (int)WIN_HEIGHT);
		config.setResizable(false);
		config.setTitle("JumpySquare");
		new Lwjgl3Application(new Game(), config);
	}
}
