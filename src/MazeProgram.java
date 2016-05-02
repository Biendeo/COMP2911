import java.awt.event.KeyEvent;
import java.io.IOException;

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
	
	public MazeProgram() {
		cfg = new Config();
		try {
			cfg.loadConfig();
		} catch (ClassNotFoundException e) {
			
		}
		// g will be null initially.
		// frame will be null initially.
		
		keyGet = new UIKeyGetter(this);
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
			System.out.println(arg);
			System.out.println(arg.length());
			if (arg.equals("-cfg")) {
				System.out.println(arg);
				try {
					cfg.loadConfig();
					cfg.editConfig();
					cfg.saveConfig();
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
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
	
	public static void main(String[] args) {
		MazeProgram p = new MazeProgram();
		p.parseInputs(args);
		p.run();
	}

}
