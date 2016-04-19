import java.util.Random;
import java.util.Stack;

// TODO: Formally decide what a maze is, and what it stores.
public class Maze {
	private MazeGrid[][] maze;
	private int width;
	private int height;
	private Coord start;
	private Coord end;
	
	public Maze(int width, int height) {
		this.width = width;
		this.height = height;
		this.maze = new MazeGrid[width][height];
		// The start is the bottom-left, the end is the top-right.
		this.start = new Coord(0, height - 1);
		this.end = new Coord(width - 1, 0);
	}
	
	public void generateMapDepthStyle() {
		Random rand = new Random();
		Stack<Coord> currentPath = new Stack<Coord>();
		Coord current = start.clone();
		
		currentPath.push(current);
		maze[current.x][current.y] = new MazeGrid();
			
		while (!currentPath.isEmpty()) {
			current = currentPath.peek();
			
			boolean checkedSide = false;
			// TODO: This is really ugly.
			boolean checkedUp = false;
			boolean checkedRight = false;
			boolean checkedDown = false;
			boolean checkedLeft = false;
			
			// TODO: 
			while (!checkedSide) {
				int randomNum = rand.nextInt(4);
				// 0 is up, 1 is right, 2 is down, 3 is left.
				
				switch (randomNum) {
				case 0:
					if (!isOutOfBound(current.x, current.y - 1) && maze[current.x][current.y - 1] == null) {
						currentPath.push(new Coord(current.x, current.y - 1));
						maze[current.x][current.y - 1] = new MazeGrid();
						maze[current.x][current.y].top = true;
						maze[current.x][current.y - 1].bottom = true;
						checkedSide = true;
					}
					checkedUp = true;
					break;
				case 1:
					if (!isOutOfBound(current.x + 1, current.y) && maze[current.x + 1][current.y] == null) {
						currentPath.push(new Coord(current.x + 1, current.y));
						maze[current.x + 1][current.y] = new MazeGrid();
						maze[current.x][current.y].right = true;
						maze[current.x + 1][current.y].left = true;
						checkedSide = true;
					}
					checkedRight = true;
					break;
				case 2:
					if (!isOutOfBound(current.x, current.y + 1) && maze[current.x][current.y + 1] == null) {
						currentPath.push(new Coord(current.x, current.y + 1));
						maze[current.x][current.y + 1] = new MazeGrid();
						maze[current.x][current.y].bottom = true;
						maze[current.x][current.y + 1].top = true;
						checkedSide = true;
					}
					checkedDown = true;
					break;
				case 3:
					if (!isOutOfBound(current.x - 1, current.y) && maze[current.x - 1][current.y] == null) {
						currentPath.push(new Coord(current.x - 1, current.y));
						maze[current.x - 1][current.y] = new MazeGrid();
						maze[current.x][current.y].left = true;
						maze[current.x - 1][current.y].right = true;
						checkedSide = true;
					}
					checkedLeft = true;
					break;
				}
				
				if (checkedUp && checkedRight && checkedDown && checkedLeft) {
					currentPath.pop();
					break;
				}
			}
		}
	}
	
	private boolean isOutOfBound(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isOutOfBound(Coord c) {
		return isOutOfBound(c.x, c.y);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean isConnectedLeft(int x, int y) {
		if (!isOutOfBound(x, y)) {
			return maze[x][y].left;
		} else {
			return false;
		}
	}
	
	public boolean isConnectedUp(int x, int y) {
		if (!isOutOfBound(x, y)) {
			return maze[x][y].top;
		} else {
			return false;
		}
	}
	
	public boolean isConnectedRight(int x, int y) {
		if (!isOutOfBound(x, y)) {
			return maze[x][y].right;
		} else {
			return false;
		}
	}
	
	public boolean isConnectedDown(int x, int y) {
		if (!isOutOfBound(x, y)) {
			return maze[x][y].bottom;
		} else {
			return false;
		}
	}
	
	public boolean isConnectedLeft(Coord c) {
		return isConnectedLeft(c.x, c.y);
	}
	
	public boolean isConnectedUp(Coord c) {
		return isConnectedUp(c.x, c.y);
	}
	
	public boolean isConnectedRight(Coord c) {
		return isConnectedRight(c.x, c.y);
	}
	
	public boolean isConnectedDown(Coord c) {
		return isConnectedDown(c.x, c.y);
	}
}
