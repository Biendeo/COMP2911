import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * Provides a graphical UI for the maze.
 *
 */
public class UIFrame extends JFrame {
	
	// This exists purely for the JFrame.
	private static final long serialVersionUID = -5356384863762278628L;
	
	// The maze image.
	private BufferedImage mazeImg;

	// The mazeGame to be used.
	// TODO: Try to have it so the game is passed to the drawing, not existing
	// in it.
	private MazeGame game;
	
	// A parallel array of the images these player have.
	// TODO: An idea, maybe have the images in the player object (although this
	// would break having the UI and the maze game separate).
	private ArrayList<BufferedImage> playerImages;
	
	/**
	 * Creates a UIFrame with some basic properties. This also includes a paint
	 * function that gets called when repaint() is called.
	 */
	public UIFrame(MazeGame game) {
		playerImages = new ArrayList<BufferedImage>();
		
		this.game = game;
		
		Player[] players = game.getPlayers();
		
		// This needs to be much nicer.
		for (int i = 0; i < players.length; i++) {
			playerImages.add(createPlayerImage(players[i]));
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 320, 240);
		this.add(new JComponent(){
			private static final long serialVersionUID = -89098975220986245L;

			@Override
			/**
			 * Draws the screen.
			 */
			public void paintComponent(Graphics g) {
				if (mazeImg != null) {
					g.drawImage(mazeImg, 0, 0, null);
				}
				
				Player[] players = game.getPlayers();
				
				for (int i = 0; i < players.length; i++) {
					// TODO: Get the 16 out and replace it with a general constant tileSize.
					g.drawImage(playerImages.get(i), 16 * players[i].getPos().x, 16 * players[i].getPos().y, null);
				}
			}
		});
		
		this.addKeyListener(new UIKeyGetter(this));
	}
	
	/**
	 * Draws the current game's maze.
	 */
	public void drawMazeSwingOne() {
		// Modify these two values to your liking.
		// These may be moved out to be parameters.
		final int tileSize = 16;
		final int wallSize = 1;
		
		Maze m = game.getMaze();
		
		setBounds(100, 100, tileSize * m.getWidth(), tileSize * m.getHeight());
		BufferedImage image = new BufferedImage(tileSize * m.getWidth(), tileSize * m.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		// Modify these to your liking.
		Color wall = Color.BLACK;
		Color space = Color.WHITE;
		Color start = new Color(200, 200, 255);
		Color end = new Color(255, 200, 200);
		
		// TODO: This needs to be refactored, it's too copy-paste.
		// x and y are the map co-ordinates, a and b are the current tile pixel position.
		for (int y = 0; y < m.getHeight(); y++) {
			for (int x = 0; x < m.getWidth(); x++) {
				for (int b = 0; b < tileSize; b++) {
					for (int a = 0; a < tileSize; a++) {
						// If in the corners of a tile.
						if ((a < wallSize || a >= tileSize - wallSize) && (b < wallSize || b >= tileSize - wallSize)) {
							image.setRGB(x * tileSize + a, y * tileSize + b, wall.getRGB());
						} else if (a < wallSize) { // If on the left side.
							if (m.isConnected(x,  y, Direction.LEFT)) {
								image.setRGB(x * tileSize + a, y * tileSize + b, space.getRGB());
							} else {
								image.setRGB(x * tileSize + a, y * tileSize + b, wall.getRGB());
							}
						} else if (a >= tileSize - wallSize) { // If on the right side.
							if (m.isConnected(x, y, Direction.RIGHT)) {
								image.setRGB(x * tileSize + a, y * tileSize + b, space.getRGB());
							} else {
								image.setRGB(x * tileSize + a, y * tileSize + b, wall.getRGB());
							}
						} else if (b < wallSize) { // If on the top side.
							if (m.isConnected(x, y, Direction.UP)) {
								image.setRGB(x * tileSize + a, y * tileSize + b, space.getRGB());
							} else {
								image.setRGB(x * tileSize + a, y * tileSize + b, wall.getRGB());
							}
						} else if (b >= tileSize - wallSize) { // If on the bottom side.
							if (m.isConnected(x, y, Direction.DOWN)) {
								image.setRGB(x * tileSize + a, y * tileSize + b, space.getRGB());
							} else {
								image.setRGB(x * tileSize + a, y * tileSize + b, wall.getRGB());
							}
						} else { // In the centre.
							if (m.getStart().equals(new Coord(x, y))) {
								image.setRGB(x * tileSize + a, y * tileSize + b, start.getRGB());	
							} else if (m.getEnd().equals(new Coord(x, y))) {
								image.setRGB(x * tileSize + a, y * tileSize + b, end.getRGB());
							} else {
								image.setRGB(x * tileSize + a, y * tileSize + b, space.getRGB());
							}
						}
					}
				}
			}
		}
		mazeImg = image;
		// I need a bit of padding because it overlaps a little.
		this.setPreferredSize(new Dimension(mazeImg.getWidth() + 16, mazeImg.getHeight() + 39));
		this.pack();
		this.repaint();
	}
	
	/**
	 * Creates a player image for the game.
	 * @param p
	 * The player in question.
	 */
	private BufferedImage createPlayerImage(Player p) {
		// TODO: Have this be the same variable across the whole panel.
		final int tileSize = 16;
		
		Color playerColor = p.getColor();
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
	
	/**
	 * Adds a player to the frame to handle drawing it.
	 * @deprecated Replaced by MazeGame.addPlayer(Player)
	 * @param p
	 * The player in question.
	 * @return
	 * Whether this action was successful.
	 */
	public boolean addPlayer(Player p) {
		if (p == null) {
			return false;
		}
		
		game.addPlayer(p);
		playerImages.add(createPlayerImage(p));
		return true;
	}

	/**
	 * Makes a list of all the players.
	 * @return
	 * A list of all the players.
	 */
	// TODO: Decide which class manages players.
	public Player[] getPlayers() {
		return game.getPlayers();
	}
}
