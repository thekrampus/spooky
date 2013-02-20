package menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import sys.AssetLib;
import sys.Game;
import sys.InputMethod;
import sys.Launcher;

public class StartMenu extends MenuFrame {
	public static final int WIDTH = 200, HEIGHT = 400;

	private Menu menu;
	private ArrayList<InputMethod> allInputs;

	public StartMenu(ArrayList<InputMethod> allInputs) {
		super(Game.centerX - WIDTH / 2, Game.centerY - HEIGHT / 2, WIDTH, HEIGHT);

		this.allInputs = allInputs;

		menu = new Menu();

		menu.addItem(new MenuItem("new game") {
			public void activate() {
				// uhhhhhhhhhhhh
				alive = false;
			}
		});

		menu.addItem(new MenuItem("options") {
			public void activate() {
				System.out.println("Aint no mothafuckin options!");
				alive = false;
			}
		});

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
			if (in.attack())
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
		g.drawString("Dungeon", frame.x+6, frame.y+76);
		g.setFont(AssetLib.FONT_SMALL);
		g.drawString("v" + Launcher.VERSION_NUM, frame.x+3, frame.y + (int)frame.getHeight() - 1 - 24);
		g.drawString("Made for Tech Game Jam 1", frame.x+3, frame.y + (int)frame.getHeight() - 1 - 6);
		menu.draw(g, (int) frame.getCenterX(), frame.y + 180);
	}

}
