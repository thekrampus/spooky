package ent;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import sys.Animation;
import sys.AssetLib;
import sys.Game;
import world.Level;
import world.Tile;


public class Stairs extends Entity {

	public Stairs(double x, double y) {
		super(x+.5, y+.5, new Animation(AssetLib.TILE_STAIRS, 0, 1, AssetLib.TILE_STAIRS.getWidth(), AssetLib.TILE_STAIRS.getHeight()));
		radius = .7;
	}
	
	@Override
	public boolean update(Level l) {
		ArrayList<Player> players = l.getPlayers();
		
		int ready = 0;
		int dead = 0;
		for(Player p : players) {
			if(this.getDistance(p) < radius)
				ready++;
			
			else if(p.getHealth() <= 0)
				dead++;
		}
		
		if(ready+dead == players.size() && ready > 0)
			Game.nextLevel();
		
		return true;
	}
	
	@Override
	public void draw(Graphics2D g) {
		int[] c = Tile.getScreenCoords(xCoord, yCoord);

		BufferedImage frame = AssetLib.TILE_STAIRS;
		g.drawImage(frame, c[0] - frame.getWidth() / 2, c[1] - frame.getHeight() + Tile.TILE_HEIGHT - 10, null);
	}

}
