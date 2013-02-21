package world;

import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import ent.Entity;

public class Level {
	private Tile[][] tiles;
	private Area hitbox;
	private ArrayList<Entity> entities;
	private BufferedImage background;
	private int bgOffset;

	public Level() {
		entities = new ArrayList<Entity>();
	}

	public Level(int size) {
		tiles = new Tile[size][size];
		entities = new ArrayList<Entity>();

		for (int i = 0; i < size; i++)
			Arrays.fill(tiles[i], Tile.EMPTY);
	}

	/**
	 * Draw all the stuff in the level to the screen
	 * 
	 * @param g
	 *            Graphics object to draw with
	 */
	public void draw(Graphics2D g) {
		g.drawImage(background, 0, -bgOffset, null);

		Collections.sort(entities);
		for (Entity e : entities) {
			e.draw(g);
		}

		/* Uncomment to enable GOTTA GO SLOW mode */
		// for (int i = 0; i < tiles.length; i++) {
		// for (int j = tiles[i].length - 1; j >= 0; j--) {
		// Tile.draw(tiles[i][j], g, i, j);
		//
		// for(Entity e : entities) {
		// if(e.checkPosition(i, j))
		// e.draw(g);
		// }
		// }
		// }
	}

	public void setTile(int x, int y, Tile tile) {
		tiles[x][y] = tile;
	}

	@Deprecated
	/**
	 * Build the hitbox for the current level by polling the hitboxen of all the tiles
	 */
	public void buildHitbox() {
		hitbox = new Area();
		for (int i = 0; i < tiles.length; i++)
			for (int j = 0; j < tiles[i].length; j++)
				hitbox.add(Tile.getHitbox(tiles[i][j], i, j));
	}

	@Deprecated
	/**
	 * Render the background image
	 */
	public void buildBackground() {
		int width = Tile.getScreenCoords(tiles.length, tiles[0].length)[0];
		int height = Tile.getScreenCoords(tiles.length, 0)[1] - Tile.getScreenCoords(0, tiles[0].length)[1] + 4*Tile.TILE_HEIGHT;
		bgOffset = height / 2;
		background = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = background.createGraphics();
		g.translate(0, bgOffset);
		for (int i = 0; i < tiles.length; i++)
			for (int j = tiles[i].length - 1; j >= 0; j--)
				Tile.draw(tiles[i][j], g, i, j);
	}
	
	public void buildLevelProps() {
		hitbox = new Area();
		
		int width = Tile.getScreenCoords(tiles.length, tiles[0].length)[0];
		int height = Tile.getScreenCoords(tiles.length, 0)[1] - Tile.getScreenCoords(0, tiles[0].length)[1] + 4*Tile.TILE_HEIGHT;
		bgOffset = height / 2;
		background = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = background.createGraphics();
		g.translate(0, bgOffset);
		for (int i = 0; i < tiles.length; i++) {
			for (int j = tiles[i].length - 1; j >= 0; j--) {
				Tile.draw(tiles[i][j], g, i, j);
				hitbox.add(Tile.getHitbox(tiles[i][j], i, j));
				Entity ent = Tile.pullEnt(tiles[i][j], i, j);
				if(ent != null)
					entities.add(ent);
			}
		}
	}

	/**
	 * Tests if a given isometric coordinate falls within the boundaries of the level
	 * 
	 * @param x
	 *            isometric X coord
	 * @param y
	 *            isometric Y coord
	 * @return true if the given point is within level boundaries
	 */
	public boolean isInBounds(double x, double y) {
		return hitbox.contains(x, y);
	}

	public void addEntity(Entity ent) {
		entities.add(ent);
	}

	/**
	 * One logical "tick" of the level
	 */
	public void update() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update(this);
		}
	}

	@Deprecated
	public static Level buildDebug() {
		Level l = new Level();
		l.loadFile(new File("data/levels/debug-huge.lvl"));
		return l;
	}

	/**
	 * Try and load a Level from a file path
	 * 
	 * @param path
	 *            Path of file to load
	 * @return Level loaded from file
	 */
	public static Level loadLevel(String path) {
		Level l = new Level();
		if(l.loadFile(new File(path))) {
//			l.buildHitbox();
//			l.buildBackground();
			l.buildLevelProps();
			return l;
		} else {
			System.exit(0);
			return null;
		}
			
	}

	/**
	 * Load a tile matrix from a file!
	 * 
	 * @param f
	 *            File to load
	 * @return true if file loaded successfully
	 */
	public boolean loadFile(File f) {
		try {
			ObjectInput in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
			tiles = (Tile[][]) in.readObject();

			in.close();
		} catch (Exception e) {
			System.out.println("Couldn't open file!");
			e.printStackTrace();
			return false;
		}
		System.out.println("Read from file successfully!");
		return true;
	}
}
