package world;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import sys.Launcher;

public enum Tile implements Serializable {
	EMPTY, FLOOR, WALL;

	public static final int TILE_WIDTH = 160, TILE_HEIGHT = 80; // default size of tiles

	public static Area getHitbox(Tile t, int x, int y) {
		if (t == null)
			return new Area();

		switch (t) {
		case EMPTY:
		case WALL:
			return new Area();
		case FLOOR:
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
			sprite = Launcher.game.getLevel().getTileset().floor.getSubimage(0, TILE_HEIGHT * (int) (Math.random()*3), TILE_WIDTH, TILE_HEIGHT);
			break;
		case WALL:
			sprite = Launcher.game.getLevel().getTileset().wall;
			height = 240;
			break;
		default:
			System.out.println("Not sure what to draw with this kind of tile!");
			return;
		}

		g.drawImage(sprite, c[0], c[1] - (height - TILE_HEIGHT), width, height, null);
	}

	public static BufferedImage getSprite(Tile t) {
		if (t == null)
			return Launcher.game.getLevel().getTileset().blank;
		switch (t) {
		case EMPTY:
			return Launcher.game.getLevel().getTileset().blank;
		case FLOOR:
			return Launcher.game.getLevel().getTileset().floor.getSubimage(0, 0, TILE_WIDTH, TILE_HEIGHT);
		case WALL:
			return Launcher.game.getLevel().getTileset().wall;
		default:
			System.out.println("Not sure what sprite this is!");
			return Launcher.game.getLevel().getTileset().blank;
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