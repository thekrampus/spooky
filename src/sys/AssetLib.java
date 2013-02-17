package sys;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AssetLib {
	public static BufferedImage SHEET_SKELLY, TILE_STONE, TILE_STONEWALL, TILE_NULL;

	/**
	 * Batch load all external assets needed in-game
	 * 
	 * @throws IOException
	 *             Bad filename
	 */
	public static void load() throws IOException {
		SHEET_SKELLY = loadImage("data/skeleton-optimized.png");
		TILE_STONE = loadImage("data/tile-stone.png");
		TILE_STONEWALL = loadImage("data/stonewall-3.png");
		TILE_NULL = loadImage("data/tile-null.png");
	}

	public static BufferedImage loadImage(String path) throws IOException {
		return ImageIO.read(new File(path));
	}
}
