package ent;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import sys.Animation;
import sys.AssetLib;
import world.Level;
import world.Tile;

public class Portal extends Entity {
	public static final int MAX_ENEMIES = 10, SPAWN_DELAY = 100, MAX_HEALTH = 10;
	private int health = MAX_HEALTH, timer = SPAWN_DELAY;

	public Portal(double x, double y) {
		super(x+.5, y+.5, new Animation(AssetLib.SHEET_PORTAL, 0, 6, 120, 59, 12));
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean update(Level l) {
		if(timer <= 0) {
			System.out.println("Spawning!");
			l.addEntity(new Flash(xCoord + (Math.random()-.5)/2.0, yCoord + (Math.random()-.5)/2.0));
			timer = SPAWN_DELAY;
		} else
			timer--;
		
		return health > 0;
	}
	
	@Override
	public void draw(Graphics2D g) {
		int[] c = Tile.getScreenCoords(xCoord, yCoord);
		BufferedImage frame = anim.getNextFrame();

		g.drawImage(frame, c[0] - frame.getWidth() / 2, c[1] - frame.getHeight() + frame.getHeight() - 50, null);
	}

}
