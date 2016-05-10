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
import java.awt.CardLayout;
import java.awt.Font;

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
	
	private MazeProgram program;
	
	// The size of tiles used in the program (both width and height).
	private int tileSize;
	
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JLabel timeLabel;
	private JLabel moveLabel;
	private UIImageComponent mazeView;
	private JPanel buttonPanel;
	private JButton quitButton;
	private JPanel mainMenuPanel;
	private JPanel mainMenuContentPanel;
	private JButton btnExit;
	private JPanel mainPanel;
	private JPanel mazeViewPanel;
	private JButton btnSettings;
	private JButton btnTwoPlayers;
	private JButton btnNewMaze;
	private JButton btnInstantAction;
	private JPanel mainMenuButtonPanelCentral;
	private JPanel mainMenuButtonPanelDummy1;
	private JLabel mainMenuTitleLabel;
	private JPanel mainMenuButtonPanel;
	private CardLayout mainPanelCardLayout;
	
	/**
	 * Creates a UIFrame with some basic properties. This also includes a paint
	 * function that gets called when repaint() is called.
	 */
	public UIFrame(MazeProgram program) {
		this.tileSize = 16;
		this.program = program;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 450);
		setTimeText("00:00:000");
		setMoveText("0 moves");
		getContentPane().setLayout(new CardLayout(0, 0));
		
		mainPanel = new JPanel();
		getContentPane().add(mainPanel, "name_187122148572434");
		mainPanelCardLayout = new CardLayout(0, 0);
		mainPanel.setLayout(mainPanelCardLayout);
		
		mazeViewPanel = new JPanel();
		mainPanel.add(mazeViewPanel, "mazeViewPanel");
		SpringLayout sl_mazeViewPanel = new SpringLayout();
		mazeViewPanel.setLayout(sl_mazeViewPanel);
		
		leftPanel = new JPanel();
		sl_mazeViewPanel.putConstraint(SpringLayout.NORTH, leftPanel, 10, SpringLayout.NORTH, mazeViewPanel);
		sl_mazeViewPanel.putConstraint(SpringLayout.WEST, leftPanel, 10, SpringLayout.WEST, mazeViewPanel);
		sl_mazeViewPanel.putConstraint(SpringLayout.SOUTH, leftPanel, -10, SpringLayout.SOUTH, mazeViewPanel);
		mazeViewPanel.add(leftPanel);
		
		SpringLayout sl_leftPanel = new SpringLayout();
		leftPanel.setLayout(sl_leftPanel);
		
		mazeView = new UIImageComponent(mazeImg, game);
		sl_leftPanel.putConstraint(SpringLayout.NORTH, mazeView, 0, SpringLayout.NORTH, leftPanel);
		sl_leftPanel.putConstraint(SpringLayout.WEST, mazeView, 0, SpringLayout.WEST, leftPanel);
		sl_leftPanel.putConstraint(SpringLayout.SOUTH, mazeView, 0, SpringLayout.SOUTH, leftPanel);
		sl_leftPanel.putConstraint(SpringLayout.EAST, mazeView, 0, SpringLayout.EAST, leftPanel);
		leftPanel.add(mazeView);
		
		rightPanel = new JPanel();
		sl_mazeViewPanel.putConstraint(SpringLayout.EAST, leftPanel, -10, SpringLayout.WEST, rightPanel);
		sl_mazeViewPanel.putConstraint(SpringLayout.NORTH, rightPanel, 10, SpringLayout.NORTH, mazeViewPanel);
		sl_mazeViewPanel.putConstraint(SpringLayout.WEST, rightPanel, -210, SpringLayout.EAST, mazeViewPanel);
		sl_mazeViewPanel.putConstraint(SpringLayout.SOUTH, rightPanel, -10, SpringLayout.SOUTH, mazeViewPanel);
		sl_mazeViewPanel.putConstraint(SpringLayout.EAST, rightPanel, 10, SpringLayout.EAST, mazeViewPanel);
		mazeViewPanel.add(rightPanel);
		rightPanel.setLayout(new GridLayout(8, 1, 0, 0));
		
		timeLabel = new JLabel("Time Text");
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		rightPanel.add(timeLabel);
		
		moveLabel = new JLabel("Move Text");
		moveLabel.setHorizontalAlignment(SwingConstants.CENTER);
		rightPanel.add(moveLabel);
		
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
		
		mainMenuPanel = new JPanel();
		mainPanel.add(mainMenuPanel, "mainMenuPanel");
		SpringLayout sl_mainMenuPanel = new SpringLayout();
		mainMenuPanel.setLayout(sl_mainMenuPanel);
		
		mainMenuContentPanel = new JPanel();
		sl_mainMenuPanel.putConstraint(SpringLayout.NORTH, mainMenuContentPanel, 10, SpringLayout.NORTH, mainMenuPanel);
		sl_mainMenuPanel.putConstraint(SpringLayout.WEST, mainMenuContentPanel, 150, SpringLayout.WEST, mainMenuPanel);
		sl_mainMenuPanel.putConstraint(SpringLayout.SOUTH, mainMenuContentPanel, -10, SpringLayout.SOUTH, mainMenuPanel);
		sl_mainMenuPanel.putConstraint(SpringLayout.EAST, mainMenuContentPanel, -150, SpringLayout.EAST, mainMenuPanel);
		mainMenuPanel.add(mainMenuContentPanel);
		SpringLayout sl_mainMenuContentPanel = new SpringLayout();
		mainMenuContentPanel.setLayout(sl_mainMenuContentPanel);
		
		mainMenuButtonPanel = new JPanel();
		sl_mainMenuContentPanel.putConstraint(SpringLayout.NORTH, mainMenuButtonPanel, -199, SpringLayout.SOUTH, mainMenuContentPanel);
		sl_mainMenuContentPanel.putConstraint(SpringLayout.WEST, mainMenuButtonPanel, 10, SpringLayout.WEST, mainMenuContentPanel);
		sl_mainMenuContentPanel.putConstraint(SpringLayout.SOUTH, mainMenuButtonPanel, -10, SpringLayout.SOUTH, mainMenuContentPanel);
		sl_mainMenuContentPanel.putConstraint(SpringLayout.EAST, mainMenuButtonPanel, -10, SpringLayout.EAST, mainMenuContentPanel);
		mainMenuContentPanel.add(mainMenuButtonPanel);
		mainMenuButtonPanel.setLayout(new GridLayout(0, 3, 0, 0));
		
		mainMenuButtonPanelDummy1 = new JPanel();
		mainMenuButtonPanel.add(mainMenuButtonPanelDummy1);
		
		mainMenuButtonPanelCentral = new JPanel();
		mainMenuButtonPanel.add(mainMenuButtonPanelCentral);
		mainMenuButtonPanelCentral.setLayout(new GridLayout(5, 1, 0, 0));
		
		btnInstantAction = new JButton("Instant Action");
		mainMenuButtonPanelCentral.add(btnInstantAction);
		btnInstantAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				program.instantAction();
			}
		});
		
		btnNewMaze = new JButton("New Maze");
		mainMenuButtonPanelCentral.add(btnNewMaze);
		
		btnTwoPlayers = new JButton("Two Players");
		mainMenuButtonPanelCentral.add(btnTwoPlayers);
		
		btnSettings = new JButton("User Settings");
		mainMenuButtonPanelCentral.add(btnSettings);
		
		btnExit = new JButton("Exit");
		mainMenuButtonPanelCentral.add(btnExit);
		
		JPanel mainMenuButtonPanelDummy2 = new JPanel();
		mainMenuButtonPanel.add(mainMenuButtonPanelDummy2);
		
		mainMenuTitleLabel = new JLabel("Maze Game");
		mainMenuTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sl_mainMenuContentPanel.putConstraint(SpringLayout.NORTH, mainMenuTitleLabel, 75, SpringLayout.NORTH, mainMenuContentPanel);
		sl_mainMenuContentPanel.putConstraint(SpringLayout.WEST, mainMenuTitleLabel, 165, SpringLayout.WEST, mainMenuContentPanel);
		sl_mainMenuContentPanel.putConstraint(SpringLayout.SOUTH, mainMenuTitleLabel, -80, SpringLayout.NORTH, mainMenuButtonPanel);
		sl_mainMenuContentPanel.putConstraint(SpringLayout.EAST, mainMenuTitleLabel, -165, SpringLayout.EAST, mainMenuContentPanel);
		mainMenuTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		mainMenuContentPanel.add(mainMenuTitleLabel);
		sl_mazeViewPanel.putConstraint(SpringLayout.NORTH, mainMenuPanel, 0, SpringLayout.NORTH, mazeViewPanel);
		sl_mazeViewPanel.putConstraint(SpringLayout.WEST, mainMenuPanel, 0, SpringLayout.WEST, mazeViewPanel);
		sl_mazeViewPanel.putConstraint(SpringLayout.SOUTH, mainMenuPanel, 0, SpringLayout.SOUTH, mazeViewPanel);
		sl_mazeViewPanel.putConstraint(SpringLayout.EAST, mainMenuPanel, 0, SpringLayout.EAST, mazeViewPanel);

		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit?", JOptionPane.YES_NO_OPTION);
				if (dialogResult == JOptionPane.YES_OPTION) {
					program.exitMazeGame();
				}
			}
			
		});
		
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
			
		});
		
		mazeViewPanel.setVisible(false);
		
		mainPanelCardLayout.show(mainPanel, "mainMenuPanel");
		
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
		if (mazeImg != null) {
			this.setSize(mazeImg.getWidth() + 16 + 250, mazeImg.getHeight() + 39 + 20);
		}
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
	
	public void switchPanel(String panelName) {
		mainPanelCardLayout.show(mainPanel, panelName);
	}
	
	public void setGame(MazeGame g) {
		this.game = g;
		mazeView.setGame(g);;
	}
}
