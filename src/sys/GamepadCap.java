package sys;

import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class GamepadCap implements InputMethod {
	public static final double STICK_DEADZONE = 0.05;
	private Controller gamepad;
	private double[] stickL = {0., 0.}, stickR = {0., 0.}, nullStick = {0., 0.};
	private boolean button_A, button_X, button_start;
	
	public GamepadCap(Controller gamepad) {
		this.gamepad = gamepad;
		this.startListening(gamepad.getEventQueue());
	}

	@Override
	public double[] stickL() {
		if(stickL[0]*stickL[0] + stickL[1]*stickL[1] > STICK_DEADZONE)
			return stickL;
		else
			return nullStick;
	}

	@Override
	public double[] stickR() {
		if(stickR[0]*stickR[0] + stickR[1]*stickR[1] > STICK_DEADZONE)
			return stickR;
		else
			return nullStick;
	}

	@Override
	public boolean attack() {
		return button_A;
	}

	@Override
	public boolean special() {
		return button_X;
	}

	@Override
	public boolean menu() {
		return button_start;
	}
	
	private void startListening(final EventQueue queue) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean running = true;
				Event event = new Event();
				while (running) {
					running = gamepad.poll();
					while(queue.getNextEvent(event)) {
						//System.out.println(gamepad.getName() + " - " + event.getComponent().getName() + " - " + event.getValue());
						
						if(event.getComponent().getIdentifier().equals(Identifier.Button._0)) {
							button_A = (event.getValue()!=0);
						} else if(event.getComponent().getIdentifier().equals(Identifier.Button._2)) {
							button_X = (event.getValue()!=0);
						} else if(event.getComponent().getIdentifier().equals(Identifier.Button._8)) {
							button_start = (event.getValue()!=0);
						} else if(event.getComponent().getIdentifier().equals(Identifier.Axis.X)) {
							stickL[0] = event.getValue();
						} else if(event.getComponent().getIdentifier().equals(Identifier.Axis.Y)) {
							stickL[1] = event.getValue();
						}
						
					}
					try {
						Thread.sleep(20);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

}
