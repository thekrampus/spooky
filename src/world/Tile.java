package world;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import sys.AssetLib;

public enum Tile implements Serializable{
	EMPTY, FLOOR, WALL;
	
	public static final int TILE_WIDTH = 160, TILE_HEIGHT = 80; // default size of tiles
	
	public static Area getHitbox(Tile t, int x, int y) {
		switch(t) {
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
		int[] c = Tile.getScreenCoords(x, y);
		int width = TILE_WIDTH;
		int height = TILE_HEIGHT;
		BufferedImage sprite = null;
		switch(t) {
		case EMPTY:
			return; //I'm invisible!!
		case FLOOR:
			sprite = AssetLib.TILE_STONE.getSubimage(0, TILE_HEIGHT*(int)(Math.random()*3), TILE_WIDTH, TILE_HEIGHT);
			break;
		case WALL:
			sprite = AssetLib.TILE_STONEWALL;
			height = 240;
			break;
		default:
			System.out.println("Not sure what to draw with this kind of tile!");
			return;
		}
		
		g.drawImage(sprite, c[0], c[1]-(height-TILE_HEIGHT), width, height, null);
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
}