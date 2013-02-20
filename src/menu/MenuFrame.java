package menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class MenuFrame {
	protected Rectangle frame;
	protected boolean alive = true, hold = true;
	
	public MenuFrame(int x, int y, int width, int height) {
		frame = new Rectangle(x, y, width, height);
	}
	
	public void draw(Graphics2D g) {
		//TODO: In the near future let's be super cool and texture this
		
		g.setColor(Color.pink);
		g.fill(frame);
	}
	
	public abstract void control();
	
	public boolean isAlive() {
		return alive;
	}

}
