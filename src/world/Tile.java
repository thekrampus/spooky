package world;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

import sys.AssetLib;
import ent.Entity;
import ent.WallEnt;
import ent.enemy.Bat;

public enum Tile {
	EMPTY, FLOOR, WALL, PORTAL_BAT;

	public static final int TILE_WIDTH = 160, TILE_HEIGHT = 80; // default size of tiles

	public static Area getHitbox(Tile t, int x, int y) {
		if (t == null)
			return new Area();

		switch (t) {
		case EMPTY:
		case WALL:
			return new Area();
		case FLOOR:
		case PORTAL_BAT:
			return new Area(new Rectangle(x, y, 1, 1));
		default:
			System.out.println("Not sure what to do with this kind of tile!");
			return null;
		}
	}

	public static void draw(Tile t, Graphics2D g, int x, int y) {
		if (t == null)
			return;

		int[] c = Tile.getScreenCoords(x, y);
		int width = TILE_WIDTH;
		int height = TILE_HEIGHT;
		BufferedImage sprite = null;
		switch (t) {
		case EMPTY:
			return; // I'm invisible!!
		case FLOOR:
			sprite = AssetLib.TILE_DEFAULT_FLOOR.getSubimage(0, TILE_HEIGHT * (int) (Math.random() * 3), TILE_WIDTH, TILE_HEIGHT);
			break;
		case WALL:
			sprite = AssetLib.TILE_DEFAULT_WALL;
			height = 240;
			break;
		case PORTAL_BAT:
			sprite = AssetLib.SHEET_PORTAL.getSubimage(0, 59, TILE_WIDTH, TILE_HEIGHT);
			break;
		default:
			System.out.println("Not sure what to draw with this kind of tile!");
			return;
		}

		g.drawImage(sprite, c[0], c[1] - (height - TILE_HEIGHT), width, height, null);
	}

	public static BufferedImage getSprite(Tile t) {
		if (t == null)
			return AssetLib.TILE_NULL;
		switch (t) {
		case EMPTY:
			return AssetLib.TILE_NULL;
		case FLOOR:
			return AssetLib.TILE_DEFAULT_FLOOR.getSubimage(0, 0, TILE_WIDTH, TILE_HEIGHT);
		case WALL:
			return AssetLib.TILE_DEFAULT_WALL;
		case PORTAL_BAT:
			return AssetLib.SHEET_BAT.getSubimage(0, 0, 58, 110);
		default:
			System.out.println("Not sure what sprite this is!");
			return AssetLib.TILE_NULL;
		}

	}
	
	public static Entity pullEnt(Tile t, int x, int y) {
		if(t == null)
			return null;
		switch(t) {
		case WALL:
			return new WallEnt(x, y);
		case PORTAL_BAT:
			return new Bat.BatPortal(x, y);
		default:
			return null;
		}
	}

	/**
	 * Translate isometric coordinates to screen coordinates
	 * 
	 * @param isoX
	 *            Isometric X coord
	 * @param isoY
	 *            Isometric Y coord
	 * @return Array of screen X coord, screen Y coord
	 */
	public static int[] getScreenCoords(double isoX, double isoY) {
		int[] c = new int[2];
		c[0] = (int) ((isoY * TILE_WIDTH / 2) + (isoX * TILE_WIDTH / 2));
		c[1] = (int) ((isoX * TILE_HEIGHT / 2) - (isoY * TILE_HEIGHT / 2));

		return c;
	}

	public static double[] getIsoCoords(double screenX, double screenY) {
		double[] c = new double[2];
		c[0] = ((screenY * 2.0 / (double) TILE_HEIGHT) + (screenX / (double) TILE_WIDTH))/2.0;
		c[1] = (screenY * 2.0 / (double) TILE_HEIGHT) - c[0];
//		c[0] = (double) screenY / (double) TILE_HEIGHT + (double) screenX / (2.0 * (double) TILE_WIDTH);
//		c[1] = (double) screenY / (double) TILE_HEIGHT - (double) screenX / (2.0 * (double) TILE_WIDTH);
		return c;
	}
}