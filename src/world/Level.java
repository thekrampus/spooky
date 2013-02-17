package world;

import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import ent.Entity;

public class Level {
	private Tile[][] tiles;
	private Area hitbox;
	private ArrayList<Entity> entities;
	private BufferedImage background;
	private int bgOffset;

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
		// for(int i = 0; i < tiles.length; i++) {
		// for(int j = tiles[i].length-1; j >= 0; j--) {
		// Tile.draw(tiles[i][j], g, i, j);
		//
		// for(Entity e : entities) {
		// if(e.checkPosition(i, j))
		// e.draw(g);
		// }
		// }
		// }
		g.drawImage(background, 0, -bgOffset, null);

		for (Entity e : entities) {
			e.draw(g);
		}
	}

	public void setTile(int x, int y, Tile tile) {
		tiles[x][y] = tile;
	}

	/**
	 * Build the hitbox for the current level by polling the hitboxen of all the tiles
	 */
	public void buildHitbox() {
		hitbox = new Area();
		for (int i = 0; i < tiles.length; i++)
			for (int j = 0; j < tiles[i].length; j++)
				hitbox.add(Tile.getHitbox(tiles[i][j], i, j));
	}

	/**
	 * Render the background image
	 */
	public void buildBackground() {
		bgOffset = Tile.getScreenCoords(tiles.length, tiles.length)[0] / 2;
		background = new BufferedImage(2 * bgOffset, 2 * bgOffset, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = background.createGraphics();
		g.translate(0, bgOffset);
		for (int i = 0; i < tiles.length; i++)
			for (int j = tiles[i].length - 1; j >= 0; j--)
				Tile.draw(tiles[i][j], g, i, j);

	}

	/**
	 * Tests if a given isometric coordinate falls within the boundaries of the level
	 * 
	 * @param x
	 *            isometric X coord
	 * @param y
	 *            isometric Y coord
	 * @return true if the given poin is within level boundaries
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

	public static Level buildDebug() {
		int debugSize = 6;
		Level l = new Level(debugSize);
		for (int j = 0; j < debugSize; j++)
			l.setTile(0, j, Tile.WALL);
		for (int i = 1; i < debugSize; i++) {
			for (int j = 0; j < debugSize; j++) {
				l.setTile(i, j, Tile.FLOOR);
			}
		}

		return l;
	}
}
