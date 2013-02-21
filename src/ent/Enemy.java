package ent;

import sys.Animation;
import world.Level;

public class Enemy extends Entity {
	protected double speed;
	protected int health, damage;

	public Enemy(double x, double y, double speed, int maxHealth, Animation a) {
		super(x, y, a);
		this.speed = speed;
		this.health = maxHealth;
	}
	
	@Override
	public boolean update(Level l) {
		super.update(l);
		
		return health > 0;
	}

}
