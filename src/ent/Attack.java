package ent;

import java.awt.Graphics2D;

import sys.Animation;
import world.Level;

public class Attack extends Entity {
	private double range, step;
	private int damage;

	public Attack(double x, double y, double xV, double yV, double range, double speed, int damage, Animation a) {
		super(x, y, a);

		this.range = range;
		this.damage = damage;

		double r = Math.sqrt(xV * xV + yV * yV);
		if (r != 0)
			impulse(speed * (xV / r), speed * (yV / r));
		else
			this.range = 0;

		step = Math.sqrt(xVel * xVel + yVel * yVel);
	}

	@Override
	public void update(Level l) {
		xCoord += xVel;
		yCoord += yVel;
		range -= step;

		if (!l.isInBounds(xCoord, yCoord))
			range = 0;
	}
	
	@Override
	public void draw(Graphics2D g) {
		this.draw(80, g);
	}

	@Override
	public boolean isAlive() {
		return range > 0;
	}

}
