import java.util.ArrayList;

public class MazeGame {
	private Maze m;
	private ArrayList<Player> players;
	private boolean isPlaying;
	
	// Other game related stuff.
	
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
		case NONE:
		default:
			m = null;
			// TODO: Maybe throw an error? (ideally this never gets called).
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
			if (p.getPos().equals(m.getEnd())) {
				goals++;
			}
		}
		return goals;
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
	
	public MazeGenerationStrategy getGenerationStrategy() {
		return m.getStrategy();
	}
}
