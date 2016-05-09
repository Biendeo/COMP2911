import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.FlowLayout;

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
	
	// The size of tiles used in the program (both width and height).
	private int tileSize;
	
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JLabel timeLabel;
	private JLabel moveLabel;
	private UIImageComponent mazeView;
	private JPanel buttonPanel;
	private JButton quitButton;
	
	/**
	 * Creates a UIFrame with some basic properties. This also includes a paint
	 * function that gets called when repaint() is called.
	 */
	public UIFrame(MazeGame gameArg) {
		this.tileSize = 16;
		this.game = gameArg;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 450);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		leftPanel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, leftPanel, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, leftPanel, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, leftPanel, -10, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, leftPanel, -220, SpringLayout.EAST, getContentPane());
		getContentPane().add(leftPanel);
		
		rightPanel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, rightPanel, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, rightPanel, -210, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, rightPanel, -10, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, rightPanel, -10, SpringLayout.EAST, getContentPane());
		getContentPane().add(rightPanel);
		rightPanel.setLayout(new GridLayout(8, 1, 0, 0));
		
		timeLabel = new JLabel("Time Text");
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		rightPanel.add(timeLabel);
		setTimeText("00:00:000");
		
		moveLabel = new JLabel("Move Text");
		moveLabel.setHorizontalAlignment(SwingConstants.CENTER);
		rightPanel.add(moveLabel);
		setMoveText("0 moves");
		
		// Dummy panels.
		rightPanel.add(new JPanel());
		rightPanel.add(new JPanel());
		rightPanel.add(new JPanel());
		rightPanel.add(new JPanel());
		rightPanel.add(new JPanel());
		
		buttonPanel = new JPanel();
		rightPanel.add(buttonPanel);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		quitButton = new JButton("Quit");
		quitButton.setToolTipText("Quits the maze. (RIGHT NOW IT QUITS THE PROGRAM)");
		buttonPanel.add(quitButton);
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit?", JOptionPane.YES_NO_OPTION);
				if (dialogResult == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		
		SpringLayout sl_leftPanel = new SpringLayout();
		leftPanel.setLayout(sl_leftPanel);
		
		mazeView = new UIImageComponent(mazeImg, game);
		sl_leftPanel.putConstraint(SpringLayout.NORTH, mazeView, 0, SpringLayout.NORTH, leftPanel);
		sl_leftPanel.putConstraint(SpringLayout.WEST, mazeView, 0, SpringLayout.WEST, leftPanel);
		sl_leftPanel.putConstraint(SpringLayout.SOUTH, mazeView, 0, SpringLayout.SOUTH, leftPanel);
		sl_leftPanel.putConstraint(SpringLayout.EAST, mazeView, 0, SpringLayout.EAST, leftPanel);
		leftPanel.add(mazeView);

		//leftPanel.setBackground(new Color(50, 200, 50));
		//rightPanel.setBackground(new Color(200, 200, 50));
	}
	
	/**
	 * Draws the current game's maze.
	 */
	public void drawMazeSwingOne() {
		// Modify this value to your liking.
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
		this.setSize(mazeImg.getWidth() + 16 + 250, mazeImg.getHeight() + 39 + 20);
		mazeView.setMazeImg(mazeImg);
		// I need a bit of padding because it overlaps a little.
		//panel.setPreferredSize(new Dimension(mazeImg.getWidth() + 16, mazeImg.getHeight() + 39));
		//this.repaint();
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
	
	@Override
	public void pack() {
		//super.pack();
		this.setSize(mazeImg.getWidth() + 16 + 250, mazeImg.getHeight() + 39 + 20);
	}

	/**
	 * Sets the label text for time.
	 * @param timeText
	 * The string to show.
	 */
	public void setTimeText(String timeText) {
		if (this.timeLabel != null) {
			this.timeLabel.setText(timeText);
		}
	}
	
	/**
	 * Sets the label text for total moves.
	 * @param moveText
	 * The string to show.
	 */
	public void setMoveText(String moveText) {
		if (this.moveLabel != null) {
			this.moveLabel.setText(moveText);
		}
	}
}
