import java.util.ArrayList;

public class MazeGame {
	private Maze m;
	private ArrayList<Player> players;
	private boolean isPlaying;
	
	/**
	 * Creates a maze game with some default conditions.
	 */
	public MazeGame() {
		this(25, 20, MazeGenerationStrategy.DEPTHFIRSTSEARCH, 0);
	}
	
	/**
	 * Creates a maze game with given conditions.
	 * @param width
	 * The width of the maze.
	 * @param height
	 * The height of the maze.
	 * @param seed
	 * The seed of the maze (0 is random).
	 */
	public MazeGame(int width, int height, MazeGenerationStrategy strategy, long seed) {
		switch (strategy) {
		case DEPTHFIRSTSEARCH:
			m = new DepthFirstSearchMaze(width, height);
			break;
		case RING:
			m = new RingMaze(width, height);
			break;
		case PRIMS:
			m = new PrimsMaze(width, height);
			break;
		default:
			m = null;
			break;
		}
		
		if (seed == 0) {
			m.generateMaze();
		} else {
			m.generateMaze(seed);
		}
		players = new ArrayList<Player>();
		isPlaying = false;
	}
	
	/**
	 * Adds a player that has already been created.
	 * The player will automatically be moved to the start.
	 * @param p
	 */
	public void addPlayer(Player p) {
		if (!players.contains(p)) {
			players.add(p);
			p.setMaze(m);
			p.setPos(m.getStart());
		}
	}
	
	/**
	 * Adds a new player from scratch. It automatically assigns it the maze
	 * and the start position.
	 */
	public void addPlayer() {
		addPlayer(new Player(m, m.getStart()));
	}
	
	/**
	 * Returns the maze object that the game is using.
	 * @return
	 * The maze object.
	 */
	public Maze getMaze() {
		return m;
	}
	
	/**
	 * Returns whether the game is still playing, or whether it has ended.
	 * @return
	 * True if the game is playing, or false if it has ended.
	 */
	public boolean isPlaying() {
		return isPlaying;
	}
	
	/**
	 * Sets the game's playing state.
	 * @param playing
	 * The game's playing state.
	 * @return
	 * The value passed in.
	 */
	public boolean setPlaying(boolean playing) {
		return isPlaying = playing;
	}
	
	/**
	 * Returns all players in the game.
	 * @return
	 * An array of all players in the game.
	 */
	public Player[] getPlayers() {
		return players.toArray(new Player[players.size()]);
	}
	
	/**
	 * Returns the number of players that are sitting at the goal.
	 * @return
	 * The number of players that are sitting at the goal.
	 */
	public int getPlayersAtGoal() {
		int goals = 0;
		for (Player p : players) {
			if (p.isFinished()) {
				goals++;
			}
		}
		return goals;
	}
	
	/**
	 * Marks all players who are at the goal as finished.
	 */
	public void autoFinishPlayers() {
		for (Player p : players) {
			if (p.getPos().equals(m.getEnd())) {
				p.setFinished(true);
			}
		}
	}
	
	/**
	 * Returns true if every player is at the goal.
	 * @return
	 * Whether every player is at the goal.
	 */
	public boolean isEveryoneAtGoal() {
		if (getPlayersAtGoal() == players.size()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns the seed of the game.
	 * @return
	 * The seed of the game.
	 */
	public long getSeed() {
		return m.getSeed();
	}
	
	/**
	 * Returns the strategy of the maze generation.
	 * @return
	 * The maze generation strategy.
	 */
	public MazeGenerationStrategy getGenerationStrategy() {
		return m.getStrategy();
	}
	
	/**
	 * Returns an array of all the coins in the maze.
	 * @return
	 * An array of all the coins in the maze.
	 */
	public Coin[] getCoins() {
		return m.getCoins();
	}
	
	/**
	 * Prints the map to the console using a specific character for walls and spaces.
	 * It draws walls as their own spots in the grid.
	 * @param m
	 * The maze to be drawn.
	 */
	public void printMazeASCII(Maze m) {
		
		// You can easily modify the appearance here.
		final char wallChar = '#';
		final char spaceChar = '.';
		final String wall = Character.toString(wallChar);
		final String space = Character.toString(spaceChar);
		
		// Firstly, the top wall is printed.
		for (int i = 0; i < m.getWidth() * 2 + 1; i++) {
			System.out.print(wall);
		}
		
		System.out.println();
			
		// For each y row, we have two loops.
		for (int y = 0; y < m.getHeight(); y++) {
			// Left wall is printed.
			System.out.print(wall);
			// Then, each space is printed, followed by whether it has a
			// connection to the right. This will always draw the right wall.
			for (int x = 0; x < m.getWidth(); x++) {
				System.out.print(space);
				if (m.isConnected(x, y, Direction.RIGHT)) {
					System.out.print(space);
				} else {
					System.out.print(wall);
				}
			}
			System.out.println();
			
			// Another left wall is printed.
			System.out.print(wall);
			// Finally, each space's down connection is printed, followed by
			// a wall.
			for (int x = 0; x < m.getWidth(); x++) {
				if (m.isConnected(x, y, Direction.DOWN)) {
					System.out.print(space);
				} else {
					System.out.print(wall);
				}
				System.out.print(wall);
			}
			System.out.println();
		}
	}
}
