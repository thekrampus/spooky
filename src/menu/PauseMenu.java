package menu;

import java.awt.Color;
import java.awt.Graphics2D;

import sys.AssetLib;
import sys.Game;
import sys.InputMethod;

public class PauseMenu extends MenuFrame {
	public static final int WIDTH = 200, HEIGHT = 200;
	private InputMethod input;
	private Menu menu;

	public PauseMenu(int x, int y, InputMethod input) {
		super(x - WIDTH/2, y - HEIGHT / 2, WIDTH, HEIGHT);
		Game.setPaused(true);

		this.input = input;
		
		menu = new Menu();
		
		menu.addItem(new MenuItem("continue") {
			public void activate() {
				// nyehhhhhhhhhhhh
				Game.setPaused(false);
				alive = false;
			}
		});

		menu.addItem(new MenuItem("exit") {
			public void activate() {
				//confirm exit
				Game.pushMenu(new ExitMenu((int)frame.getCenterX()+30, (int)frame.getCenterY()+30, getInput()));
			}
		});	
	}
	
	private InputMethod getInput() {
		return input;
	}

	@Override
	public void control() {
		boolean up, down, select;
		up = down = select = false;
		
		if (input.stickL()[1] < -.5)
			up = true;
		if (input.stickL()[1] > .5)
			down = true;
		if (getInput().attack())
			select = true;

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
		g.drawString("Paused", frame.x+6, frame.y+36);
		g.setFont(AssetLib.FONT_SMALL);
		g.drawString("Chill out bro", frame.x+3, frame.y + (int)frame.getHeight() - 1 - 6);
		menu.draw(g, (int) frame.getCenterX(), frame.y + 80);
	}

}
