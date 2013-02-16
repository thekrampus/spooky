/**
 * An animation - a collection of sprite frames that can be either accessed individually or in sequence.
 * Forms an animation from a vertically-oriented spritesheet.
 */

package sys;

import java.awt.image.BufferedImage;

public class Animation {
	private BufferedImage sheet;
	protected int width, height, frameCount, key, slowdown, subkey, row;

	public Animation(BufferedImage spritesheet, int numRows, int numFrames) {
		sheet = spritesheet;
		key = subkey = 0;
		frameCount = numFrames;
		width = sheet.getWidth() / frameCount;
		height = sheet.getHeight() / numRows;
		slowdown = 0;
		row = 0;
	}

	public Animation(BufferedImage spritesheet, int numRows, int numFrames, int slow) {
		sheet = spritesheet;
		key = subkey = 0;
		frameCount = numFrames;
		width = sheet.getWidth() / frameCount;
		height = sheet.getHeight() / numRows;
		slowdown = slow;
		row = 0;
	}

	public BufferedImage getFrame(int frame) {
		key = frame;
		return sheet.getSubimage(width * frame, height * row, width, height);
	}

	public BufferedImage getNextFrame() {
		BufferedImage frame = sheet.getSubimage(width * key, height * row, width, height);
		if (subkey < slowdown)
			subkey++;
		else {
			key++;
			key %= frameCount;
			subkey = 0;
		}
		return frame;
	}
	
	public BufferedImage getCurrentFrame() {
		return this.getFrame(key);
	}

	public int getLength() {
		return frameCount;
	}
	
	public void setRow(int r) {
		row = r;
	}
	
	public void reset() {
		key = subkey = 0;
	}

}
