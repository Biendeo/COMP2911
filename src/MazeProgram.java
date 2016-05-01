/**
 * The main execution of the program. It creates a maze and calls basic UI
 * functions. It should eventually handle input and possible other features.
 * @author Group 2
 *
 */
public class MazeProgram {
	
	public static void main(String[] args) {

		MazeGame g = new MazeGame();
		g.addPlayer();
		
		UI.printMazeASCII(g.getMaze());
		
		UIFrame theFrame = new UIFrame(g);
		
		theFrame.drawMazeSwingOne();
		
		theFrame.setVisible(true);
	}

}
