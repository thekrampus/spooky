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
	private boolean hold = false;

	public Player(double x, double y, Animation a, InputMethod input) {
		super(x, y, a);
		this.input = input;
		// There will be more here but I'm not sure what it is!!
	}

	public void handleInput() {
		double[] a = input.stickL();
		
		impulse(speed*(a[1]+a[0]), speed*(a[1]-a[0]));
		
		if(input.attack() && !hold) {
			System.out.println("THWACK");
			hold = true;
		}
	
		if(input.special() && !hold) {
			System.out.println("HUZZAH!");
			hold = true;
		}
		
		if(!input.attack() && !input.special())
			hold = false;
	}
	
	@Override
	public void update(Level l) {
		if(xVel > 0.01)
			facingLeft = false;
		else if(xVel < -0.01)
			facingLeft = true;
		
		xCoord += xVel;
		if (!l.isInBounds(xCoord, yCoord)) {
			xCoord -= xVel;
			xVel = 0;
		}

		yCoord += yVel;
		if (!l.isInBounds(xCoord, yCoord)) {
			yCoord -= yVel;
			yVel = 0;
		}
		
		if(!Game.trackCam(this)) {
			xCoord -= xVel;
			yCoord -= yVel;
		}
		
		xVel *= FRICTION;
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
	
	public static class DummyPlayer extends DebugPlayer {

		public DummyPlayer(double x, double y) {
			super(x, y, null);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void handleInput() {
			//Wooo spooky!
		}

		
	}
}
