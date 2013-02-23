package sys;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JFrame;

import menu.MenuFrame;
import menu.StartMenu;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import world.Level;
import world.Tile;
import ent.Entity;
import ent.Player;

public class Game extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final long framediff = 1000L / 60L;
	private long timer, mspf;
	private BufferStrategy buffer;
	private KeyCap keys;
	private static boolean running; // Set this to false when you decide the game is over!
	private static boolean paused = false; // Game logic will only run when this is false - drawing will still occur, though!

	private static Level level;
	private static ArrayList<Player> players;
	protected static Stack<MenuFrame> menuStack;

	private static Rectangle cambox;
	private static final int CAM_BUFFER = 200; // How close a player needs to be to the edge of the screen to start panning

	public static int centerX, centerY;
	
	private static String levelDir;
	private static int levelNum;

	public Game() {
		super("WHERE'S MY SUPERSUIT?!");

		cambox = new Rectangle(0, 0, 0, 0);
		this.setSize(1024, 768);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(true);

		// put that shit in the middle of the goddamn screen
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		center.translate(-this.getWidth() / 2, -this.getHeight() / 2);
		this.setLocation(center);

		// initialize keyboard capture
		keys = new KeyCap();
		this.setFocusable(true);
		this.addKeyListener(keys);
		this.requestFocus();

		// initialize frame-lock
		timer = System.currentTimeMillis();
		mspf = 1;
		this.setVisible(true);

		// initialize frame buffer
		this.createBufferStrategy(2);
		buffer = this.getBufferStrategy();

		// initialize menu background level
		ArrayList<GamepadCap> gamepads = initControllers();
		players = new ArrayList<Player>();
		players.add(new Player.DummyPlayer(2, 2));
		players.add(new Player.DummyPlayer(4, 4));

		Game.loadLevel("data/levels/menuscene.lvl", players);

		// initialise start menu
		ArrayList<InputMethod> allInputs = new ArrayList<InputMethod>();
		allInputs.add(keys);
		allInputs.addAll(gamepads);
		menuStack = new Stack<MenuFrame>();
		menuStack.push(new StartMenu(allInputs));

		cambox.setLocation(160, -200);
		running = true;
	}

	public Game(ArrayList<Player> playerList) {
		super("I uttered a scream at the figure in white with a silver GameBoy in his palm, and like a sharp, sudden jolt of four-bit lightening, he brought light into this world");

		cambox = new Rectangle(0, 0, 0, 0);
		this.setSize(1024, 768);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(true);

		// put that shit in the middle of the goddamn screen
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		center.translate(-this.getWidth() / 2, -this.getHeight() / 2);
		this.setLocation(center);

		// initialize frame-lock
		timer = System.currentTimeMillis();
		mspf = 1;
		this.setVisible(true);

		// initialize frame buffer
		this.createBufferStrategy(2);
		buffer = this.getBufferStrategy();

		// init level, inputs
		this.setFocusable(true);
		ArrayList<InputMethod> allInputs = new ArrayList<InputMethod>();
		players = playerList;
		for (Player p : players) {
			level.addEntity(p);
			InputMethod in = p.getInput();
			allInputs.add(in);
			if (in instanceof KeyCap)
				this.addKeyListener((KeyCap) in);
		}
		
		//Game.loadLevel("data/levels/debug.lvl", players);
		Game.startLevelSet("data/levels/demo/");

		this.requestFocus();
		
		menuStack = new Stack<MenuFrame>();

		cambox.setLocation(50, -300);
		running = true;
	}

	public static ArrayList<GamepadCap> initControllers() {
		Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
		ArrayList<GamepadCap> gp = new ArrayList<GamepadCap>();

		for (Controller c : controllers) {
			System.out.println(c.getType());
			if (c.getType() == Controller.Type.GAMEPAD || c.getType() == Controller.Type.STICK)
				gp.add(new GamepadCap(c));
		}

		return gp;
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
		
		this.setVisible(false);
		this.dispose();
	}

	/**
	 * Synchronize game to a predefined framerate
	 */
	private void timing() {
		try {
			long wait = timer - System.currentTimeMillis();
			if (wait > 0)
				Thread.sleep(wait);
			mspf = System.currentTimeMillis() - (timer - framediff);
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
		//g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

		// draw fps counter
		g2d.setColor(Color.blue);
		g2d.drawString("FPS: " + (1000 / mspf), 20, 20);

		// draw things
		g2d.translate(-(cambox.getX() - CAM_BUFFER), -(cambox.getY() - CAM_BUFFER));
		level.draw(g2d);

		//draw player health bars
		for(Player p : players) {
			int[] c = p.getScreenCoords();
			c[0] -= Player.MAX_HEALTH/4;
			c[1] += Tile.TILE_HEIGHT/2+10;
			int h = p.getHealth();
			
			if(h < Player.MAX_HEALTH/4)
				g2d.setColor(Color.red);
			else
				g2d.setColor(Color.green);
			
			g2d.fillRect(c[0], c[1], h/2, 6);
			
			g2d.setColor(Color.black);
			g2d.drawRect(c[0], c[1], Player.MAX_HEALTH/2, 6);
		}
		
		// g2d.dispose();
		// g2d = (Graphics2D) buffer.getDrawGraphics();
		g2d.translate((cambox.getX() - CAM_BUFFER), (cambox.getY() - CAM_BUFFER));
		// draw all the menus!
		for (MenuFrame menu : menuStack) {
			menu.draw(g2d);
		}

		g2d.dispose();

		// draw buffer to screen if it's ready
		if (!buffer.contentsLost())
			buffer.show();
	}

	/**
	 * Throw players at game
	 */
	private void logic() {
		if (!paused)
			level.update();
	}

	/**
	 * Throw input polling at players
	 */
	private void input() {
		if (menuStack.isEmpty()) {
			if(!paused) {
				for (Player p : players)
					p.handleInput();
			}
		} else {
			if (!menuStack.peek().isAlive())
				menuStack.pop();
			else
				menuStack.peek().control();
		}
	}

	/**
	 * Given the x and y screen coords of an entity to track, attempts to pan the camera suitably, and if that's impossible to resolve with
	 * other tracked entities then returns false
	 * 
	 * @param e
	 *            Entity being tracked
	 * @return false if the camera cannot be resolved
	 */
	public static boolean trackCam(Entity e) {
		int[] c = e.getScreenCoords();
		double dX = 0;
		if (c[0] < cambox.getX())
			dX = c[0] - cambox.getX() - 2;
		else if (c[0] > cambox.getMaxX())
			dX = c[0] - cambox.getMaxX() + 2;

		double dY = 0;
		if (c[1] < cambox.getY())
			dY = c[1] - cambox.getY() - 3;
		else if (c[1] > cambox.getMaxY())
			dY = c[1] - cambox.getMaxY() + 3;

		cambox.translate((int) dX, (int) dY);

		for (Player p : players) {
			if (p != e) {
				int[] pc = p.getScreenCoords();
				if (!cambox.contains(pc[0], pc[1])) {
					// System.out.println("Can't pan cam! " + pc[0] + ", " + pc[1]);
					cambox.translate((int) -dX, (int) -dY);
					return false;
				}
			}
		}

		return true;
	}

	@Deprecated
	/**
	 * Pans the camera, but only does checking on the X axis. Better for how we're doing seperate-axis integration
	 * 
	 * @param x
	 *            X coordinate of the tracked entity
	 * @param y
	 *            Y coordinate of the tracked entity
	 * @return false if the camera cannot be resolved
	 */
	public static boolean panX(double x, double y) {
		double s = (y * Tile.TILE_WIDTH / 2) + (x * Tile.TILE_WIDTH / 2);
		double d = 0;
		if (s < cambox.getX())
			d = s - cambox.getX();
		else if (s > cambox.getMaxX())
			d = s - cambox.getMaxX() + 1;

		cambox.x += d;

		for (Player p : players) {
			int[] c = p.getScreenCoords();
			if (c[0] < cambox.getX() || c[0] > cambox.getMaxX()) {
				System.out.println("Can't pan cam!!");
				cambox.translate((int) -d, 0);
				return false;
			}
		}

		return true;
	}

	@Deprecated
	/**
	 * Pans the camera, but only does checking on the Y axis. Better for how we're doing seperate-axis integration
	 * 
	 * @param x
	 *            X coordinate of the tracked entity
	 * @param y
	 *            Y coordinate of the tracked entity
	 * @return false if the camera cannot be resolved
	 */
	public static boolean panY(double x, double y) {
		double s = (x * Tile.TILE_HEIGHT / 2) - (y * Tile.TILE_HEIGHT / 2);
		double d = 0;
		if (s < cambox.getY())
			d = s - cambox.getY();
		else if (s > cambox.getMaxY())
			d = s - cambox.getMaxY() + 1;

		cambox.y += d;

		for (Player p : players) {
			int[] c = p.getScreenCoords();
			if (c[1] < cambox.getY() || c[1] > cambox.getMaxY()) {
				System.out.println("Can't pan cam!!");
				cambox.translate(0, (int) -d);
				return false;
			}
		}

		return true;
	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		cambox.setSize(width - 2 * CAM_BUFFER, height - 2 * CAM_BUFFER);
		centerX = width / 2;
		centerY = height / 2;
	}

	public static void setPaused(boolean state) {
		paused = state;
	}

	public static void pushMenu(MenuFrame menu) {
		menuStack.push(menu);
	}
	
	public static void stopAllInstances() {
		running = false;
	}
	
	public static Point getCamOffset() {
		return cambox.getLocation();
	}
	
	public static boolean loadLevel(String path, ArrayList<Player> p) {
		level = new Level(p);
		if(level.loadFile(new File(path))) {
			level.buildLevelProps();
			return true;
		} else {
			return false;
		}
	}
	
	public static void startLevelSet(String dir) {
		levelNum = 1;
		levelDir = dir;
		if(loadLevel(levelDir + levelNum + ".lvl", players))
			System.out.println("Starting new levelset...");
		else {
			System.out.println("Bad levelset dir, exiting");
			System.exit(0);
		}			
	}
	
	public static void nextLevel() {
		for(Player p : players) {
			double spawnX = Math.random() + 1;
			double spawnY = Math.random() + 1;
			p.setCoords(spawnX, spawnY);
			p.damage(-Player.MAX_HEALTH/3);
		}
		
		levelNum++;
		if(loadLevel(levelDir + levelNum + ".lvl", players))
			System.out.println("Next level loaded!");
		else {
			System.out.println("Levelset over...");
			System.exit(0);
		}
	}

}
