import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;
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
	
	// If anyone complains about this line, just replace it with 60.
	private static int refreshRate = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode().getRefreshRate();
	private static int refreshTime = (int)((float)1000 / refreshRate);
	
	/**
	 * Creates a new MazeProgram.
	 */
	public MazeProgram() {
		cfg = new Config();
		cfg.loadConfig();
		// g will be null initially.
		// frame will be null initially.
		
		
		keyGet = new UIKeyGetter(this);

		refresh = new Timer(refreshTime, keyGet);
		refresh.start();
	}
	
	/**
	 * The program's main execution.
	 */
	public void run() {
		frame = new UIFrame(this);
		
		frame.addKeyListener(keyGet);
		
		frame.pack();
		
		frame.setVisible(true);
		frame.setFocusable(true);
	}
	
	public void instantAction() {
		g = new MazeGame();
		g.addPlayer();
		
		frame.setGame(g);
		
		UI.printMazeASCII(g.getMaze());
		
		g.setPlaying(true);
		
		frame.drawMazeSwingOne();
		
		frame.setMoveText("0 moves");
		
		frame.switchPanel("mazeViewPanel");
		
	}
	
	public void exitMazeGame() {
		frame.setGame(null);
		frame.switchPanel("mainMenuPanel");
	}
	
	/**
	 * Reads the program arguments and runs additional settings.
	 * @param args
	 * The program arguments.
	 */
	public void parseInputs(String[] args) {
		for (String arg : args) {
			if (arg.equals("-cfg")) {
				cfg.loadConfig();
				cfg.editConfig();
				cfg.saveConfig();
			}
		}
	}
	
	/**
	 * Responds to a KeyEvent.
	 * @param ke
	 * The key event.
	 */
	public void keyPress(KeyEvent ke) {
		Player[] players = g.getPlayers();
		char keyChar = ke.getKeyChar();
		try {
			if (g.isPlaying()) {
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
			}
		} catch (Exception e) {
			// Do nothing.
		}

		if (g.isEveryoneAtGoal()) {
			g.setPlaying(false);
		}
		
		// TODO: This needs to work much better with more than one player.
		if (g.getPlayersAtGoal() > 0) {
			JOptionPane.showMessageDialog(null, "You won! You're awesome!", "Winner winner chicken dinner", JOptionPane.PLAIN_MESSAGE);
		}

		frame.setMoveText(Integer.toString(g.getPlayers()[0].getTotalMoves()) + " moves");
		if (frame != null) {
			frame.setMoveText(Integer.toString(g.getPlayers()[0].getTotalMoves()) + " moves");
			frame.repaint();
		}
	}
	
	/**
	 * Gets called once a frame update.
	 * @param e
	 * The ActionEvent involved with the update.
	 */
	public void updateFrame(ActionEvent e) {
		if (g != null) {
			if (g.isPlaying()) {
				for (Player pl : g.getPlayers()) {
					pl.addMilliseconds(refreshTime);
				}
				
				// This just exists to test the time system.
				int milliseconds = g.getPlayers()[0].getMillisecondsTaken();
				
				if (frame != null) {
					frame.setTimeText(String.format("%02d:%02d:%03d", milliseconds / 60000, milliseconds / 1000 % 60, milliseconds % 1000));
				}
			}
		}
	}
	
	public static void main(String[] args) {
		MazeProgram p = new MazeProgram();
		p.parseInputs(args);
		p.run();
	}

}
