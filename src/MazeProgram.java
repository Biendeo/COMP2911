import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

/**
 * The main execution of the program. It creates a maze and calls basic UI
 * functions. It should eventually handle input and possible other features.
 * @author Group 2
 *
 */
public class MazeProgram {
	private Config cfg;
	private MazeGame g;
	private UIFrame frame;
	private UIKeyGetter keyGet;
	
	private Timer refresh;
	
	private static int refreshRate = 60;
	private static int refreshTime = (int)((float)1000 / refreshRate);
	
	public MazeProgram() {
		cfg = new Config();
		cfg.loadConfig();
		// g will be null initially.
		// frame will be null initially.
		
		
		keyGet = new UIKeyGetter(this);

		refresh = new Timer(refreshTime, keyGet);
		refresh.start();
	}
	
	public void run() {
		g = new MazeGame();
		g.addPlayer();

		UI.printMazeASCII(g.getMaze());
		
		frame = new UIFrame(g);
		
		frame.addKeyListener(keyGet);
		
		frame.drawMazeSwingOne();
		
		frame.setVisible(true);
	}
	
	public void parseInputs(String[] args) {
		for (String arg : args) {
			if (arg.equals("-cfg")) {
				cfg.loadConfig();
				cfg.editConfig();
				cfg.saveConfig();
			}
		}
	}
	
	public void keyPress(KeyEvent ke) {
		Player[] players = g.getPlayers();
		char keyChar = ke.getKeyChar();
		try {
			if (keyChar == cfg.getPlayer1Up()) {
				players[0].move(Direction.UP);
			} else if (keyChar == cfg.getPlayer1Right()) {
				players[0].move(Direction.RIGHT);
			} else if (keyChar == cfg.getPlayer1Down()) {
				players[0].move(Direction.DOWN);
			} else if (keyChar == cfg.getPlayer1Left()) {
				players[0].move(Direction.LEFT);
			} else if (keyChar == cfg.getPlayer2Up()) {
				players[1].move(Direction.UP);
			} else if (keyChar == cfg.getPlayer2Right()) {
				players[1].move(Direction.RIGHT);
			} else if (keyChar == cfg.getPlayer2Down()) {
				players[1].move(Direction.DOWN);
			} else if (keyChar == cfg.getPlayer2Left()) {
				players[1].move(Direction.LEFT);
			}
		} catch (Exception e) {
			// Do nothing.
		}
		frame.pack();
		frame.repaint();
	}
	
	/**
	 * Gets called once a frame update.
	 * @param e
	 * The ActionEvent involved with the update.
	 */
	public void updateFrame(ActionEvent e) {
		for (Player pl : g.getPlayers()) {
			pl.addMilliseconds(refreshTime);
		}
		
		// This just exists to test the time system.
		int milliseconds = g.getPlayers()[0].getMillisecondsTaken();
		System.out.format("Current time: %02d:%02d:%03d\n", milliseconds / 60000, milliseconds / 1000 % 60, milliseconds % 1000);
	}
	
	public static void main(String[] args) {
		MazeProgram p = new MazeProgram();
		p.parseInputs(args);
		p.run();
	}

}
