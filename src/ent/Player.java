package ent;

import java.awt.Graphics2D;

import sys.Animation;
import sys.AssetLib;
import sys.KeyCap;

public abstract class Player extends Entity {
	protected double speed;

	public Player(double x, double y, Animation a) {
		super(x, y, a);
		// There will be more here but I'm not sure what it is!!
	}

	public void handleInput(KeyCap keys) {
		if (keys.key_w) {
			impulse(-speed, speed);
		} else if (keys.key_s) {
			impulse(speed, -speed);
		}

		if (keys.key_a) {
			impulse(-speed, -speed);
			facingLeft = true;
		} else if (keys.key_d) {
			impulse(speed, speed);
			facingLeft = false;
		}
	}

	public static class DebugPlayer extends Player {

		public DebugPlayer(double x, double y) {
			super(x, y, new Animation(AssetLib.SHEET_SKELLY, 0, 2, 129, 205, 10));
			speed = 0.02;
		}

		public void draw(Graphics2D g) {
			super.draw(g);
		}

	}
}
