import java.io.Serializable;

/**
 * An entry in the leaderboard that stores information about a certain run.
 *
 */
public class LeaderboardEntry implements Serializable {
	private static final long serialVersionUID = -5590923297868581983L;
	private int mazeWidth;
	private int mazeHeight;
	private long mazeSeed;
	private MazeGenerationStrategy mazeStrategy;
	private int timeMillis;
	private int movesTaken;
	private int coinsCollected;
	

	
	/**
	 * Creates a new leaderboard entry.
	 * @param mazeWidth
	 * The width of the maze.
	 * @param mazeHeight
	 * The height of the maze.
	 * @param mazeSeed
	 * The seed of the maze.
	 * @param mazeStrategy
	 * The strategy used by the maze.
	 * @param timeMillis
	 * The time it took the player to clear it (in milliseconds).
	 * @param movesTaken
	 * The moves taken by the player.
	 * @param coinsCollected
	 * The number of coins the player collected.
	 */
	public LeaderboardEntry(int mazeWidth, int mazeHeight, long mazeSeed, MazeGenerationStrategy mazeStrategy, int timeMillis, int movesTaken, int coinsCollected) {
		this.mazeWidth = mazeWidth;
		this.mazeHeight = mazeHeight;
		this.mazeSeed = mazeSeed;
		this.mazeStrategy = mazeStrategy;
		this.timeMillis = timeMillis;
		this.movesTaken = movesTaken;
		this.coinsCollected = coinsCollected;
	}
	
	// TODO: Automatic comments, away!
	/**
	 * @return the mazeWidth
	 */
	public int getMazeWidth() {
		return mazeWidth;
	}

	/**
	 * @return the mazeHeight
	 */
	public int getMazeHeight() {
		return mazeHeight;
	}
	
	/**
	 * @return the mazeSeed
	 */
	public long getMazeSeed() {
		return mazeSeed;
	}
	
	/**
	 * @return the mazeStrategy
	 */
	public MazeGenerationStrategy getMazeStrategy() {
		return mazeStrategy;
	}

	/**
	 * @return the timeMillis
	 */
	public int getTimeMillis() {
		return timeMillis;
	}

	/**
	 * @return the movesTaken
	 */
	public int getMovesTaken() {
		return movesTaken;
	}

	/**
	 * @return the coinsCollected
	 */
	public int getCoinsCollected() {
		return coinsCollected;
	}
}
