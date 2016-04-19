
public class UI {
	public static void printMazeASCII(Maze m) {
		
		// You can easily modify the appearance here.
		final char wallChar = '#';
		final char spaceChar = '.';
		final String wall = Character.toString(wallChar);
		final String space = Character.toString(spaceChar);
		
		for (int i = 0; i < m.getWidth() * 2 + 1; i++) {
			System.out.print(wall);
		}
		
		System.out.println();
			
		for (int y = 0; y < m.getHeight(); y++) {
			System.out.print(wall);
			for (int x = 0; x < m.getWidth(); x++) {
				System.out.print(space);
				if (m.isConnectedRight(x, y)) {
					System.out.print(space);
				} else {
					System.out.print(wall);
				}
			}
			System.out.println();
			
			System.out.print(wall);
			for (int x = 0; x < m.getWidth(); x++) {
				if (m.isConnectedDown(x, y)) {
					System.out.print(space);
				} else {
					System.out.print(wall);
				}
				System.out.print(wall);
			}
			System.out.println();
		}
	}
}
