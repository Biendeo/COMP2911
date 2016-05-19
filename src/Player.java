import java.awt.image.BufferedImage;

/**
 * The player class, which has details about the player objects themselves.
 *
 */
public class Player {
	private Maze m;
	private Coord pos;
	private BufferedImage img;
	private int id;
	
	private int totalMoves;
	private int millisecondsTaken;
	
	private int coinsCollected;
	
	private boolean finished;
	
	private static int nextId = 1;
	
	/**
	 * Creates a new player
	 * @param m
	 * A reference to the maze it is in.
	 * @param pos
	 * Where it is.
	 */
	public Player(Maze m, Coord pos) {
		this.m = m;
		this.pos = pos;
		this.img = null;
		this.id = nextId++;
		this.totalMoves = 0;
		this.millisecondsTaken = 0;
		this.coinsCollected = 0;
		this.finished = false;
	}
	
	/**
	 * Get where the player is (read-only).
	 * @return
	 * Where the player is.
	 */
	public Coord getPos() {
		return pos.clone();
	}

	/**
	 * Returns the player image.
	 * @return
	 * The player image.
	 */
	public BufferedImage getImg() {
		return img;
	}
	
	public void setImg(BufferedImage image) {
		img = image;
	}
	
	/**
	 * Get the id of the player.
	 * @return
	 * The id of the player.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Gets the total moves taken by this player.
	 * @return
	 * The total moves taken by this player.
	 */
	public int getTotalMoves() {
		return totalMoves;
	}
	
	/**
	 * Gets the total time taken by this player.
	 * @return
	 * The total time taken by this player.
	 */
	public int getMillisecondsTaken() {
		return millisecondsTaken;
	}
	
	/**
	 * Adds a given milliseconds to the player.
	 * @param ms
	 * The milliseconds added.
	 * @return
	 * The new total milliseconds.
	 */
	public int addMilliseconds(int ms) {
		return (millisecondsTaken += ms);
	}
	
	/**
	 * Moves the player in a direction. Nothing happens if the player is unable
	 * to move in that direction.
	 * @param d
	 * The direction in question.
	 * @return
	 * Whether the player moved.
	 */
	public boolean move(Direction d) {
		if (m.canMove(pos, d)) {
			pos.move(d);
			totalMoves++;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Sets the player's position manually.
	 * @param c
	 * The coordinate to move them to.
	 */
	public void setPos(Coord c) {
		pos = c.clone();
	}
	
	/**
	 * Sets the player's maze.
	 * @param m
	 * The maze that the player is in.
	 */
	public void setMaze(Maze m) {
		this.m = m;
	}
	
	/**
	 * Gets the number of coins the player has collected.
	 * @return
	 * The number of coins the player has collected.
	 */
	public int getCoinsCollected() {
		return coinsCollected;
	}
	
	/**
	 * Increments the number of coins the player has collected by one.
	 */
	public void collectCoin() {
		coinsCollected++;
	}
	
	/**
	 * Returns whether the player has finished the game.
	 * @return
	 * Whether the player has finished the game.
	 */
	public boolean isFinished() {
		return finished;
	}
	
	/**
	 * Sets whether the player has finished the game.
	 * @param finished
	 * Whether the player has finished the game.
	 */
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
}
