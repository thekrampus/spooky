package sys;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AssetLib {
	public static BufferedImage SHEET_SKELLY, TILE_STONE1, TILE_STONEWALL;

	/**
	 * Batch load all external assets needed in-game
	 * 
	 * @throws IOException
	 *             Bad filename
	 */
	public static void load() throws IOException {
		SHEET_SKELLY = loadImage("data/skeleton-optimized.png");
		TILE_STONE1 = loadImage("data/tile-stone-1.png");
		TILE_STONEWALL = loadImage("data/stonewall.png");
	}

	public static BufferedImage loadImage(String path) throws IOException {
		return ImageIO.read(new File(path));
	}
}
