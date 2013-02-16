package world;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

import sys.AssetLib;

public abstract class Tile {
	public static final int TILE_WIDTH = 160, TILE_HEIGHT = 80; // default size of tiles
	private BufferedImage sprite;

	public Tile(BufferedImage sprite) {
		this.sprite = sprite;
	}

	/**
	 * Build an Area of the intersectable portion of this tile. Each inheriting class should have its own unique rules for building a hitbox
	 */
	public abstract Area buildHitbox(int x, int y);

	/**
	 * Draw this tile to screen
	 * 
	 * @param g
	 *            Graphics object to draw with
	 * @param x
	 *            Isomorphic X coord
	 * @param y
	 *            Isomorphic Y coord
	 */
	public void draw(Graphics2D g, int x, int y) {
		int[] c = getScreenCoords(x, y);
		g.drawImage(sprite, c[0], c[1], null);
	}

	/**
	 * Translate isomorphic coordinates to screen coordinates
	 * 
	 * @param isoX
	 *            Isomorphic X coord
	 * @param isoY
	 *            Isomorphic Y coord
	 * @return Array of screen X coord, screen Y coord
	 */
	public static int[] getScreenCoords(double isoX, double isoY) {
		int[] c = new int[2];
		c[0] = (int) ((isoY * TILE_WIDTH / 2) + (isoX * TILE_WIDTH / 2));
		c[1] = (int) ((isoX * TILE_HEIGHT / 2) - (isoY * TILE_HEIGHT / 2));

		return c;
	}
	
	public static class Stone extends Tile {
		public Stone() {
			super(AssetLib.TILE_STONE1);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Area buildHitbox(int x, int y) {

			return new Area(new Rectangle(x, y, 1, 1));
		}
	}
}
