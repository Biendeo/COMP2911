import java.awt.Color;

/**
 * The player class, which has details about the player objects themselves.
 *
 */
public class Player {
	private Maze m;
	private Coord pos;
	// TODO: Color should be replaced by image. If no set image is given, the
	// UI will create an image.
	private Color color;
	private int id;
	
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
		this.color = color;
		this.id = nextId++;
	}
	
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
		this.color = UI.getRandomColor();
		this.id = nextId++;
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
	 * Get the color of the player (read-only).
	 * @return
	 * The color of the player.
	 */
	public Color getColor() {
		return new Color(color.getRGB());
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
			return true;
		} else {
			return false;
		}
	}
}
