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
