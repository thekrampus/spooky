package ent.enemy;

import java.util.ArrayList;

import sys.Animation;
import world.Level;
import ent.Entity;

public class Enemy extends Entity {
	protected double speed;
	protected int health;

	public Enemy(double x, double y, double speed, int maxHealth, int damage, Animation a) {
		super(x, y, a);
		this.speed = speed;
		this.health = maxHealth;
		this.damage = damage;
		damtype = DamageType.HURTS_PLAYERS;
	}
	
	@Override
	public void update(Level l) {
		ArrayList<Entity> hazards = l.getEnemyHazards();
		for(Entity ent : hazards) {
			if(this.getDistance(ent) < radius)
				health -= ent.getDamage();
		}
	}
	
	@Override
	public boolean isAlive() {
		return health > 0;
	}

}
