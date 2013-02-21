package ent;

import java.awt.Graphics2D;

import menu.PauseMenu;
import sys.Animation;
import sys.AssetLib;
import sys.Game;
import sys.InputMethod;
import world.Level;

public abstract class Player extends Entity {
	protected double speed;
	
	private InputMethod input;
	private boolean hold = true;
	private int health;

	public Player(double x, double y, int health, Animation a, InputMethod input) {
		super(x, y, a);
		this.input = input;
		this.health = health;
	}

	public void handleInput() {
		double[] a = input.stickL();
		
		impulse(speed*(a[0]+a[1]), speed*(a[0]-a[1]));
		
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
		
		if(input.menu()) {
			//int[] sc = this.getScreenCoords();
			//Game.pushMenu(new PauseMenu(new Point(sc[0], sc[1]), input));
			Game.pushMenu(new PauseMenu(input));
		}
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
	
	public InputMethod getInput() {
		return input;
	}
	
	@Override
	public boolean isAlive() {
		return health > 0;
	}

	public static class DebugPlayer extends Player {

		public DebugPlayer(double x, double y, InputMethod input) {
			super(x, y, 100, new Animation(AssetLib.SHEET_SKELLY, 0, 2, 129, 205, 10), input);
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
