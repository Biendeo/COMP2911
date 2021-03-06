import java.util.ArrayList;
import java.util.Random;

/**
 * A maze class, that stores data about a maze.
 * Maze generation should only be called once per maze object. Create
 * a new maze if that is required.
 *
 */
public abstract class Maze {
	// The is stored with a 2D array.
	protected MazeGrid[][] maze;
	protected int width;
	protected int height;
	
	// These store the entry and exit points on the maze.
	protected Coord start;
	protected Coord end;
	
	protected long seed;
	
	protected MazeGenerationStrategy strategy;
	
	protected ArrayList<Coin> coins;
	
	/**
	 * Creates a new maze with given size parameters.
	 * @param width
	 * How many tiles wide it is.
	 * @param height
	 * How many tiles tall it is.
	 */
	public Maze(int width, int height) {
		this.width = width;
		this.height = height;
		this.maze = new MazeGrid[width][height];
		// The start is the bottom-left, the end is the top-right.
		// We can change this at any time.
		this.start = new Coord(0, height - 1);
		this.end = new Coord(width - 1, 0);
		this.seed = 0;
		this.strategy = MazeGenerationStrategy.NONE;
		this.coins = new ArrayList<Coin>();
	}
	
	/**
	 * Creates a new maze using a randomised seed.
	 */
	public void generateMaze() {
		generateMaze(new Random().nextLong());
	}
	
	/**
	 * Creates a new maze using the strategy that has been implemented..
	 * @param seed
	 * A specific seed for the random generation (the same seed will give the
	 * same results).
	 */
	public abstract void generateMaze(long seed);
	
	/**
	 * Returns whether the coord is valid in the map (in the boundaries).
	 * @param x
	 * The x coordinate.
	 * @param y
	 * The y coordinate.
	 * @return
	 * Whether it is inside the map boundaries.
	 */
	protected boolean isOutOfBound(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns whether the coord is valid in the map (in the boundaries).
	 * @param c
	 * The coordinate.
	 * @return
	 * Whether it is inside the map boundaries.
	 */
	protected boolean isOutOfBound(Coord c) {
		return isOutOfBound(c.x, c.y);
	}
	
	/**
	 * Get the width of the maze.
	 * @return
	 * The width of the maze.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Get the height of the maze.
	 * @return
	 * The height of the maze.
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Returns whether the coord has a connection in that direction.
	 * @param c
	 * The coordinate in question.
	 * @param d
	 * The direction to move in.
	 * @return
	 * Whether the coord has a connection in that direction.
	 */
	public boolean isConnected(Coord c, Direction d) {
		if (isOutOfBound(c)) {
			return false;
		}
		switch(d) {
		case UP:
		default:
			return maze[c.x][c.y].top;
		case RIGHT:
			return maze[c.x][c.y].right;
		case DOWN:
			return maze[c.x][c.y].bottom;
		case LEFT:
			return maze[c.x][c.y].left;
		}
	}
	
	/**
	 * Returns whether the coord has a connection in that direction.
	 * @param x
	 * The x-coordinate in question.
	 * @param y
	 * The y-coordinate in question.
	 * @param d
	 * The direction to move in.
	 * @return
	 * Whether the coord has a connection in that direction.
	 */
	public boolean isConnected(int x, int y, Direction d) {
		return isConnected(new Coord(x, y), d);
	}
	
	/**
	 * Same as {@link #isConnected(Coord, Direction)}
	 */
	public boolean canMove(Coord c, Direction d) {
		return isConnected(c, d);
	}
	
	/**
	 * Returns the start coordinate (read-only).
	 * @return
	 * The start coordinate.
	 */
	public Coord getStart() {
		return start.clone();
	}
	
	/**
	 * Returns the end coordinate (read-only).
	 * @return
	 * The end coordinate.
	 */
	public Coord getEnd() {
		return end.clone();
	}
	
	/**
	 * Returns the seed of the maze.
	 * @return
	 * The seed of the maze.
	 */
	public long getSeed() {
		return seed;
	}
	
	/**
	 * Returns the strategy used by this maze.
	 * @return
	 * The strategy used by this maze.
	 */
	public MazeGenerationStrategy getStrategy() {
		return strategy;
	}
	
	/**
	 * Returns an array of all the coins in this maze.
	 * @return
	 * An array of all the coins in the maze.
	 */
	public Coin[] getCoins() {
		return coins.toArray(new Coin[coins.size()]);
	}
	
	/**
	 * Generates coins around the maze. This should place one coin for every
	 * twenty spaces on the maze.
	 * @param rand
	 * The random number generator (should consistent with the same seed).
	 */
	protected abstract void placeCoins(Random rand);
}
