import java.util.Random;
import java.util.Stack;

public class RingMaze extends Maze {
	public RingMaze(int width, int height) {
		super(width, height);
		strategy = MazeGenerationStrategy.RING;
	}
	
	/**
	 * The process to creating a maze using a randomised depth-first search.
	 * @param rand
	 * The random generator.
	 */
	// TODO: This function.
	public void generateMaze(long seed) {
		this.seed = seed;
		Random rand = new Random(seed);
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				maze[x][y] = new MazeGrid();
			}
		}
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (x == y && (x < width / 2 && y < height / 2)) {
					maze[x][y].bottom = true;
					maze[x][y].right = true;
				} else if (width - x - 1 == y && (x >= width / 2 && y < height / 2)) {
					maze[x][y].bottom = true;
					maze[x][y].left = true;
				} else if (x == height - y - 1 && (x < width / 2 && y >= height / 2)) {
					maze[x][y].top = true;
					maze[x][y].right = true;
				} else if (width - x - 1 == height - y - 1 && (x >= width / 2 && y >= height / 2)) {
					maze[x][y].top = true;
					maze[x][y].left = true;
				} else if (x > y && width - x - 1 > y) {
					maze[x][y].left = true;
					maze[x][y].right = true;
				} else if (height - y - 1 < width - x - 1 && height - y - 1 < x) {
					maze[x][y].left = true;
					maze[x][y].right = true;
				} else if (y > x) {
					maze[x][y].top = true;
					maze[x][y].bottom = true;
				} else if (y > width - x - 1) {
					maze[x][y].top = true;
					maze[x][y].bottom = true;
				}
			}
		}
		
		// Add in the set boundaries on each ring.
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (x + 1 == width / 2 && y < height / 2 && y % 2 == 0) {
					maze[x][y].right = false;
					maze[x + 1][y].left = false;
				} else if (x + 1 == width / 2 && y >= height / 2 && (height - y - 1) % 2 == 0) {
					maze[x][y].right = false;
					maze[x + 1][y].left = false;
				} else if (y + 1 == height / 2 && x < width / 2 && x % 2 == 1) {
					maze[x][y].bottom = false;
					maze[x][y + 1].top = false;
				} else if (y + 1 == height / 2 && x >= width / 2 && (width - x - 1) % 2 == 1) {
					maze[x][y].bottom = false;
					maze[x][y + 1].top = false;
				}
			}
		}
		
		// Then go through a random process to create pathways.
		int rings = 0;
		if (width < height) {
			rings = (width + 1) / 2;
		} else {
			rings = (height + 1) / 2;
		}
		
		Coord c = new Coord(0, 0);
		
		Stack<Boolean> topOrLeftPreviously = new Stack<Boolean>();
		
		for (int i = 0; i < rings; i++) {
			if (i % 2 == 0) {
				int availablePlaces = 2 * (width / 2 - i - 1) + height - (2 * (i + 1));
				if (availablePlaces <= 0) {
					continue;
				}
				int randomInt = rand.nextInt(availablePlaces);
				
				if (randomInt < (width / 2 - i - 1)) {
					c.x = i + randomInt + 1;
					c.y = i;
					maze[c.x][c.y].bottom = true;
					maze[c.x][c.y + 1].top = true;
					c.x = width - c.x - 1;
					c.y = height - c.y - 1;
					maze[c.x][c.y].top = true;
					maze[c.x][c.y - 1].bottom = true;
				} else if (randomInt < (width / 2 - i - 1) + height - (2 * (i + 1))) {
					c.x = i;
					c.y = i + randomInt - (width / 2 - i - 1);
					maze[c.x][c.y].right = true;
					maze[c.x + 1][c.y].left = true;
					c.x = width - c.x - 1;
					c.y = height - c.y - 1;
					maze[c.x][c.y].left = true;
					maze[c.x - 1][c.y].right = true;
				} else {
					c.x = i + randomInt - (width / 2 - i) - height + (2 * (i + 1)) + 1;
					c.y = height - i - 1;
					maze[c.x][c.y].top = true;
					maze[c.x][c.y - 1].bottom = true;
					c.x = width - c.x - 1;
					c.y = height - c.y - 1;
					maze[c.x][c.y].bottom = true;
					maze[c.x][c.y + 1].top = true;
				}
				
				if (c.y < height / 2) {
					topOrLeftPreviously.push(new Boolean(true));
				} else {
					topOrLeftPreviously.push(new Boolean(false));
				}
			} else {
				int availablePlaces = 2 * (height / 2 - i - 1) + width - (2 * (i + 1));
				if (availablePlaces <= 0) {
					continue;
				}
				int randomInt = rand.nextInt(availablePlaces);
				
				if (randomInt < (height / 2 - i - 1)) {
					c.x = i;
					c.y = i + randomInt + 1;
					maze[c.x][c.y].right = true;
					maze[c.x + 1][c.y].left = true;
					c.x = width - c.x - 1;
					c.y = height - c.y - 1;
					maze[c.x][c.y].left = true;
					maze[c.x - 1][c.y].right = true;
				} else if (randomInt < (height / 2 - i - 1) + width - (2 * (i + 1))) {
					c.x = i + randomInt - (height / 2 - i - 1);
					c.y = i;
					maze[c.x][c.y].bottom = true;
					maze[c.x][c.y + 1].top = true;
					c.x = width - c.x - 1;
					c.y = height - c.y - 1;
					maze[c.x][c.y].top = true;
					maze[c.x][c.y - 1].bottom = true;
				} else {
					c.x = width - i - 1;
					c.y = i + randomInt - (height / 2 - i) - width + (2 * (i + 1)) + 1;
					maze[c.x][c.y].left = true;
					maze[c.x - 1][c.y].right = true;
					c.x = width - c.x - 1;
					c.y = height - c.y - 1;
					maze[c.x][c.y].right = true;
					maze[c.x + 1][c.y].left = true;
				}
				
				if (c.x < width / 2) {
					topOrLeftPreviously.push(new Boolean(true));
				} else {
					topOrLeftPreviously.push(new Boolean(false));
				}
			}
		}
		
		// TODO: Do a truely random opposite side.
		/*
		for (int i = rings - 1; i >= 0; i--) {
			if (i % 2 == 0) {
				if (topOrLeftPreviously.peek().booleanValue()) {
					int availablePlaces = 2 * (width / 2 - i - 1) + height - (2 * i);
					int randomInt = rand.nextInt(availablePlaces);
					
					if (randomInt < (width / 2 - i - 1)) {
						c.x = i + randomInt + 1;
						c.y = i;
						maze[c.x][c.y].bottom = true;
						maze[c.x][c.y + 1].top = true;
					} else if (randomInt < (width / 2 - i - 1) + height - (2 * i)) {
						c.x = i;
						c.y = i + randomInt - (width / 2 - i - 1);
						maze[c.x][c.y].right = true;
						maze[c.x + 1][c.y].left = true;
					} else {
						c.x = i + randomInt - (width / 2 - i) - height + (2 * i);
						c.y = height - i - 1;
						maze[c.x][c.y].top = true;
						maze[c.x][c.y - 1].bottom = true;
					}
				} else {
					
				}
			} else {
				if (topOrLeftPreviously.peek().booleanValue()) {
					
				} else {
					
				}
			}
			topOrLeftPreviously.pop();
		}
		*/
		
		placeCoins(rand);
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

	protected void placeCoins(Random rand) {
		int totalCoins = width * height / 20;
		
		for (int i = 0; i < totalCoins; i++) {
			boolean foundSpot = false;
			Coord coinPos = null;
			while (!foundSpot) {
				foundSpot = true;
				coinPos = new Coord(rand.nextInt(width), rand.nextInt(height));
				if (!coinPos.equals(start) && !coinPos.equals(end)) {
					for (Coin c : coins) {
						if (coinPos.equals(c.getPos())) {
							foundSpot = false;
						}
					}
				} else {
					foundSpot = false;
				}
			}
			
			if (foundSpot) {
				coins.add(new Coin(coinPos));
			}
		}
	}
}
