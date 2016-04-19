
// TODO: Formally decide what this class is.
// Thomas: I'm using this more as a struct than a class.
public class MazeGrid {
	// True if connected in that direction.
	public boolean left;
	public boolean top;
	public boolean right;
	public boolean bottom;
	
	public MazeGrid() {
		left = false;
		top = false;
		right = false;
		bottom = false;
	}

}
