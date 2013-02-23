package ent;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import menu.PauseMenu;
import sys.Animation;
import sys.AssetLib;
import sys.Game;
import sys.InputMethod;
import world.Level;

public abstract class Player extends Entity {
	public static final int MAX_HEALTH = 100;
	protected double speed;
	
	private InputMethod input;
	private boolean hold = true;
	private int health, attackTimer, attackDelay;
	private double[] attackVector = {0.,0.};

	public Player(double x, double y, int health, int attackDelay, Animation a, InputMethod input) {
		super(x, y, a);
		this.input = input;
		this.health = health;
		this.attackTimer = this.attackDelay = attackDelay;
	}

	public void handleInput() {
		double[] a = input.stickL();
		double[] b = input.stickR();
		
		impulse(speed*(a[0]+a[1]), speed*(a[0]-a[1]));
		attackVector[0] = b[0]+b[1];
		attackVector[1] = b[0]-b[1];
		
		if(input.activate() && !hold) {
			System.out.println("THWACK");
			hold = true;
		}
	
		if(input.special() && !hold) {
			System.out.println("HUZZAH!");
			hold = true;
		}
		
		if(input.menu() && !hold) {
			int[] sc = this.getScreenCoords();
			Point offset = Game.getCamOffset();
			sc[0] -= offset.x;
			sc[1] -= offset.y;
			Game.pushMenu(new PauseMenu(sc[0], sc[1], input));
		}
		
		if(!input.activate() && !input.special() && !input.menu())
			hold = false;
	}
	
	@Override
	public boolean update(Level l) {
		ArrayList<Entity> hazards = l.getPlayerHazards();
		for(Entity ent : hazards) {
			if(this.getDistance(ent) < radius)
				damage(ent.getDamage());
		}
		
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
		
		xVel *= friction;
		yVel *= friction;
		
		if(attackTimer <= 0) {
			if(!(attackVector[0] == 0 && attackVector[1] == 0)) {
				l.addEntity(fireAttack(attackVector));
				attackVector[0] = 0; attackVector[1] = 0;
				attackTimer = attackDelay;
			}
		} else
			attackTimer--;
		
		return true;
	}
	
	public InputMethod getInput() {
		return input;
	}
	
	@Override
	public boolean isAlive() {
		return health > 0;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void damage(int amount) {
		health -= amount;
		if(health > Player.MAX_HEALTH)
			health = Player.MAX_HEALTH;
	}
	
	protected abstract Attack fireAttack(double[] direction);

	public static class DebugPlayer extends Player {

		public DebugPlayer(double x, double y, InputMethod input) {
			super(x, y, 100, 10, new Animation(AssetLib.SHEET_SKELLY, 0, 2, 129, 205, 10), input);
			speed = 0.02;
		}

		@Override
		protected Attack fireAttack(double[] direction) {
			return new Attack(xCoord, yCoord, direction[0], direction[1], 6, .1, 5, new Animation(AssetLib.SHEET_PROJECTILE, 0, 7, 67, 71, 4));
		}
	}
	
	public static class Warrior extends Player {
		public Warrior(double x, double y, InputMethod input) {
			super(x, y, 100, 10, new Animation(AssetLib.SHEET_SKELLY, 0, 2, 129, 205, 10), input);
			speed = 0.02;
		}

		@Override
		protected Attack fireAttack(double[] direction) {
			return new Attack(xCoord, yCoord, direction[0], direction[1], 4, .09, 4,
					new Animation(AssetLib.SHEET_PROJECTILE, 0, 7, 70, 70, 4));
		}
	}
	
	public static class Rogue extends Player {

		public Rogue(double x, double y, InputMethod input) {
			super(x, y, 70, 5, new Animation(AssetLib.SHEET_GHOST, 0, 2, 139, 107, 20), input);
			speed = 0.03;
		}

		@Override
		protected Attack fireAttack(double[] direction) {
			return new Attack(xCoord, yCoord, direction[0], direction[1], 2, .1, 2,
					new Animation(AssetLib.SHEET_PROJECTILE, 0, 7, 70, 70, 4));
		}
		
		@Override
		public void draw(Graphics2D g) {
			this.draw(70, g);
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
