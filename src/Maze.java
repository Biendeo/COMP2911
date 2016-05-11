import java.util.Random;
import java.util.Stack;

// TODO: Formally decide what a maze is, and what it stores.
/**
 * A maze class, that stores data about a maze.
 * Maze generation should only be called once per maze object. Create
 * a new maze if that is required.
 *
 */
public class Maze {
	// The is stored with a 2D array.
	private MazeGrid[][] maze;
	private int width;
	private int height;
	
	// These store the entry and exit points on the maze.
	private Coord start;
	private Coord end;
	
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
	}
	
	/**
	 * Creates a new maze using a randomised depth-first search.
	 * 
	 * This method works by creating a tile at the entry point. Then, each
	 * side is checked in a random order. If the side doesn't exist, then
	 * it is created and checked next (added to a stack). When a dead-end
	 * is reached and all the sides are made, then it pops off the stack
	 * to a point where it can go again. This will fill the entire map area
	 * with no holes, and every place is connected. However, a preset end
	 * location may not always be in a dead-end (but sometimes it can).
	 * 
	 */
	public void generateMapDepthStyle() {
		generateMapDepthStyleProcess(new Random());
	}
	
	/**
	 * Creates a new maze using a randomised depth-first search.
	 * @param seed
	 * A specific seed for the random generation (the same seed will give the
	 * same results).
	 */
	public void generateMapDepthStyle(long seed) {
		generateMapDepthStyleProcess(new Random(seed));
	}
	
	/**
	 * The process to creating a maze using a randomised depth-first search.
	 * @param rand
	 * The random generator.
	 */
	private void generateMapDepthStyleProcess(Random rand) {
		Stack<Coord> currentPath = new Stack<Coord>();
		Coord current = start.clone();
		
		currentPath.push(current);
		maze[current.x][current.y] = new MazeGrid();
		
		// We run until we've run out of places to check (which should mean we've filled the whole maze).
		while (!currentPath.isEmpty()) {
			current = currentPath.peek();
			
			boolean checkedSide = false;
			// TODO: This is really ugly.
			boolean checkedUp = false;
			boolean checkedRight = false;
			boolean checkedDown = false;
			boolean checkedLeft = false;
			
			// This inner loop keeps getting random sides until either it's
			// checked every side, or it's found a side which has an unchecked
			// tile.
			while (!checkedSide) {
				// TODO: Delegate this to a function, and return the enum instead.
				Direction randomDirection = getRandomDirection(rand);
				// 0 is up, 1 is right, 2 is down, 3 is left.
				// This can be modified to add some interesting bias to the generation.
				
				switch (randomDirection) {
				case UP:
					// Check if the side is within the maze dimensions, and whether that position doesn't exist already.
					if (!isOutOfBound(current.x, current.y - 1) && maze[current.x][current.y - 1] == null) {
						// Add that to the stack (so it's checked next).
						currentPath.push(new Coord(current.x, current.y - 1));
						// Create that tile.
						maze[current.x][current.y - 1] = new MazeGrid();
						// Connect that tile to the current tile.
						maze[current.x][current.y].top = true;
						maze[current.x][current.y - 1].bottom = true;
						// Mark that we've found a side.
						checkedSide = true;
					}
					checkedUp = true;
					break;
				case RIGHT:
					if (!isOutOfBound(current.x + 1, current.y) && maze[current.x + 1][current.y] == null) {
						currentPath.push(new Coord(current.x + 1, current.y));
						maze[current.x + 1][current.y] = new MazeGrid();
						maze[current.x][current.y].right = true;
						maze[current.x + 1][current.y].left = true;
						checkedSide = true;
					}
					checkedRight = true;
					break;
				case DOWN:
					if (!isOutOfBound(current.x, current.y + 1) && maze[current.x][current.y + 1] == null) {
						currentPath.push(new Coord(current.x, current.y + 1));
						maze[current.x][current.y + 1] = new MazeGrid();
						maze[current.x][current.y].bottom = true;
						maze[current.x][current.y + 1].top = true;
						checkedSide = true;
					}
					checkedDown = true;
					break;
				case LEFT:
					if (!isOutOfBound(current.x - 1, current.y) && maze[current.x - 1][current.y] == null) {
						currentPath.push(new Coord(current.x - 1, current.y));
						maze[current.x - 1][current.y] = new MazeGrid();
						maze[current.x][current.y].left = true;
						maze[current.x - 1][current.y].right = true;
						checkedSide = true;
					}
					checkedLeft = true;
					break;
				}
				
				// If every side was checked and no spaces were found, then we pop this off.
				if (!checkedSide && checkedUp && checkedRight && checkedDown && checkedLeft) {
					currentPath.pop();
					break;
				}
			}
		}
	}
	
	/**
	 * Returns whether the coord is valid in the map (in the boundaries).
	 * @param x
	 * The x coordinate.
	 * @param y
	 * The y coordinate.
	 * @return
	 * Whether it is inside the map boundaries.
	 */
	private boolean isOutOfBound(int x, int y) {
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
	private boolean isOutOfBound(Coord c) {
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
	 * Gets a random direction based on a random generator.
	 * @param r
	 * The random generator.
	 * @return
	 * The direction decided.
	 */
	// TODO: Add a way to add bias.
	public Direction getRandomDirection(Random r) {
		int randomInt = r.nextInt(4);
		switch(randomInt) {
		case 0:
		default:
			return Direction.UP;
		case 1:
			return Direction.RIGHT;
		case 2:
			return Direction.DOWN;
		case 3:
			return Direction.LEFT;
		}
	}
}
