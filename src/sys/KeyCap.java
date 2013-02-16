package sys;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyCap implements KeyListener {

	public boolean key_right, key_left, key_up, key_down, key_w, key_a, key_s, key_d, key_q, key_space, key_esc;

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

}
