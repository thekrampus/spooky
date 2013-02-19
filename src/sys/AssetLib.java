package sys;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AssetLib {
	public static BufferedImage SHEET_SKELLY, TILE_DEFAULT_FLOOR, TILE_DEFAULT_WALL, TILE_NULL;

	/**
	 * Batch load all external assets needed in-game
	 * 
	 * @throws IOException
	 *             Bad filename
	 */
	public static void load() throws IOException {
		SHEET_SKELLY = loadImage("data/skeleton-optimized.png");
		TILE_DEFAULT_FLOOR = loadImage("data/tile-stone.png");
		TILE_DEFAULT_WALL = loadImage("data/stonewall-3.png");
		TILE_NULL = loadImage("data/tile-null.png");
	}

	public static BufferedImage loadImage(File f) throws IOException
	{
		return ImageIO.read(f);
	}
	
	public static BufferedImage loadImage(String path) throws IOException {
		return loadImage(new File(path));
	}
}
