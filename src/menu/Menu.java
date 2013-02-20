package menu;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


public class Menu {
	private ArrayList<MenuItem> items;
	private int index;
	
	public Menu() {
		items = new ArrayList<MenuItem>();
		index = 0;
	}
	
	public void draw(Graphics2D g, float x, float y) {
		//Rectangle2D charSize = g.getFont().getStringBounds("{", g.getFontRenderContext());
		for(int i = 0; i < items.size(); i++) {
			String item;
			if(i == index)
				item = "{" + items.get(i).toString() + "}";
			else
				item = items.get(i).toString();
			
			Rectangle2D size = g.getFont().getStringBounds(item, g.getFontRenderContext());
			g.drawString(item, x - (int)(size.getWidth()/2), y+i*(int)size.getHeight());
		}
	}
	
	public void next() {
		if(index < items.size()-1)
			index++;
	}
	
	public void prev() {
		if(index > 0)
			index--;
	}
	
	public void activate() {
		items.get(index).activate();
	}
	
	public void addItem(MenuItem m) {
		items.add(m);
	}
	
	public void remove(MenuItem m) {
		items.remove(m);
	}
}

abstract class MenuItem {
	String string;
	
	public MenuItem(String str) {
		string = str;
	}
	
	public abstract void activate();
	
	@Override
	public String toString() {
		return string;
	}
}