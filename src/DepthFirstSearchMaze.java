import java.util.Random;
import java.util.Stack;

public class DepthFirstSearchMaze extends Maze {
	public DepthFirstSearchMaze(int width, int height) {
		super(width, height);
	}
	
	/**
	 * The process to creating a maze using a randomised depth-first search.
	 * @param rand
	 * The random generator.
	 */
	public void generateMaze(long seed) {
		this.seed = seed;
		Random rand = new Random(seed);
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
	 * Gets a random direction based on a random generator.
	 * @param r
	 * The random generator.
	 * @return
	 * The direction decided.
	 */
	private Direction getRandomDirection(Random r) {
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
