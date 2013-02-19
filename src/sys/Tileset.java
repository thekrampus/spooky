package sys;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tileset {
	public BufferedImage floor, wall, blank;
	public Tileset(String name) throws IOException
	{
		File floorFile = new File(name + "/floor.png");
		File wallFile = new File(name + "/wall.png");
		File blankFile = new File(name + "/null.png");
		
		if (floorFile.exists())
		{
			floor = AssetLib.loadImage(floorFile);
		}
		else
		{
			floor = AssetLib.TILE_DEFAULT_FLOOR;
		}
		
		if (wallFile.exists())
		{
			wall = AssetLib.loadImage(wallFile);
		}
		else
		{
			wall = AssetLib.TILE_DEFAULT_WALL;
		}
		
		if (blankFile.exists())
		{
			blank = AssetLib.loadImage(blankFile);
		}
		else
		{
			blank = AssetLib.TILE_NULL;
		}
	}
}