
/**
 * An enum used to note the strategy used by the maze generator.
 *
 */
public enum MazeGenerationStrategy {
	NONE,
	DEPTHFIRSTSEARCH,
	RING;
	
	public String toString() {
		switch (this) {
		case NONE:
		default:
			return "None";
		case DEPTHFIRSTSEARCH:
			return "DFS";
		case RING:
			return "Ring";
		}
	}
	
	/**
	 * Returns a relevant MazeGenerationStrategy to a given string.
	 * @param str
	 * The given string.
	 * @return
	 * The MazeGenerationStrategy associated with that name.
	 */
	public static MazeGenerationStrategy fromString(String str) {
		switch (str) {
		case "None":
		default:
			return NONE;
		case "DFS":
			return DEPTHFIRSTSEARCH;
		case "Ring":
			return RING;
		}
	}
}
