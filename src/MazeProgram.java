import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

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
	private UIMazeKeyListener keyGet;
	
	private Timer refresh;

	private static int refreshRate = 60;
	private static int refreshTime = (int)((float)1000 / refreshRate);
	
	/**
	 * Creates a new MazeProgram.
	 */
	public MazeProgram() {
		cfg = new Config();
		cfg.loadConfig();
		// g will be null initially.
		// frame will be null initially.
		
		keyGet = new UIMazeKeyListener(this);

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
		customGameSetup(25, 20, MazeGenerationStrategy.DEPTHFIRSTSEARCH, 0, 1);
	}
	
	/**
	 * Tells the frame to exit the game.
	 */
	public void exitMazeGame() {
		frame.setGame(null);
		frame.switchPanel("mainMenuPanel");
	}
	
	/**
	 * Responds to a KeyEvent.
	 * @param ke
	 * The key event.
	 */
	public void keyPress(KeyEvent ke) {
		if (g != null && g.isPlaying()) {
			Player[] players = g.getPlayers();
			Coin[] coins = g.getCoins();
			int keyCode = ke.getKeyCode();
			try {
				if (players.length >= 1 && !players[0].isFinished()) {
					if (keyCode == cfg.getKeyBinding(ControlCode.PLAYER1UP)) {
						players[0].move(Direction.UP);
					} else if (keyCode == cfg.getKeyBinding(ControlCode.PLAYER1RIGHT)) {
						players[0].move(Direction.RIGHT);
					} else if (keyCode == cfg.getKeyBinding(ControlCode.PLAYER1DOWN)) {
						players[0].move(Direction.DOWN);
					} else if (keyCode == cfg.getKeyBinding(ControlCode.PLAYER1LEFT)) {
						players[0].move(Direction.LEFT);
					}
				}
				
				if (players.length >= 2 && !players[1].isFinished()) {
					if (keyCode == cfg.getKeyBinding(ControlCode.PLAYER2UP)) {
						players[1].move(Direction.UP);
					} else if (keyCode == cfg.getKeyBinding(ControlCode.PLAYER2RIGHT)) {
						players[1].move(Direction.RIGHT);
					} else if (keyCode == cfg.getKeyBinding(ControlCode.PLAYER2DOWN)) {
						players[1].move(Direction.DOWN);
					} else if (keyCode == cfg.getKeyBinding(ControlCode.PLAYER2LEFT)) {
						players[1].move(Direction.LEFT);
					}
				}
			} catch (Exception e) {
				// Do nothing.
			}
			
			ArrayList<Boolean> hasFinished = new ArrayList<Boolean>();
			
			for (Player p : players) {
				hasFinished.add(new Boolean(p.isFinished()));
			}
			
			g.autoFinishPlayers();
			
			for (int i = 0; i < players.length; i++) {
				for (Coin c : coins) {
					if (players[i].getPos().equals(c.getPos())) {
						if (i == 0 && !c.getCollectedPlayer1()) {
							c.setCollectedPlayer1(true);
							players[i].collectCoin();
						} else if (i == 1 && !c.getCollectedPlayer2()) {
							c.setCollectedPlayer2(true);
							players[i].collectCoin();
						}
					}
				}
			}
	
			if (g.isEveryoneAtGoal()) {
				g.setPlaying(false);
			}
			
			if (players[0].getTotalMoves() == 1) {
				frame.setMoveText(Integer.toString(players[0].getTotalMoves()) + " move");
			} else {
				frame.setMoveText(Integer.toString(players[0].getTotalMoves()) + " moves");
			}
			
			if (players[0].getCoinsCollected() == 1) {
				frame.setCoinText(Integer.toString(players[0].getCoinsCollected()) + " coin");
			} else {
				frame.setCoinText(Integer.toString(players[0].getCoinsCollected()) + " coins");
			}
			frame.repaint();
			
			for (int i = 0; i < players.length; i++) {
				if (!hasFinished.get(i).equals(players[i].isFinished())) {
					if (players.length == 1) {
						JOptionPane.showMessageDialog(null, "You won!", "Winner!", JOptionPane.PLAIN_MESSAGE);
					} else if (i == 0) {
						JOptionPane.showMessageDialog(null, "Player 1 has finished!", "Winner!", JOptionPane.PLAIN_MESSAGE);
					} else if (i == 1) {
						JOptionPane.showMessageDialog(null, "Player 2 has finished!", "Winner!", JOptionPane.PLAIN_MESSAGE);
					}
					
					cfg.addLeaderboardEntry(g.getMaze().getWidth(), g.getMaze().getHeight(), g.getSeed(), g.getGenerationStrategy(), players[i].getMillisecondsTaken(), players[i].getTotalMoves(), players[i].getCoinsCollected());
					frame.addLeaderboardRow(cfg.getLeaderboardTable()[cfg.getLeaderboardTable().length - 1]);
				}
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
	public void customGameSetup(int width, int height, MazeGenerationStrategy strategy, long seed, int players) {
		g = new MazeGame(width, height, strategy, seed);
		
		for (int i = 0; i < players; i++) {
			g.addPlayer();
			if (i == 0) {
				g.getPlayers()[0].setImg(cfg.getPlayer1Image());
			} else if (i == 1) {
				g.getPlayers()[1].setImg(cfg.getPlayer2Image());
			}
		}
		
		frame.setGame(g);
		
		//UI.printMazeASCII(g.getMaze());
		
		g.setPlaying(true);
		
		frame.drawMaze();
		
		frame.showPause(false);
		
		frame.setMoveText("0 moves");
		
		frame.setCoinText("0 coins");
		
		frame.setSeedText("Seed: " + Long.toString(g.getSeed()));
		
		frame.switchPanel("mazeViewPanel");
	}
	
	public static void main(String[] args) {
		if (GraphicsEnvironment.isHeadless()) {
			System.out.println("No screen available, just generating a maze.");
			MazeGame g  = new MazeGame(25, 20, MazeGenerationStrategy.DEPTHFIRSTSEARCH, 0);
			UI.printMazeASCII(g.getMaze());
			return;
		} else {
			MazeProgram p = new MazeProgram();
			p.run();
		}
	}

}
