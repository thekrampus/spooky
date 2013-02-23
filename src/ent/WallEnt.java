package ent;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import sys.AssetLib;
import world.Level;
import world.Tile;

public class WallEnt extends Entity{
	public WallEnt(double x, double y) {
		super(x+.5, y+.5, null);
	}
	
	@Override
	public void update(Level l) {}
	
	@Override
	public void draw(Graphics2D g) {
		int[] c = Tile.getScreenCoords(xCoord, yCoord);

		BufferedImage frame = AssetLib.TILE_DEFAULT_WALL;
		g.drawImage(frame, c[0] - frame.getWidth() / 2, c[1] - frame.getHeight() + Tile.TILE_HEIGHT - 10, null);
	}
}
