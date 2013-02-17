package sys;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import world.Tile;

public class LevelBuilder extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private DisplayPanel panel;
	
	public static void main(String[] args) {
		try {
		AssetLib.load();
		} catch(IOException e) {
			System.out.println("Couldn't load game assets!");
			e.printStackTrace();
			System.exit(0);
		}
		
		new LevelBuilder();
	}
	
	public LevelBuilder() {
		super("Level Builder Utility");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new DisplayPanel();
		this.add(panel);
		
		JMenuItem newLevel = new JMenuItem("New Level");
		newLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = JOptionPane.showInputDialog("Enter an integer map size!");
				panel.newLevel(Integer.parseInt(str.trim()));
			}	
		});
		
		JMenuItem loadLevel = new JMenuItem("Load Level");
		loadLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser dialog = new JFileChooser(".");
				if(dialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					panel.loadLevel(dialog.getSelectedFile());
				}
			}
		});
		
		JMenuItem saveLevel = new JMenuItem("Save Level");
		saveLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser dialog = new JFileChooser(".");
				if(dialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					panel.saveLevel(dialog.getSelectedFile());
				}
			}
		});
		
		JMenu file = new JMenu("File");
		file.setMnemonic('F');
		file.add(newLevel);
		file.add(loadLevel);
		file.add(saveLevel);
		JMenuBar bar = new JMenuBar();
		bar.add(file);
		this.setJMenuBar(bar);
		
		this.setVisible(true);
		this.pack();
	}

}

class DisplayPanel extends JPanel implements MouseMotionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	
	private Tile[][] tiles;
	private Point camera;
	private int pX, pY; //used for mouse dragging
	private TileMenu menu;
	
	public DisplayPanel() {
		super(false);
		newLevel(4);
		this.setOpaque(true);
		this.setFocusable(true);
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(700, 700));
		this.setVisible(true);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		menu = new TileMenu(710, 0);
	}
	
	public void newLevel(int size) {
		tiles = new Tile[size][size];
		for(Tile[] row : tiles)
			Arrays.fill(row, Tile.FLOOR);
		camera = new Point(0, Tile.getScreenCoords(size, size)[0] / 2);
		
		repaint();
	}
	
	public void loadLevel(File f) {
		try {
			ObjectInput in = new ObjectInputStream( new BufferedInputStream(new FileInputStream(f)));
			tiles = (Tile[][]) in.readObject();
			
			in.close();
		} catch (Exception e) {
			System.out.println("Couldn't open file!");
			e.printStackTrace();
			return;
		}
		
		System.out.println("Read from file successfully!");
		repaint();
	}
	
	public void saveLevel(File f) {
		try {
			ObjectOutput out = new ObjectOutputStream( new BufferedOutputStream(new FileOutputStream(f)));
			out.writeObject(tiles);
			
			out.close();
		} catch (Exception e) {
			System.out.println("Couldn't open file!");
			e.printStackTrace();
			return;
		}
		
		System.out.println("Wrote to file successfully!");
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int bgOffset = Tile.getScreenCoords(tiles.length, tiles.length)[0] / 2;
		BufferedImage buffer = new BufferedImage(2 * bgOffset, 2 * bgOffset, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = buffer.createGraphics();
		g2d.translate(0, bgOffset);
		for (int i = 0; i < tiles.length; i++)
			for (int j = tiles[i].length - 1; j >= 0; j--)
				Tile.draw(tiles[i][j], g2d, i, j);

		g.translate(camera.x, camera.y);
		g.drawImage(buffer, 0, -bgOffset-Tile.TILE_HEIGHT/2, null);
		
		//draw grid
		g.setColor(Color.red);
		for(int i = 0; i <= tiles.length; i++) {
			int[] p1 = Tile.getScreenCoords(i, 0);
			int[] p2 = Tile.getScreenCoords(i, tiles.length);
			g.drawLine(p1[0], p1[1], p2[0], p2[1]);
			p1 = Tile.getScreenCoords(0, i);
			p2 = Tile.getScreenCoords(tiles.length, i);
			g.drawLine(p1[0], p1[1], p2[0], p2[1]);
		}
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(e.getButton() == 1) {
			if(pX != 0 && pY != 0) {
				camera.x += e.getX() - pX;
				camera.y += e.getY() - pY;
				repaint();
			}
			pX = e.getX();
			pY = e.getY();
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		pX = pY = 0;

		if(e.getButton() == 3) {
			double[] iso = Tile.getIsoCoords(1.5*(e.getX()-camera.x), e.getY()-camera.y);
			System.out.println(Math.round(iso[0]) + ", " + Math.round(iso[1]));
			
			int iX = (int)Math.round(iso[0]);
			int iY = (int)-Math.round(iso[1]);
			if(iX >= 0 && iX < tiles.length && iY >= 0 && iY < tiles.length) {
				tiles[iX][iY] = menu.getSelection();
				repaint();
			}
		}
	}

	//what the fuck ever who gives a shit
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseMoved(MouseEvent arg0) {}
	
}

class TileMenu extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private Tile selection = Tile.EMPTY;
	
	public TileMenu(int x, int y) {
		super("Things!");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLayout(new GridLayout(Tile.values().length, 1));
		
		for(final Tile tile : Tile.values()) {
			JButton button = new JButton(new ImageIcon(Tile.getSprite(tile)));
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) { selection = tile; }
			});
			this.add(button);
		}
		
		this.setLocation(x, y);
		this.setVisible(true);
		this.pack();
	}
	
	public Tile getSelection() {
		return selection;
	}
	
	
}