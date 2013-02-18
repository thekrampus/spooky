package ent;

import java.awt.Graphics2D;

import sys.Animation;
import sys.AssetLib;
import sys.Game;
import sys.InputMethod;
import world.Level;

public abstract class Player extends Entity {
	protected double speed;
	
	private InputMethod input;

	public Player(double x, double y, Animation a, InputMethod input) {
		super(x, y, a);
		this.input = input;
		// There will be more here but I'm not sure what it is!!
	}

	public void handleInput() {
		double[] a = input.stickL();
		
		impulse(speed*(a[1]+a[0]), speed*(a[1]-a[0]));
	}
	
	@Override
	public void update(Level l) {
;		xCoord += xVel;
		if (!l.isInBounds(xCoord, yCoord) || !Game.panX(xCoord, yCoord)) {
			xCoord -= xVel;
			xVel = 0;
		} else
			xVel *= FRICTION;

		yCoord += yVel;
		if (!l.isInBounds(xCoord, yCoord) || !Game.panY(xCoord, yCoord)) {
			yCoord -= yVel;
			yVel = 0;
		} else
			yVel *= FRICTION;
	}

	public static class DebugPlayer extends Player {

		public DebugPlayer(double x, double y, InputMethod input) {
			super(x, y, new Animation(AssetLib.SHEET_SKELLY, 0, 2, 129, 205, 10), input);
			speed = 0.02;
		}

		public void draw(Graphics2D g) {
			super.draw(g);
		}

	}
}
