
/**
 * A coin object that is placed in the maze.
 *
 */
public class Coin {
	private Coord pos;
	private boolean collectedPlayer1;
	private boolean collectedPlayer2;
	
	/**
	 * Creates a new coin given a position. It assumes it hasn't been collected
	 * by either player yet.
	 * @param pos
	 * The position it is at.
	 */
	public Coin(Coord pos) {
		this.pos = pos;
		collectedPlayer1 = false;
		collectedPlayer2 = false;
	}
	
	/**
	 * Returns the position of the coin.
	 * @return
	 * The position of the coin.
	 */
	public Coord getPos() {
		return pos.clone();
	}
	
	/**
	 * Returns whether the coin has been collected by player 1.
	 * @return
	 * Whether the coin has been collected by player 1.
	 */
	public boolean getCollectedPlayer1() {
		return collectedPlayer1;
	}
	
	/**
	 * Sets whether player 1 has collected the coin.
	 * @param collected
	 * Whether player 1 has collected the coin.
	 */
	public void setCollectedPlayer1(boolean collected) {
		collectedPlayer1 = collected;
	}

	/**
	 * Returns whether the coin has been collected by player 2.
	 * @return
	 * Whether the coin has been collected by player 2.
	 */
	public boolean getCollectedPlayer2() {
		return collectedPlayer2;
	}

	/**
	 * Sets whether player 2 has collected the coin.
	 * @param collected
	 * Whether player 2 has collected the coin.
	 */
	public void setCollectedPlayer2(boolean collected) {
		collectedPlayer2 = collected;
	}
}
