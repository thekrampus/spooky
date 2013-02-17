package sys;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AnimViewer {
	private static final long framediff = 1000L / 100L;
	private static long timer;
	private static JFrame frame;
	private static Animation anim;

	public static void main(String[] args) {
		frame = new JFrame("Animation Viewer!");
		frame.setSize(700, 700);
		frame.setBackground(Color.cyan);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuItem open = new JMenuItem("Open");
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser(".");
				if (jfc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					open(jfc.getSelectedFile());
				}
			}
		});
		JMenu file = new JMenu("File");
		file.add(open);
		JMenuBar mb = new JMenuBar();
		mb.add(file);
		frame.setJMenuBar(mb);

		JPanel panel = new JPanel() {
			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g) {
				g.clearRect(0, 0, 700, 700);

				if (anim != null)
					g.drawImage(anim.getNextFrame(), 200, 200, null);
				else
					g.drawString("HEY LOAD AN ANIMATION YA DUMMY", 200, 200);

				try {
					long wait = timer - System.currentTimeMillis();
					if (wait > 0)
						Thread.sleep(wait);
					timer = System.currentTimeMillis() + framediff;
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("How on earth did this happen? Yell at the dev!");
				}
			}
		};
		frame.add(panel);

		frame.setVisible(true);

		while (true) {
			frame.repaint();
		}
	}

	public static void open(File f) {
		try {
			BufferedImage img = ImageIO.read(f);
			// String s[] = f.getName().split("-")[1].split("x"); // could probably do this with a regex
			String[] params = JOptionPane.showInputDialog("Enter the image loading params!!", "width,height,row,length,slowdown").split(",");
			anim = new Animation(img, Integer.parseInt(params[2]), Integer.parseInt(params[3]), Integer.parseInt(params[0]),
					Integer.parseInt(params[1]), Integer.parseInt(params[4]));
		} catch (IOException e) {
			System.out.println("FUCKIN THING SUCKS");
			e.printStackTrace();
			System.exit(0);
		}
	}
}
