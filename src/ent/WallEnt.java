package ent;

import sys.Animation;
import sys.AssetLib;
import world.Level;

public class WallEnt extends Entity{
	public WallEnt(double x, double y) {
		super(x+.5, y+.5, new Animation(AssetLib.TILE_DEFAULT_WALL, 0, 1, AssetLib.TILE_DEFAULT_WALL.getWidth(), AssetLib.TILE_DEFAULT_WALL.getHeight()));
	}
	
	@Override
	public boolean update(Level l) { return true; }
}
