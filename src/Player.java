import java.awt.Color;
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
	
	private static int nextId = 1;
	
	/**
	 * Creates a new player.
	 * @param m
	 * A reference to the maze it is in.
	 * @param pos
	 * Where it is.
	 * @param color
	 * What color it should be.
	 */
	public Player(Maze m, Coord pos, Color color) {
		this.m = m;
		this.pos = pos;
		this.img = generateImage(color, 16);
		this.id = nextId++;
		this.totalMoves = 0;
		this.millisecondsTaken = 0;
	}
	
	/**
	 * Creates a new player
	 * @param m
	 * A reference to the maze it is in.
	 * @param pos
	 * Where it is.
	 */
	public Player(Maze m, Coord pos) {
		this(m, pos, UI.getRandomColor());
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
	 * Creates a default player image based on a color and size.
	 * @param playerColor
	 * The player's color.
	 * @param tileSize
	 * The width and height of the image.
	 * @return
	 * The made image.
	 */
	private BufferedImage generateImage(Color playerColor, int tileSize) {
		BufferedImage image = new BufferedImage(tileSize, tileSize, BufferedImage.TYPE_INT_ARGB);
		
		// For now we make a basic 8x8 square.
		// TODO: Make a better shape (a circle would stand out).
		for (int y = 0; y < tileSize; y++) {
			for (int x = 0; x < tileSize; x++) {
				if (y < 4 || y >= 12 || x < 4 || x >= 12) {
					// Outside the square is transparent.
					image.setRGB(x, y, new Color(0, 0, 0, 0).getRGB());
				} else {
					image.setRGB(x, y, playerColor.getRGB());
				}
			}
		}

		return image;
	}
}
