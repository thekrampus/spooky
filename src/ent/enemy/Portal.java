package ent.enemy;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import sys.Animation;
import sys.AssetLib;
import world.Level;
import world.Tile;
import ent.Entity;
import ent.Flash;

public abstract class Portal extends Enemy {
	private int timer, spawnDelay, maxChildren;
	private ArrayList<Enemy> children;

	public Portal(double x, double y, int spawnRate, int maxChildren) {
		super(x+.5, y+.5, 0, 80, 0, new Animation(AssetLib.SHEET_PORTAL, 0, 6, 120, 59, 12));
		children = new ArrayList<Enemy>();
		this.timer = this.spawnDelay = spawnRate;
		this.maxChildren = maxChildren;
		
		damtype = DamageType.HARMLESS;
	}
	
	@Override
	public void update(Level l) {
		ArrayList<Entity> hazards = l.getEnemyHazards();
		for(Entity ent : hazards) {
			if(this.getDistance(ent) < radius)
				health -= ent.getDamage();
		}
		
		if(timer <= 0) {
			if(children.size() < maxChildren) {
				System.out.println("Spawning!");
				double x = xCoord + (Math.random()-.5)/2.0;
				double y = yCoord + (Math.random()-.5)/2.0;
				l.addEntity(new Flash(x, y));
				Enemy enemy = createEnemy();
				l.addEntity(enemy);
				children.add(enemy);
			}
			timer = spawnDelay;
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

		g.drawImage(frame, c[0] - frame.getWidth() / 2, c[1] - 50, null);
	}
	
	@Override
	public boolean isAlive() {
		return health > 0;
	}
	
	protected abstract Enemy createEnemy();
}
