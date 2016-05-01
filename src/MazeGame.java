import java.util.ArrayList;

public class MazeGame {
	private Maze m;
	private ArrayList<Player> players;
	// Other game related stuff.
	
	/**
	 * Creates a maze game with some default conditions.
	 */
	public MazeGame() {
		m = new Maze(25, 20);
		m.generateMapDepthStyle();
		players = new ArrayList<Player>();
	}
	
	/**
	 * Adds a player that has already been created.
	 * The player will automatically be moved to the start.
	 * @param p
	 */
	public void addPlayer(Player p) {
		if (!players.contains(p)) {
			players.add(p);
			p.setMaze(m);
			p.setPos(m.getStart());
		}
	}
	
	/**
	 * Adds a new player from scratch. It automatically assigns it the maze
	 * and the start position.
	 */
	public void addPlayer() {
		addPlayer(new Player(m, m.getStart()));
	}
	
	/**
	 * Returns the maze object that the game is using.
	 * @return
	 * The maze object.
	 */
	public Maze getMaze() {
		return m;
	}
	
	/**
	 * Returns all players in the game.
	 * @return
	 * An array of all players in the game.
	 */
	public Player[] getPlayers() {
		return players.toArray(new Player[players.size()]);
	}
}
