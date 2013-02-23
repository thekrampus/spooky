package sys;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AssetLib {
	public static BufferedImage SHEET_SKELLY, SHEET_GHOST, SHEET_PROJECTILE, SHEET_PORTAL, SHEET_FLASH, SHEET_BAT, TILE_DEFAULT_FLOOR, TILE_DEFAULT_WALL, TILE_STAIRS, TILE_NULL;

	public static Font FONT_LARGE, FONT_SMALL;

	/**
	 * Batch load all external assets needed in-game
	 * 
	 * @throws IOException
	 *             Bad filename
	 * @throws FontFormatException
	 *             Couldn't format fonts
	 */
	public static void load() throws IOException, FontFormatException {
		SHEET_SKELLY = loadImage("data/skeleton-optimized.png");
		SHEET_GHOST = loadImage("data/ghost.png");
		SHEET_PROJECTILE = loadImage("data/projectile.png");
		SHEET_PORTAL = loadImage("data/pentagram-anim.png");
		SHEET_FLASH = loadImage("data/spawnimation.png");
		SHEET_BAT = loadImage("data/bat1-optimized.png");
		TILE_DEFAULT_FLOOR = loadImage("data/tile-stone.png");
		TILE_DEFAULT_WALL = loadImage("data/stonewall-3.png");
		TILE_STAIRS = loadImage("data/stone-stairs.png");
		TILE_NULL = loadImage("data/tile-null.png");

		//Font f = Font.createFont(Font.TRUETYPE_FONT, new File("data/Fipps-Regular.otf"));
		Font f = Font.createFont(Font.TRUETYPE_FONT, new File("data/HeinzHeinrich-Regular.otf"));
		FONT_SMALL = f.deriveFont(8f);
		FONT_LARGE = f.deriveFont(24f);
	}

	public static BufferedImage loadImage(File f) throws IOException {
		return ImageIO.read(f);
	}

	public static BufferedImage loadImage(String path) throws IOException {
		return loadImage(new File(path));
	}
}
