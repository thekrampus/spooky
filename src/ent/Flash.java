package ent;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import sys.Animation;
import sys.AssetLib;
import world.Level;
import world.Tile;

public class Flash extends Entity{
	private int timer;

	public Flash(double x, double y) {
		super(x, y, new Animation(AssetLib.SHEET_FLASH, 0, 22, 211, 109, 4));
		timer = 22 * 4;
	}
	
	@Override
	public void update(Level l) {
		timer--;
	}
	
	@Override
	public void draw(Graphics2D g) {
		int[] c = Tile.getScreenCoords(xCoord, yCoord);
		BufferedImage frame = anim.getNextFrame();

		g.drawImage(frame, c[0] - frame.getWidth() / 2, c[1] - frame.getHeight() + frame.getHeight() - 50, null);
	}
	
	@Override
	public boolean isAlive() {
		return timer > 0;
	}

}
