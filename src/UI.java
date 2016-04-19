
public class UI {
	public static void printMazeASCII(Maze m) {
		
		for (int i = 0; i < m.getWidth() * 2 + 1; i++) {
			System.out.print("#");
		}
		
		System.out.println();
			
		for (int y = 0; y < m.getHeight(); y++) {
			System.out.print("#");
			for (int x = 0; x < m.getWidth(); x++) {
				System.out.print(" ");
				if (m.isConnectedRight(x, y)) {
					System.out.print(" ");
				} else {
					System.out.print("#");
				}
			}
			System.out.println();
			
			System.out.print("#");
			for (int x = 0; x < m.getWidth(); x++) {
				if (m.isConnectedDown(x, y)) {
					System.out.print(" ");
				} else {
					System.out.print("#");
				}
				System.out.print("#");
			}
			System.out.println();
		}
	}
}
