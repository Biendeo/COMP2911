
/**
 * A basic class that stores an x and y position.
 * It should be used more as a copyable struct rather than a class.
 *
 */
public class Coord {
	public int x;
	public int y;
	
	/**
	 * Creates a coord with specified properties.
	 * @param x
	 * The x coord.
	 * @param y
	 * The y coord.
	 */
	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Creates a new coord with the same x and y.
	 */
	public Coord clone() {
		return new Coord(x, y);
	}
	
	/**
	 * Checked whether two coords are equal.
	 * @param c
	 * The other coord to check.
	 * @return
	 * Whether they're equal.
	 */
	@Override
	public boolean equals(Object m) {
		Coord c = (Coord)m;
		if (x == c.x && y == c.y) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Changes the coord depending on a given direction.
	 * It does not check map boundaries, so negatives are possible.
	 * @param d
	 * The direction to move.
	 */
	public void move(Direction d) {
		switch(d) {
		case UP:
		default:
			y -= 1;
			break;
		case RIGHT:
			x += 1;
			break;
		case DOWN:
			y += 1;
			break;
		case LEFT:
			x -= 1;
			break;
		}
	}
}
