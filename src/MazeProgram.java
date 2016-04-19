/**
 * @author Group 2
 *
 */
public class MazeProgram {
	
	public static void main(String[] args) {

		Maze m = new Maze(20, 15); // Any width and height works, but this makes a pretty decent one.
		m.generateMapDepthStyle();
		
		UI.printMazeASCII(m);
	}

}
