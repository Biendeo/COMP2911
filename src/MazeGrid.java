
/**
 * A basic class that stores whether a space is connected in any direction.
 * It is all that is necessary to define the maze.
 * It shoud be used more as a copyable struct rather than a class.
 *
 */
public class MazeGrid {
	// True if connected in that direction.
	public boolean left;
	public boolean top;
	public boolean right;
	public boolean bottom;
	
	/**
	 * Creates a new maze grid object.
	 */
	public MazeGrid() {
		left = false;
		top = false;
		right = false;
		bottom = false;
	}

}
