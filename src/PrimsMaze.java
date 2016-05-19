import java.util.ArrayList;
import java.util.Random;

public class PrimsMaze extends Maze {
	public PrimsMaze(int width, int height) {
		super(width, height);
		strategy = MazeGenerationStrategy.PRIMS;
	}
	
	/**
	 * The process to creating a maze using a randomised depth-first search.
	 * @param rand
	 * The random generator.
	 */
	public void generateMaze(long seed) {
		this.seed = seed;
		Random rand = new Random(seed);
		//Stack<Coord> currentPath = new Stack<Coord>();
		ArrayList<Coord> neighbours = new ArrayList<Coord>();
		Coord current = end.clone();
		
		//currentPath.push(current);
		neighbours.add(current);
		//int currIndex = 0;
		//maze[current.x][current.y] = new MazeGrid();
		int created = 0;
		
		// We run until we've run out of places to check (which should mean we've filled the whole maze).
		while (!neighbours.isEmpty()) {
			//current = currentPath.peek();
			//System.out.println(currIndex);
			//choose a random neighbour
			current = getRandomNeighbour(rand,neighbours);
			System.out.println("current = " + current.x+ ","+current.y);
			if(current.x == 0 && current.y == 0 && neighbours.size()>1){
				current = getRandomNeighbour(rand,neighbours);
			}
			maze[current.x][current.y] = new MazeGrid();
			Coord cd1 = new Coord(current.x+1, current.y);
			Coord cd2 = new Coord(current.x+1, current.y);
			if(cd1==cd2){
				System.out.println("ITS THE SAME");
			}else{
				System.out.println("ITS NO?T THE SAME");
			}
			
			//add neighbours to new neighbour list
			if (!isOutOfBound(current.x, current.y - 1) && maze[current.x][current.y - 1] == null){
				Coord coord =(new Coord(current.x, current.y - 1));
				if(!neighbours.contains(coord)){
					neighbours.add(coord);
				}
			}
			if (!isOutOfBound(current.x + 1, current.y)&& maze[current.x+1][current.y] == null){
				Coord coord =(new Coord(current.x+1, current.y));
				if(!neighbours.contains(coord)){
					neighbours.add(coord);
				}
			}
			if (!isOutOfBound(current.x, current.y + 1)&& maze[current.x][current.y + 1] == null){
				Coord coord =(new Coord(current.x, current.y + 1));
				if(!neighbours.contains(coord)){
					neighbours.add(coord);
				}			}
			if (!isOutOfBound(current.x-1, current.y)&& maze[current.x-1][current.y] == null){
				Coord coord = new Coord(current.x-1, current.y);
				if(!neighbours.contains(coord)){
					neighbours.add(coord);
				}			
			}
			

			created++;
			boolean checkedSide = false;
			// TODO: This is really ugly.
			boolean checkedUp = false;
			boolean checkedRight = false;
			boolean checkedDown = false;
			boolean checkedLeft = false;
			
			// This inner loop keeps getting random sides until either it's
			// checked every side, or it's found a side which has an unchecked
			// tile.
			while (!checkedSide) {
				// TODO: Delegate this to a function, and return the enum instead.
				Direction randomDirection = getRandomDirection(rand);
				
				// 0 is up, 1 is right, 2 is down, 3 is left.
				// This can be modified to add some interesting bias to the generation.
				
				//if direction exists, i.e. visited, break walls between current neighbour and direction
				switch (randomDirection) {
				case UP:
					// Check if the side is within the maze dimensions, and whether that position doesn't exist already.
					if (!isOutOfBound(current.x, current.y - 1) && maze[current.x][current.y - 1] != null) {
						// Add that to the stack (so it's checked next).
						//currentPath.push(new Coord(current.x, current.y - 1));
						// Create that tile.
						// Connect that tile to the current tile.
						maze[current.x][current.y].top = true;
						maze[current.x][current.y - 1].bottom = true;
						// Mark that we've found a side.
						checkedSide = true;
					}
					checkedUp = true;
					break;
				case RIGHT:
					if (!isOutOfBound(current.x + 1, current.y) && maze[current.x + 1][current.y] != null) {
						//currentPath.push(new Coord(current.x + 1, current.y));
						maze[current.x][current.y].right = true;
						maze[current.x + 1][current.y].left = true;
						checkedSide = true;
					}
					checkedRight = true;
					break;
				case DOWN:
					if (!isOutOfBound(current.x, current.y + 1) && maze[current.x][current.y + 1] != null) {
						//currentPath.push(new Coord(current.x, current.y + 1));
						maze[current.x][current.y].bottom = true;
						maze[current.x][current.y + 1].top = true;
						checkedSide = true;
					}
					checkedDown = true;
					break;
				case LEFT:
					if (!isOutOfBound(current.x - 1, current.y) && maze[current.x - 1][current.y] != null) {
						//currentPath.push(new Coord(current.x - 1, current.y));
						maze[current.x][current.y].left = true;
						maze[current.x - 1][current.y].right = true;
						checkedSide = true;
					}
					checkedLeft = true;
					break;
				}
				
				// If every side was checked and no spaces were found, then we pop this off.
				if (checkedSide || (checkedLeft && checkedDown && checkedRight && checkedUp)) {
					//currentPath.pop();
					neighbours.remove(current);
					break;
				}
			}
		}
		
		placeCoins(rand);
		System.out.println("created = " + created);
		System.out.println("seed = " + seed);

	}

	private Coord getRandomNeighbour(Random rand, ArrayList<Coord> neighbours) {
		int randomInt = rand.nextInt(neighbours.size());
		return neighbours.get(randomInt);
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
