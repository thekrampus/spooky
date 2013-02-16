package sys;

import java.io.IOException;

public class Launcher {
	public static void main(String[] args) {
		//load some fuckin libraries
		try {
			AssetLib.load();
		} catch(IOException e) {
			System.out.println("Huge problem loading assets! Yell at the devs!");
			e.printStackTrace();
			System.exit(0);
		}
		
		Game game = new Game();
		game.run();
	}
}
