package world;

import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.util.ArrayList;

import ent.Entity;

public class Level {
	private Tile[][] tiles;
	private Area hitbox;
	private ArrayList<Entity> entities;
	
	public Level(int size) {
		tiles = new Tile[size][size];
		entities = new ArrayList<Entity>();
	}
	
	/**
	 * Draw all the stuff in the level to the screen
	 * @param g Graphics object to draw with
	 */
	public void draw(Graphics2D g) {
		for(int i = 0; i < tiles.length; i++) {
			for(int j = tiles[i].length-1; j >= 0; j--) {
				if(tiles[i][j] != null)
					tiles[i][j].draw(g, i, j);
				
				for(Entity e : entities) {
					if(e.checkPosition(i, j))
						e.draw(g);
				}
			}
		}
	}
	
	public void setTile(int x, int y, Tile tile) {
		tiles[x][y] = tile;
	}
	
	/**
	 * Build the hitbox for the current level by polling the hitboxen of all the tiles
	 */
	public void buildHitbox() {
		hitbox = new Area();
		for(int i = 0; i < tiles.length; i++)
			for(int j = 0; j < tiles[i].length; j++)
				hitbox.add(tiles[i][j].buildHitbox(i, j));
	}
	
	/**
	 * Tests if a given isometric coordinate falls within the boundaries of the level
	 * @param x isometric X coord
	 * @param y	isometric Y coord
	 * @return true if the given poin is within level boundaries
	 */
	public boolean isInBounds(double x, double y) {
		return hitbox.contains(x, y);
	}
	
	public void addEntity(Entity ent) {
		entities.add(ent);
	}
	
	/**
	 * One logical "tick" of the level
	 */
	public void update() {
		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).update(this);
		}
	}
	
	public static Level buildDebug() {
		Level l = new Level(4);
		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++)
				l.setTile(i, j, new Tile.Stone());
		
		return l;
	}
}
