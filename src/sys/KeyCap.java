package sys;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyCap implements KeyListener, InputMethod{
	private boolean key_right, key_left, key_up, key_down, key_w, key_a, key_s, key_d, key_q, key_space, key_esc;

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			key_up = true;
			break;
		case KeyEvent.VK_DOWN:
			key_down = true;
			break;
		case KeyEvent.VK_LEFT:
			key_left = true;
			break;
		case KeyEvent.VK_RIGHT:
			key_right = true;
			break;
		case KeyEvent.VK_W:
			key_w = true;
			break;
		case KeyEvent.VK_A:
			key_a = true;
			break;
		case KeyEvent.VK_S:
			key_s = true;
			break;
		case KeyEvent.VK_D:
			key_d = true;
			break;
		case KeyEvent.VK_SPACE:
			key_space = true;
			break;
		case KeyEvent.VK_Q:
			key_q = true;
			break;
		case KeyEvent.VK_ESCAPE:
			key_esc = true;
			break;
		}
	}

	public void keyReleased(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			key_up = false;
			break;
		case KeyEvent.VK_DOWN:
			key_down = false;
			break;
		case KeyEvent.VK_LEFT:
			key_left = false;
			break;
		case KeyEvent.VK_RIGHT:
			key_right = false;
			break;
		case KeyEvent.VK_W:
			key_w = false;
			break;
		case KeyEvent.VK_A:
			key_a = false;
			break;
		case KeyEvent.VK_S:
			key_s = false;
			break;
		case KeyEvent.VK_D:
			key_d = false;
			break;
		case KeyEvent.VK_SPACE:
			key_space = false;
			break;
		case KeyEvent.VK_Q:
			key_q = false;
			break;
		case KeyEvent.VK_ESCAPE:
			key_esc = false;
			break;
		}

	}

	@Override
	public double[] stickL() {
		double[] axes = {0., 0.};
		if(key_w) {
			if(key_a) {
				axes[0] = -.707;
				axes[1] = -.707;
			} else if(key_d) {
				axes[0] = -.707;
				axes[1] = .707;
			} else {
				axes[0] = -1;
			}
		} else if(key_s) {
			if(key_a) {
				axes[0] = .707;
				axes[1] = -.707;
			} else if(key_d) {
				axes[0] = .707;
				axes[1] = .707;
			} else {
				axes[0] = 1;
			}
		} else {
			axes[1] = (key_a? -1 : 0) + (key_d? 1 : 0);
		}
		
		return axes;
	}

	@Override
	public double[] stickR() {
		double[] axes = {0., 0.};
		if(key_up) {
			if(key_left) {
				axes[0] = -.707;
				axes[1] = -.707;
			} else if(key_right) {
				axes[0] = -.707;
				axes[1] = .707;
			} else {
				axes[0] = -1;
			}
		} else if(key_down) {
			if(key_left) {
				axes[0] = .707;
				axes[1] = -.707;
			} else if(key_right) {
				axes[0] = .707;
				axes[1] = .707;
			} else {
				axes[0] = 1;
			}
		} else {
			axes[1] = (key_left? -1 : 0) + (key_right? 1 : 0);
		}
		
		return axes;
	}

	@Override
	public boolean attack() {
		return key_space;
	}

	@Override
	public boolean special() {
		return key_q;
	}

	@Override
	public boolean menu() {
		return key_esc;
	}

}
