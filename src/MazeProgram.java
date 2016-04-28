/**
 * The main execution of the program. It creates a maze and calls basic UI
 * functions. It should eventually handle input and possible other features.
 * @author Group 2
 *
 */
public class MazeProgram {
	
	public static void main(String[] args) {

		Maze m = new Maze(25, 20); // Any width and height works, but this makes a pretty decent one.
		m.generateMapDepthStyle();
		
		UI.printMazeASCII(m);
		
		UIFrame theFrame = new UIFrame();
		
		theFrame.drawMazeSwingOne(m);
		
		// If multiplayer is used, this should be an ArrayList.
		Player p = new Player(m, m.getStart());
		
		theFrame.addPlayer(p);
		
		theFrame.setVisible(true);
	}

}
