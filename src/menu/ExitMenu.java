package menu;

import java.awt.Color;
import java.awt.Graphics2D;

import sys.AssetLib;
import sys.InputMethod;

public class ExitMenu extends MenuFrame {
	public static final int WIDTH = 200, HEIGHT = 200;
	private InputMethod input;
	private Menu menu;

	public ExitMenu(int x, int y, InputMethod input) {
		super(x - WIDTH/2, y - HEIGHT / 2, WIDTH, HEIGHT);
		
		this.input = input;
		
		menu = new Menu();
		
		menu.addItem(new MenuItem("nah") {
			public void activate() {
				// nahhhhhhhhh
				alive = false;
			}
		});

		menu.addItem(new MenuItem("yeah") {
			public void activate() {
				System.exit(0);
			}
		});
	}

	@Override
	public void control() {
		boolean up, down, select;
		up = down = select = false;
		
		if (input.stickL()[1] < -.5)
			up = true;
		if (input.stickL()[1] > .5)
			down = true;
		if (input.attack())
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
		g.drawString("Woah,", frame.x+6, frame.y+36);
		g.drawString("Really?", frame.x+6, frame.y+76);
		g.setFont(AssetLib.FONT_SMALL);
		menu.draw(g, (int) frame.getCenterX(), frame.y + 120);
	}

}
