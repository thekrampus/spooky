package ent;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import sys.Animation;
import world.Level;
import world.Tile;

public abstract class Entity implements Comparable<Entity> {
	public static final double FRICTION = 0.6; // lower = stickier
	protected Animation anim;
	protected double xCoord, yCoord, xVel, yVel;
	protected boolean facingLeft = false;

	public Entity(double x, double y, Animation a) {
		this.xCoord = x;
		this.yCoord = y;
		this.anim = a;
		this.xVel = this.yVel = 0;
	}

	/**
	 * Act one "tick" of this entity's routine
	 * 
	 * @param l
	 *            the Level the entity is acting in
	 */
	public void update(Level l) {
		
		xCoord += xVel;
		if (!l.isInBounds(xCoord, yCoord)) {
			xCoord -= xVel;
			xVel = 0;
		} else
			xVel *= FRICTION;

		yCoord += yVel;
		if (!l.isInBounds(xCoord, yCoord)) {
			yCoord -= yVel;
			yVel = 0;
		} else
			yVel *= FRICTION;
	}

	/**
	 * Draw this entity to the screen
	 * 
	 * @param g
	 *            Graphics2D object to draw with
	 */
	public void draw(Graphics2D g) {
		int[] c = Tile.getScreenCoords(xCoord, yCoord);
		BufferedImage frame = null;
		if (Math.abs(xVel) > .005 || Math.abs(yVel) > .005)
			frame = anim.getNextFrame();
		else
			frame = anim.getCurrentFrame();

		if (facingLeft)
			g.drawImage(frame, c[0] - Tile.TILE_WIDTH / 2, c[1] - frame.getHeight() + Tile.TILE_HEIGHT - 10, null);
		else
			g.drawImage(frame, c[0] + Tile.TILE_WIDTH / 2, c[1] - frame.getHeight() + Tile.TILE_HEIGHT - 10, -frame.getWidth(),
					frame.getHeight(), null);
	}

	/**
	 * Kick this entity a given amount on the coordinate axes
	 * 
	 * @param dX
	 *            amount to add to X velocity
	 * @param dY
	 *            amount to add to Y velocity
	 */
	public void impulse(double dX, double dY) {
		xVel += dX;
		yVel += dY;
	}

	public void setCoords(double x, double y) {
		xCoord = x;
		yCoord = y;
	}

	public int[] getScreenCoords() {
		return Tile.getScreenCoords(xCoord, yCoord);
	}

	public double getX() {
		return xCoord;
	}

	public double getY() {
		return yCoord;
	}

	public boolean checkPosition(int x, int y) {
		return (xCoord > x && xCoord <= x + 1 && yCoord > y && yCoord <= yCoord + 1);
	}
	
	@Override
	public int compareTo(Entity arg0) {
		int y = arg0.getScreenCoords()[1];
		return (this.getScreenCoords()[1] - y);
	}
	
	public boolean isAlive() {
		return true;
	}

}
