package ent.enemy;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import sys.Animation;
import sys.AssetLib;
import world.Level;
import world.Tile;

public class Bat extends Enemy {

	public Bat(double x, double y) {
		// super(x, y, .1, 10, 5, new Animation(AssetLib.SHEET_BAT, 0, 2, 180, 340, 4));
		super(x, y, .1, 10, 5, new Animation(AssetLib.SHEET_BAT, 0, 2, 58, 110, 4));
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void update(Level l) {
		this.impulse((Math.random()-.5)*speed, (Math.random()-.5)*speed);
		if(xVel < -.05)
			facingLeft = true;
		else if(xVel > .05)
			facingLeft = false;
		
		super.update(l);
	}
	
	@Override
	public void draw(Graphics2D g) {
//		int[] c = Tile.getScreenCoords(xCoord, yCoord);
//		g.drawString("(BAT)", c[0], c[1]);
		int[] c = Tile.getScreenCoords(xCoord, yCoord);
		BufferedImage frame = anim.getNextFrame();

		if (!facingLeft)
			g.drawImage(frame, c[0] - frame.getWidth() / 2, c[1] - frame.getHeight(), null);
		else
			g.drawImage(frame, c[0] + frame.getWidth() / 2, c[1] - frame.getHeight(), -frame.getWidth(),
					frame.getHeight(), null);
	}
	
	@Override
	public int getDamage() {
		this.health = 0;
		return super.getDamage();
	}
	
	public static class BatPortal extends Portal {

		public BatPortal(double x, double y) {
			super(x, y, 150, 15);
		}

		@Override
		protected Enemy createEnemy() {
			return new Bat(xCoord, yCoord);
		}
		
	}

}
