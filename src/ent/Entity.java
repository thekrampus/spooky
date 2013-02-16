package ent;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import sys.Animation;
import world.Level;
import world.Tile;

public abstract class Entity {
	public static final double FRICTION = 0.5; //lower = stickier
	private Animation anim;
	protected double xCoord, yCoord, xVel, yVel;
	
	public Entity(double x, double y, Animation a) {
		this.xCoord = x;
		this.yCoord = y;
		this.anim = a;
		this.xVel = this.yVel = 0;
	}
	
	public void update(Level l) {
		xCoord += xVel;
		if(!l.isInBounds(xCoord, yCoord)) {
			xCoord -= xVel;
			xVel = 0;
		} else
			xVel *= FRICTION;
		
		yCoord += yVel;
		if(!l.isInBounds(xCoord, yCoord)) {
			yCoord -= yVel;
			yVel = 0;
		} else
			yVel *= FRICTION;
	}
	
	public void draw(Graphics2D g) {
		int[] c = Tile.getScreenCoords(xCoord, yCoord);
		BufferedImage frame = anim.getFrame(0);
		g.drawImage(frame, c[0]-Tile.TILE_WIDTH/2, c[1]-(frame.getHeight()-Tile.TILE_HEIGHT/2), null);
	}
	
	public void impulse(double dX, double dY) {
		xVel += dX;
		yVel += dY;
	}
	
	public void setCoords(double x, double y) {
		xCoord = x;
		yCoord = y;
	}
	
	public boolean checkPosition(int x, int y) {
		return (xCoord > x && xCoord <= x+1 && yCoord > y && yCoord <= yCoord+1);
	}
	
}