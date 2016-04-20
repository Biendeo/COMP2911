
// TODO: Formally decide what this class is.
// Thomas: I'm using this more as a struct than a class.
/**
 * A basic class that stores whether a space is connected in any direction.
 * It is all that is necessary to define the maze.
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
