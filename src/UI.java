import java.awt.Color;
import java.util.Random;

/**
 * Provides static functions to drawing mazes to the console.
 *
 */
public class UI {
	/**
	 * Prints the map to the console using a specific character for walls and spaces.
	 * It draws walls as their own spots in the grid.
	 * @param m
	 * The maze to be drawn.
	 */
	public static void printMazeASCII(Maze m) {
		
		// You can easily modify the appearance here.
		final char wallChar = '#';
		final char spaceChar = '.';
		final String wall = Character.toString(wallChar);
		final String space = Character.toString(spaceChar);
		
		// Firstly, the top wall is printed.
		for (int i = 0; i < m.getWidth() * 2 + 1; i++) {
			System.out.print(wall);
		}
		
		System.out.println();
			
		// For each y row, we have two loops.
		for (int y = 0; y < m.getHeight(); y++) {
			// Left wall is printed.
			System.out.print(wall);
			// Then, each space is printed, followed by whether it has a
			// connection to the right. This will always draw the right wall.
			for (int x = 0; x < m.getWidth(); x++) {
				System.out.print(space);
				if (m.isConnected(x, y, Direction.RIGHT)) {
					System.out.print(space);
				} else {
					System.out.print(wall);
				}
			}
			System.out.println();
			
			// Another left wall is printed.
			System.out.print(wall);
			// Finally, each space's down connection is printed, followed by
			// a wall.
			for (int x = 0; x < m.getWidth(); x++) {
				if (m.isConnected(x, y, Direction.DOWN)) {
					System.out.print(space);
				} else {
					System.out.print(wall);
				}
				System.out.print(wall);
			}
			System.out.println();
		}
	}
	
	/**
	 * Creates a random RGB colour.
	 * @return
	 * The colour generated.
	 */
	public static Color getRandomColor() {
		Random rand = new Random();
		// TODO: This can give really bad colours sometimes (close to white for example). Make it create interesting colours (or just force set primary colours).
		return new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
	}
}
