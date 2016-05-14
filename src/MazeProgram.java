import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

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
	private UI frame;
	private UIKeyGetter keyGet;
	
	private Timer refresh;
	
	// If anyone complains about this line, just replace it with 60 (CSE computers are 50).
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
		frame = new UI(this, cfg);
		
		frame.addKeyListener(keyGet);
		
		frame.setVisible(true);
		frame.setFocusable(true);
	}
	
	/**
	 * Starts a new game with default parameters.
	 */
	public void instantAction() {
		customGameSetup(25, 20, 0, 1);
	}
	
	/**
	 * Tells the frame to exit the game.
	 */
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
		if (g != null && g.isPlaying()) {
			Player[] players = g.getPlayers();
			int keyCode = ke.getKeyCode();
			try {
				if (keyCode == cfg.getPlayer1Up()) {
					players[0].move(Direction.UP);
				} else if (keyCode == cfg.getPlayer1Right()) {
					players[0].move(Direction.RIGHT);
				} else if (keyCode == cfg.getPlayer1Down()) {
					players[0].move(Direction.DOWN);
				} else if (keyCode == cfg.getPlayer1Left()) {
					players[0].move(Direction.LEFT);
				} else if (keyCode == cfg.getPlayer2Up()) {
					players[1].move(Direction.UP);
				} else if (keyCode == cfg.getPlayer2Right()) {
					players[1].move(Direction.RIGHT);
				} else if (keyCode == cfg.getPlayer2Down()) {
					players[1].move(Direction.DOWN);
				} else if (keyCode == cfg.getPlayer2Left()) {
					players[1].move(Direction.LEFT);
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
	
	/**
	 * Toggles pausing the game.
	 */
	public void togglePauseGame() {
		if (g != null) {
			if (g.isPlaying()) {
				g.setPlaying(false);
				frame.showPause(true);
			} else {
				if (!g.isEveryoneAtGoal()) {
					g.setPlaying(true);
					frame.showPause(false);
				}
			}
		}
	}
	
	/**
	 * Sets up a custom game with some parameters.
	 * @param width
	 * The width of the maze.
	 * @param height
	 * The height of the maze.
	 * @param seed
	 * The seed to be used (0 picks a random one).
	 * @param players
	 * The number of players.
	 */
	public void customGameSetup(int width, int height, long seed, int players) {
		g = new MazeGame(width, height, seed);
		
		for (int i = 0; i < players; i++) {
			g.addPlayer();
			if (i == 0) {
				g.getPlayers()[0].setImg(cfg.getPlayer1Image());
			} else if (i == 1) {
				g.getPlayers()[1].setImg(cfg.getPlayer2Image());
			}
		}
		
		frame.setGame(g);
		
		UI.printMazeASCII(g.getMaze());
		
		g.setPlaying(true);
		
		frame.drawMazeSwingOne();
		
		frame.showPause(false);
		
		frame.setMoveText("0 moves");
		
		frame.setSeedText("Seed: " + Long.toString(g.getSeed()));
		
		frame.switchPanel("mazeViewPanel");
	}
	
	public static void main(String[] args) {
		MazeProgram p = new MazeProgram();
		p.parseInputs(args);
		p.run();
	}

}
