package menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import sys.AssetLib;
import sys.Game;
import sys.InputMethod;
import sys.Launcher;
import ent.Player;

public class StartMenu extends MenuFrame {
	public static final int WIDTH = 220, HEIGHT = 400;

	private Menu menu;
	private ArrayList<InputMethod> allInputs;
	private CharacterMenu characterSelect;
	private String subtitle;

	public StartMenu(final ArrayList<InputMethod> allInputs) {
		super(Game.centerX - WIDTH / 2, Game.centerY - HEIGHT / 2, WIDTH, HEIGHT);
		subtitle = Launcher.subtitles[(new Random()).nextInt(Launcher.subtitles.length)];

		this.allInputs = allInputs;

		menu = new Menu();

		menu.addItem(new MenuItem("new game") {
			public void activate() {
				// uhhhhhhhhhhhh
				//alive = false;
				characterSelect = new CharacterMenu(allInputs);
				Game.pushMenu(characterSelect);
			}
		});

		/*menu.addItem(new MenuItem("options") {
			public void activate() {
				System.out.println("Aint no mothafuckin options!");
			}
		}); */

		menu.addItem(new MenuItem("exit") {
			public void activate() {
				System.out.println("Shutting down...");
				System.exit(0);
			}
		});
	}

	@Override
	public void control() {
		boolean up, down, select;
		up = down = select = false;
		for (InputMethod in : allInputs) {
			if (in.stickL()[1] < -.5)
				up = true;
			if (in.stickL()[1] > .5)
				down = true;
			if (in.activate())
				select = true;
		}

		if(!hold) {
			if (up) {
				menu.prev();
				hold = true;
			} else if (down) {
				menu.next();
				hold = true;
			} else if (select) {
				menu.activate();
				hold = true;
			}
		}
		
		if(!up && !down && !select)
			hold = false;
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		g.setColor(Color.black);
		g.setFont(AssetLib.FONT_LARGE);
		g.drawString("Spooky", frame.x+6, frame.y+36);
		g.drawString("Dungeon", frame.x+6, frame.y+78);
		g.drawString(subtitle, frame.x+6, frame.y+120);
		g.setFont(AssetLib.FONT_SMALL);
		g.drawString("ver " + Launcher.VERSION_NUM + "spooky" + 2*Launcher.VERSION_NUM + "u", frame.x+3, frame.y + (int)frame.getHeight() - 1 - 24);
		g.drawString("Made for Tech Game Jam 1", frame.x+3, frame.y + (int)frame.getHeight() - 1 - 6);
		menu.draw(g, (int) frame.getCenterX(), frame.y + 180);
	}
	
	public ArrayList<Player> getPlayerList() {
		return characterSelect.getPlayerList();
	}

}
