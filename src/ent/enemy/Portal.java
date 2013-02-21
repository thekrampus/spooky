package ent.enemy;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import sys.Animation;
import sys.AssetLib;
import world.Level;
import world.Tile;
import ent.Flash;

public class Portal extends Enemy {
	public static final int MAX_ENEMIES = 40, SPAWN_DELAY = 100;
	private int timer = SPAWN_DELAY;
	private ArrayList<Enemy> children;

	public Portal(double x, double y) {
		super(x+.5, y+.5, 0, 25, 0, new Animation(AssetLib.SHEET_PORTAL, 0, 6, 120, 59, 12));
		children = new ArrayList<Enemy>();
	}
	
	@Override
	public void update(Level l) {
		if(timer <= 0) {
			if(children.size() < MAX_ENEMIES) {
				System.out.println("Spawning!");
				double x = xCoord + (Math.random()-.5)/2.0;
				double y = yCoord + (Math.random()-.5)/2.0;
				l.addEntity(new Flash(x, y));
				Bat b = new Bat(x, y);
				l.addEntity(b);
				children.add(b);
			}
			timer = SPAWN_DELAY;
		} else
			timer--;
		
		for(int i = 0; i < children.size(); i++) {
			if(!children.get(i).isAlive()) {
				children.remove(i);
				i--;
			}
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		int[] c = Tile.getScreenCoords(xCoord, yCoord);
		BufferedImage frame = anim.getNextFrame();

		g.drawImage(frame, c[0] - frame.getWidth() / 2, c[1] - frame.getHeight() + frame.getHeight() - 50, null);
	}
	
	@Override
	public boolean isAlive() {
		return health > 0;
	}
	
	

}
