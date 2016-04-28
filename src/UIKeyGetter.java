import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles key presses and input.
 *
 */
public class UIKeyGetter implements KeyListener {
	
	// TODO: Formalise how this works.
	UIFrame frame;
	
	/**
	 * Creates a new key getter.
	 * @param frame
	 * The frame that holds this (so input is focused).
	 */
	public UIKeyGetter(UIFrame frame) {
		this.frame = frame;
	}

	@Override
	/**
	 * Handle any key pressed down.
	 */
	public void keyPressed(KeyEvent e) {
		// Add key down stuff here.
	}

	@Override
	/**
	 * Handle any key let up.
	 */
	public void keyReleased(KeyEvent e) {
		// Add key up stuff here.
	}

	@Override
	/**
	 * Handle when a key has been "typed".
	 */
	public void keyTyped(KeyEvent e) {
		Player[] players = frame.getPlayers();
		switch (e.getKeyChar()) {
		case 'a':
			players[0].move(Direction.LEFT);
			break;
		case 's':
			players[0].move(Direction.DOWN);
			break;
		case 'd':
			players[0].move(Direction.RIGHT);
			break;
		case 'w':
			players[0].move(Direction.UP);
			break;
		}
		// TODO: Should the key getter call the redraw?
		frame.pack();
		frame.repaint();
	}

}
