package ent.enemy;

import ent.Entity;
import sys.Animation;
import world.Level;

public class Enemy extends Entity {
	protected double speed;
	protected int health, damage;

	public Enemy(double x, double y, double speed, int maxHealth, int damage, Animation a) {
		super(x, y, a);
		this.speed = speed;
		this.health = maxHealth;
	}
	
	@Override
	public void update(Level l) {
		super.update(l);
	}
	
	@Override
	public boolean isAlive() {
		return health > 0;
	}

}
