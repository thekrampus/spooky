package sys;


public class Launcher {
	public static final double VERSION_NUM = 3.1;
	
	public static void main(String[] args) {
		// load some fuckin libraries
		try {
			AssetLib.load();
		} catch (Exception e) {
			System.out.println("Huge problem loading assets! Yell at the devs!");
			e.printStackTrace();
			System.exit(0);
		}

		Game game = new Game();
		game.run();
	}
}
