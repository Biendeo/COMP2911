
/**
 * An enum used to indicate a keyBinding.
 *
 */
public enum ControlCode {
	NONE,
	PLAYER1UP,
	PLAYER1RIGHT,
	PLAYER1DOWN,
	PLAYER1LEFT,
	PLAYER2UP,
	PLAYER2RIGHT,
	PLAYER2DOWN,
	PLAYER2LEFT;
	
	public String toString() {
		switch (this) {
		case PLAYER1UP:
			return "Player 1 Up";
		case PLAYER1RIGHT:
			return "Player 1 Right";
		case PLAYER1DOWN:
			return "Player 1 Down";
		case PLAYER1LEFT:
			return "Player 1 Left";
		case PLAYER2UP:
			return "Player 2 Up";
		case PLAYER2RIGHT:
			return "Player 2 Right";
		case PLAYER2DOWN:
			return "Player 2 Down";
		case PLAYER2LEFT:
			return "Player 2 Left";
		default:
			return "UNKNOWN KEYBINDING";
		}
	}
}