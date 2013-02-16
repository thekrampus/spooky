package sys;

import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final long framediff = 1000L / 100L;
	private long timer;
	private BufferStrategy buffer;
	private KeyCap keys;
	private boolean running = true; // Set this to false when you decide the game is over!

	public Game() {
		super("SPOOKY DUNGEON REVENGE v3");

		this.setSize(1024, 768);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// this.setUndecorated(true);

		// put that shit in the middle of the goddamn screen
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		center.translate(-this.getWidth() / 2, -this.getHeight() / 2);
		this.setLocation(center);

		// initialize keyboard capture
		keys = new KeyCap();
		this.setFocusable(true);
		this.addKeyListener(keys);
		this.requestFocus();

		timer = System.currentTimeMillis();
		this.setVisible(true);

		// initialize frame buffer
		this.createBufferStrategy(2);
		buffer = this.getBufferStrategy();
	}

	/**
	 * Run game loop until running is somehow false
	 */
	public void run() {
		while (running) {
			input();
			logic();
			draw();
			timing();
		}
	}

	/**
	 * Synchronize game to a predefined framerate
	 */
	private void timing() {
		try {
			long wait = timer - System.currentTimeMillis();
			if (wait > 0)
				Thread.sleep(wait);
			timer = System.currentTimeMillis() + framediff;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("How on earth did this happen? Yell at the dev!");
		}
	}

	/**
	 * Throw game at screen
	 */
	private void draw() {
		Graphics2D g2d = (Graphics2D) buffer.getDrawGraphics(); // Graphics object to draw with
		g2d.clearRect(0, 0, 1024, 768);

		// draw things
		g2d.drawString("SPOOKY DUNGEON - pre-alpha available to paying customers only!!", 100, 200);

		g2d.dispose();

		// draw buffer to screen if it's ready
		if (!buffer.contentsLost())
			buffer.show();
	}

	/**
	 * Throw players at game
	 */
	private void logic() {
		// TODO Auto-generated method stub

	}

	/**
	 * Throw input polling at players
	 */
	private void input() {
		// TODO Auto-generated method stub

	}

}
