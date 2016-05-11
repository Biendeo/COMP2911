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
		this(25, 20, 0);
	}
	
	public MazeGame(int width, int height, long seed) {
		m = new Maze(width, height);
		if (seed == 0) {
			seed = System.currentTimeMillis();
			m.generateMapDepthStyle(seed);
		} else {
			m.generateMapDepthStyle(seed);
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
}
