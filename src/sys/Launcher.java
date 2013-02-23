package sys;

import java.util.ArrayList;

import menu.StartMenu;
import ent.Player;


public class Launcher {
	public static final double VERSION_NUM = 1.8;
	public static String[] subtitles = { "2006", "XP", "Revenge", "3000", "Part ||", "Turbo", "64", "HD", "Simulator", "Gaiden", "Black Ops", "Episode 3", "Zero"};
	
	public static void main(String[] args) {
		// load some fuckin libraries
		try {
			AssetLib.load();
		} catch (Exception e) {
			System.out.println("Huge problem loading assets! Yell at the devs!");
			e.printStackTrace();
			System.exit(0);
		}
		
		MenuGame menu = new MenuGame();
		ArrayList<Player> players = menu.runMenu();

		Game game = new Game(players);
		game.run();
	}
}

class MenuGame extends Game {
	private static final long serialVersionUID = 1L;

	public ArrayList<Player> runMenu() {
		StartMenu start = (StartMenu) menuStack.peek();
		super.run();
		return start.getPlayerList();
	}
}