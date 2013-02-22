package menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import sys.Animation;
import sys.AssetLib;
import sys.Game;
import sys.InputMethod;
import ent.Player;
import ent.Player.DebugPlayer;
import ent.Player.Rogue;
import ent.Player.Warrior;

public class CharacterMenu extends MenuFrame {
	public static final int WIDTH = 600, HEIGHT = 600, MAX_PLAYERS = 4;
	private int playerCount = 0;
	
	private ArrayList<InputMethod> allInputs;
	private CharSelectFrame[] charFrames;
	private ArrayList<Player> playerList;

	public CharacterMenu(ArrayList<InputMethod> allInputs) {
		super(Game.centerX - WIDTH/2, Game.centerY - HEIGHT / 2, WIDTH, HEIGHT);
		
		this.allInputs = new ArrayList<InputMethod>();
		this.allInputs.addAll(allInputs);
		
		charFrames = new CharSelectFrame[MAX_PLAYERS];
		charFrames[0] = new CharSelectFrame(frame.getX(), frame.getY(), this);
		charFrames[1] = new CharSelectFrame(frame.getCenterX(), frame.getY(), this);
		charFrames[2] = new CharSelectFrame(frame.getX(), frame.getCenterY(), this);
		charFrames[3] = new CharSelectFrame(frame.getCenterX(), frame.getCenterY(), this);
	}

	@Override
	public void control() {
		for(int i = 0; i < allInputs.size(); i++) {
			if(allInputs.get(i).menu())
				alive = false;
			
			if(allInputs.get(i).activate() && playerCount < MAX_PLAYERS) {
				charFrames[playerCount].setInput(allInputs.get(i));
				playerCount++;
				allInputs.remove(i);
				i--;
			}
		}
		
		for(CharSelectFrame csf : charFrames)
			csf.control();
	}
	
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		g.setColor(Color.black);
		g.drawLine((int)frame.getX()+6, (int)frame.getCenterY(), (int)frame.getMaxX()-6, (int)frame.getCenterY());
		g.drawLine((int)frame.getCenterX(), (int)frame.getY()+6, (int)frame.getCenterX(), (int)frame.getMaxY()-6);
		for(CharSelectFrame csf : charFrames)
			csf.draw(g);
	}
	
	public void addInput(InputMethod input) {
		this.allInputs.add(input);
		playerCount--;
	}
	
	public ArrayList<Player> getPlayerList() {
		return playerList;
	}
	
	public void closeAndBuildPlayers() {
		playerList = new ArrayList<Player>();
		for(int i = 0; i < playerCount; i++) {
			playerList.add(charFrames[i].buildPlayer());
		}
		
		Game.stopAllInstances();
	}
	
}

class CharSelectFrame extends MenuFrame {
	public static final int WIDTH = CharacterMenu.WIDTH/2, HEIGHT = CharacterMenu.HEIGHT/2;
	public static enum Character {
		SKELLY(new Animation(AssetLib.SHEET_SKELLY, 0, 2, 129, 205, 10)),
		GHOST(new Animation(AssetLib.SHEET_GHOST, 0, 2, 139, 107, 20));
		
		Animation anim;
		
		Character(Animation a) {
			anim = a;
		}
		
		public BufferedImage getFrame() {
			return anim.getNextFrame();
		}
	};
	
	private InputMethod input;
	private int index = 0;
	private CharacterMenu parent;

	public CharSelectFrame(double x, double y, CharacterMenu parent) {
		super((int)x, (int)y, WIDTH, HEIGHT);
		this.parent = parent;
	}
	
	public void setInput(InputMethod in) {
		if(input == null)
			input = in;
	}

	@Override
	public void control() {
		if(input != null) {
			boolean special = input.special();
			boolean attack = input.activate();
			double xAxis = input.stickL()[0];
			if(!hold) {
				if(xAxis > 0) {
					index = (index+1)%Character.values().length;
					hold = true;
				}
				else if(xAxis < 0) {
					index--;
					if(index < 0)
						index += Character.values().length;
					hold = true;
				}
				
				if(special) {
					parent.addInput(input);
					input = null;
				}
				
				if(attack) {
					parent.closeAndBuildPlayers();
				}
			}
			
			if(!special && !attack && xAxis == 0)
				hold = false;
		}
	}
	
	public Player buildPlayer() {
		Player p;
		Character selection = Character.values()[index];
		double spawnX = Math.random() + .5;
		double spawnY = Math.random() + .5;
		switch(selection) {
		case SKELLY:
			p = new Warrior(spawnX, spawnY, input);
			break;
		case GHOST:
			p = new Rogue(spawnX, spawnY, input);
			break;
		default:
			p = new DebugPlayer(1, 1, input);
			break;
		}
		
		return p;
	}
	
	@Override
	public void draw(Graphics2D g) {
//		super.draw(g);
		g.setColor(Color.black);
		g.setFont(AssetLib.FONT_SMALL);
		if(input == null) {
			String message = "Press [activate] to join";
			Rectangle2D size = g.getFont().getStringBounds(message, g.getFontRenderContext());
			g.drawString(message, (int)(frame.getCenterX() - (size.getWidth()/2)), (int)(frame.getCenterY() - (size.getHeight()/2)));
		} else {
			BufferedImage sprite = Character.values()[index].getFrame();
			g.drawImage(sprite, (int) (frame.getCenterX() - sprite.getWidth()/2), (int) (frame.getCenterY() - sprite.getWidth()/2), null);
		}
	}
}