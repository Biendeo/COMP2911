import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles key presses and input.
 *
 */
public class UIKeyGetter implements KeyListener {
	
	private MazeProgram p;
	
	/**
	 * Creates a new key getter.
	 * @param p
	 * The main maze program.
	 */
	public UIKeyGetter(MazeProgram p) {
		this.p = p;
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
		p.keyPress(e);
	}

}
