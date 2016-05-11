import java.awt.CardLayout;
import java.awt.Color;
import java.util.Random;

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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

/**
 * Provides static functions to drawing mazes to the console.
 *
 */
public class UI extends JFrame {
	
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
	private JButton btnNewMaze;
	private JButton btnInstantAction;
	private JPanel mainMenuButtonPanelCentral;
	private JLabel mainMenuTitleLabel;
	private JPanel mainMenuButtonPanel;
	private CardLayout mainPanelCardLayout;
	private JLabel mazeViewPauseLabel;
	private JPanel customGameSetupPanel;
	private JTextField customSeedField;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel lblSize;
	private JPanel customGameSetupPlayerPanel;
	private JPanel customGameSetupDefaultPanel;
	private JPanel customGameSetupButtonPanel;
	private JLabel lblDifficulty;
	private JRadioButton rdbtnEasy;
	private JRadioButton rdbtnMedium;
	private JRadioButton rdbtnHard;
	private JRadioButton rdbtnCustom;
	private JRadioButton radioButton;
	private JRadioButton radioButton_1;
	
	/**
	 * Prints the map to the console using a specific character for walls and spaces.
	 * It draws walls as their own spots in the grid.
	 * @param m
	 * The maze to be drawn.
	 */
	public static void printMazeASCII(Maze m) {
		
		// You can easily modify the appearance here.
		final char wallChar = '#';
		final char spaceChar = '.';
		final String wall = Character.toString(wallChar);
		final String space = Character.toString(spaceChar);
		
		// Firstly, the top wall is printed.
		for (int i = 0; i < m.getWidth() * 2 + 1; i++) {
			System.out.print(wall);
		}
		
		System.out.println();
			
		// For each y row, we have two loops.
		for (int y = 0; y < m.getHeight(); y++) {
			// Left wall is printed.
			System.out.print(wall);
			// Then, each space is printed, followed by whether it has a
			// connection to the right. This will always draw the right wall.
			for (int x = 0; x < m.getWidth(); x++) {
				System.out.print(space);
				if (m.isConnected(x, y, Direction.RIGHT)) {
					System.out.print(space);
				} else {
					System.out.print(wall);
				}
			}
			System.out.println();
			
			// Another left wall is printed.
			System.out.print(wall);
			// Finally, each space's down connection is printed, followed by
			// a wall.
			for (int x = 0; x < m.getWidth(); x++) {
				if (m.isConnected(x, y, Direction.DOWN)) {
					System.out.print(space);
				} else {
					System.out.print(wall);
				}
				System.out.print(wall);
			}
			System.out.println();
		}
	}
	
	/**
	 * Creates a random RGB colour.
	 * @return
	 * The colour generated.
	 */
	public static Color getRandomColor() {
		Random rand = new Random();
		// TODO: This can give really bad colours sometimes (close to white for example). Make it create interesting colours (or just force set primary colours).
		return new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
	}
	
	/**
	 * Creates a UI frame with some basic properties.
	 */
	public UI(MazeProgram mazeProgram) {
		this.tileSize = 16;
		this.program = mazeProgram;
		
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
		
		mazeViewPauseLabel = new JLabel("Paused");
		mazeViewPauseLabel.setEnabled(false);
		mazeViewPauseLabel.setVisible(false);
		mazeViewPauseLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		sl_leftPanel.putConstraint(SpringLayout.NORTH, mazeViewPauseLabel, 0, SpringLayout.NORTH, leftPanel);
		sl_leftPanel.putConstraint(SpringLayout.WEST, mazeViewPauseLabel, 0, SpringLayout.WEST, leftPanel);
		sl_leftPanel.putConstraint(SpringLayout.SOUTH, mazeViewPauseLabel, 0, SpringLayout.SOUTH, leftPanel);
		sl_leftPanel.putConstraint(SpringLayout.EAST, mazeViewPauseLabel, 0, SpringLayout.EAST, leftPanel);
		mazeViewPauseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		leftPanel.add(mazeViewPauseLabel);
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
		
		JButton btnPause = new JButton("Pause");
		buttonPanel.add(btnPause);
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				program.togglePauseGame();
			}
		});
		
		quitButton = new JButton("Quit");
		quitButton.setToolTipText("Quits the maze. (RIGHT NOW IT QUITS THE PROGRAM)");
		buttonPanel.add(quitButton);
		
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Quit?", JOptionPane.YES_NO_OPTION);
				if (dialogResult == JOptionPane.YES_OPTION) {
					program.exitMazeGame();
				}
			}
		});
		
		mainMenuPanel = new JPanel();
		mainPanel.add(mainMenuPanel, "mainMenuPanel");
		SpringLayout sl_mainMenuPanel = new SpringLayout();
		mainMenuPanel.setLayout(sl_mainMenuPanel);
		
		mainMenuContentPanel = new JPanel();
		sl_mainMenuPanel.putConstraint(SpringLayout.NORTH, mainMenuContentPanel, 10, SpringLayout.NORTH, mainMenuPanel);
		sl_mainMenuPanel.putConstraint(SpringLayout.WEST, mainMenuContentPanel, 10, SpringLayout.WEST, mainMenuPanel);
		sl_mainMenuPanel.putConstraint(SpringLayout.SOUTH, mainMenuContentPanel, -10, SpringLayout.SOUTH, mainMenuPanel);
		sl_mainMenuPanel.putConstraint(SpringLayout.EAST, mainMenuContentPanel, -10, SpringLayout.EAST, mainMenuPanel);
		mainMenuPanel.add(mainMenuContentPanel);
		SpringLayout sl_mainMenuContentPanel = new SpringLayout();
		mainMenuContentPanel.setLayout(sl_mainMenuContentPanel);
		
		mainMenuButtonPanel = new JPanel();
		sl_mainMenuContentPanel.putConstraint(SpringLayout.NORTH, mainMenuButtonPanel, -150, SpringLayout.SOUTH, mainMenuContentPanel);
		sl_mainMenuContentPanel.putConstraint(SpringLayout.WEST, mainMenuButtonPanel, 0, SpringLayout.WEST, mainMenuContentPanel);
		sl_mainMenuContentPanel.putConstraint(SpringLayout.SOUTH, mainMenuButtonPanel, 0, SpringLayout.SOUTH, mainMenuContentPanel);
		sl_mainMenuContentPanel.putConstraint(SpringLayout.EAST, mainMenuButtonPanel, 0, SpringLayout.EAST, mainMenuContentPanel);
		mainMenuContentPanel.add(mainMenuButtonPanel);
		mainMenuButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		mainMenuButtonPanelCentral = new JPanel();
		mainMenuButtonPanel.add(mainMenuButtonPanelCentral);
		mainMenuButtonPanelCentral.setLayout(new GridLayout(4, 1, 0, 0));
		
		btnInstantAction = new JButton("Instant Action");
		mainMenuButtonPanelCentral.add(btnInstantAction);
		btnInstantAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				program.instantAction();
			}
		});
		
		btnNewMaze = new JButton("Custom Game");
		mainMenuButtonPanelCentral.add(btnNewMaze);
		btnNewMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanel("customGameSetupPanel");
			}
		});
		
		btnSettings = new JButton("User Settings");
		mainMenuButtonPanelCentral.add(btnSettings);
		
		btnExit = new JButton("Exit");
		mainMenuButtonPanelCentral.add(btnExit);
		
		mainMenuTitleLabel = new JLabel("Maze Game");
		sl_mainMenuContentPanel.putConstraint(SpringLayout.NORTH, mainMenuTitleLabel, 0, SpringLayout.NORTH, mainMenuContentPanel);
		sl_mainMenuContentPanel.putConstraint(SpringLayout.WEST, mainMenuTitleLabel, 0, SpringLayout.WEST, mainMenuContentPanel);
		sl_mainMenuContentPanel.putConstraint(SpringLayout.SOUTH, mainMenuTitleLabel, 0, SpringLayout.NORTH, mainMenuButtonPanel);
		sl_mainMenuContentPanel.putConstraint(SpringLayout.EAST, mainMenuTitleLabel, 0, SpringLayout.EAST, mainMenuContentPanel);
		mainMenuTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		mainMenuTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		//mainMenuTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		mainMenuContentPanel.add(mainMenuTitleLabel);
		sl_mazeViewPanel.putConstraint(SpringLayout.NORTH, mainMenuPanel, 0, SpringLayout.NORTH, mazeViewPanel);
		sl_mazeViewPanel.putConstraint(SpringLayout.WEST, mainMenuPanel, 0, SpringLayout.WEST, mazeViewPanel);
		sl_mazeViewPanel.putConstraint(SpringLayout.SOUTH, mainMenuPanel, 0, SpringLayout.SOUTH, mazeViewPanel);
		sl_mazeViewPanel.putConstraint(SpringLayout.EAST, mainMenuPanel, 0, SpringLayout.EAST, mazeViewPanel);
		
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
			
		});
		
		customGameSetupPanel = new JPanel();
		mainPanel.add(customGameSetupPanel, "customGameSetupPanel");
		SpringLayout sl_customGameSetupPanel = new SpringLayout();
		customGameSetupPanel.setLayout(sl_customGameSetupPanel);
		
		JPanel customGameSetupSeedPanel = new JPanel();
		sl_customGameSetupPanel.putConstraint(SpringLayout.WEST, customGameSetupSeedPanel, 10, SpringLayout.WEST, customGameSetupPanel);
		sl_customGameSetupPanel.putConstraint(SpringLayout.EAST, customGameSetupSeedPanel, -10, SpringLayout.EAST, customGameSetupPanel);
		customGameSetupPanel.add(customGameSetupSeedPanel);
		customGameSetupSeedPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblSeed = new JLabel("Seed:");
		customGameSetupSeedPanel.add(lblSeed);
		
		customSeedField = new JTextField();
		customGameSetupSeedPanel.add(customSeedField);
		customSeedField.setColumns(10);
		
		JPanel customGameSetupSizePanel = new JPanel();
		sl_customGameSetupPanel.putConstraint(SpringLayout.NORTH, customGameSetupSeedPanel, 10, SpringLayout.SOUTH, customGameSetupSizePanel);
		sl_customGameSetupPanel.putConstraint(SpringLayout.SOUTH, customGameSetupSeedPanel, 50, SpringLayout.SOUTH, customGameSetupSizePanel);
		sl_customGameSetupPanel.putConstraint(SpringLayout.WEST, customGameSetupSizePanel, 10, SpringLayout.WEST, customGameSetupPanel);
		sl_customGameSetupPanel.putConstraint(SpringLayout.EAST, customGameSetupSizePanel, -10, SpringLayout.EAST, customGameSetupPanel);
		customGameSetupPanel.add(customGameSetupSizePanel);
		customGameSetupSizePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblSize = new JLabel("Size:");
		customGameSetupSizePanel.add(lblSize);
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setText("10");
		customGameSetupSizePanel.add(textField);
		textField.setColumns(5);
		
		JLabel lblBy = new JLabel("by");
		customGameSetupSizePanel.add(lblBy);
		
		textField_1 = new JTextField();
		textField_1.setEnabled(false);
		textField_1.setText("10");
		customGameSetupSizePanel.add(textField_1);
		textField_1.setColumns(5);
		
		customGameSetupPlayerPanel = new JPanel();
		sl_customGameSetupPanel.putConstraint(SpringLayout.NORTH, customGameSetupPlayerPanel, 10, SpringLayout.SOUTH, customGameSetupSeedPanel);
		sl_customGameSetupPanel.putConstraint(SpringLayout.WEST, customGameSetupPlayerPanel, 10, SpringLayout.WEST, customGameSetupPanel);
		sl_customGameSetupPanel.putConstraint(SpringLayout.SOUTH, customGameSetupPlayerPanel, 50, SpringLayout.SOUTH, customGameSetupSeedPanel);
		sl_customGameSetupPanel.putConstraint(SpringLayout.EAST, customGameSetupPlayerPanel, -10, SpringLayout.EAST, customGameSetupPanel);
		customGameSetupPanel.add(customGameSetupPlayerPanel);
		
		JLabel lblPlayers = new JLabel("Players:");
		customGameSetupPlayerPanel.add(lblPlayers);
		
		ButtonGroup customGamePlayerButtonGroup = new ButtonGroup();
		
		radioButton = new JRadioButton("1");
		radioButton.setSelected(true);
		customGameSetupPlayerPanel.add(radioButton);
		customGamePlayerButtonGroup.add(radioButton);
		
		radioButton_1 = new JRadioButton("2");
		customGameSetupPlayerPanel.add(radioButton_1);
		customGamePlayerButtonGroup.add(radioButton_1);
		
		customGameSetupDefaultPanel = new JPanel();
		sl_customGameSetupPanel.putConstraint(SpringLayout.NORTH, customGameSetupSizePanel, 10, SpringLayout.SOUTH, customGameSetupDefaultPanel);
		sl_customGameSetupPanel.putConstraint(SpringLayout.SOUTH, customGameSetupSizePanel, 50, SpringLayout.SOUTH, customGameSetupDefaultPanel);
		sl_customGameSetupPanel.putConstraint(SpringLayout.NORTH, customGameSetupDefaultPanel, 10, SpringLayout.NORTH, customGameSetupPanel);
		sl_customGameSetupPanel.putConstraint(SpringLayout.WEST, customGameSetupDefaultPanel, 10, SpringLayout.WEST, customGameSetupPanel);
		sl_customGameSetupPanel.putConstraint(SpringLayout.SOUTH, customGameSetupDefaultPanel, 50, SpringLayout.NORTH, customGameSetupPanel);
		sl_customGameSetupPanel.putConstraint(SpringLayout.EAST, customGameSetupDefaultPanel, -10, SpringLayout.EAST, customGameSetupPanel);
		customGameSetupPanel.add(customGameSetupDefaultPanel);
		
		lblDifficulty = new JLabel("Difficulty:");
		customGameSetupDefaultPanel.add(lblDifficulty);
		
		ButtonGroup customGameDifficultyGroup = new ButtonGroup();
		
		rdbtnEasy = new JRadioButton("Easy");
		rdbtnEasy.setSelected(true);
		customGameSetupDefaultPanel.add(rdbtnEasy);
		customGameDifficultyGroup.add(rdbtnEasy);
		rdbtnEasy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("10");
				textField.setEnabled(false);
				textField_1.setText("10");
				textField_1.setEnabled(false);
			}
		});
		
		rdbtnMedium = new JRadioButton("Medium");
		customGameSetupDefaultPanel.add(rdbtnMedium);
		customGameDifficultyGroup.add(rdbtnMedium);
		rdbtnMedium.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("25");
				textField.setEnabled(false);
				textField_1.setText("20");
				textField_1.setEnabled(false);
			}
		});
		
		rdbtnHard = new JRadioButton("Hard");
		customGameSetupDefaultPanel.add(rdbtnHard);
		customGameDifficultyGroup.add(rdbtnHard);
		rdbtnHard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("40");
				textField.setEnabled(false);
				textField_1.setText("30");
				textField_1.setEnabled(false);
			}
		});
		
		rdbtnCustom = new JRadioButton("Custom");
		customGameSetupDefaultPanel.add(rdbtnCustom);
		customGameDifficultyGroup.add(rdbtnCustom);
		rdbtnCustom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setEnabled(true);
				textField_1.setEnabled(true);
			}
		});
		
		customGameSetupButtonPanel = new JPanel();
		sl_customGameSetupPanel.putConstraint(SpringLayout.NORTH, customGameSetupButtonPanel, -50, SpringLayout.SOUTH, customGameSetupPanel);
		sl_customGameSetupPanel.putConstraint(SpringLayout.WEST, customGameSetupButtonPanel, 10, SpringLayout.WEST, customGameSetupPanel);
		sl_customGameSetupPanel.putConstraint(SpringLayout.SOUTH, customGameSetupButtonPanel, -10, SpringLayout.SOUTH, customGameSetupPanel);
		sl_customGameSetupPanel.putConstraint(SpringLayout.EAST, customGameSetupButtonPanel, -10, SpringLayout.EAST, customGameSetupPanel);
		customGameSetupPanel.add(customGameSetupButtonPanel);
		
		JButton btnBack = new JButton("Back");
		customGameSetupButtonPanel.add(btnBack);
		sl_customGameSetupPanel.putConstraint(SpringLayout.SOUTH, btnBack, -79, SpringLayout.SOUTH, customGameSetupPanel);
		sl_customGameSetupPanel.putConstraint(SpringLayout.WEST, btnBack, 0, SpringLayout.WEST, customGameSetupSeedPanel);
		
		JButton btnPlay = new JButton("Play");
		customGameSetupButtonPanel.add(btnPlay);
		sl_customGameSetupPanel.putConstraint(SpringLayout.EAST, btnPlay, 0, SpringLayout.EAST, customGameSetupSeedPanel);
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int width = Integer.parseInt(textField.getText());
				int height = Integer.parseInt(textField_1.getText());
				long seed = 0;
				if (customSeedField.getText().equals("")) {
					seed = 0;
				} else {
					seed = Long.parseLong(customSeedField.getText());
				}
				int players = 0;
				if (radioButton.isSelected()) {
					players = 1;
				} else if (radioButton_1.isSelected()) {
					players = 2;
				}
				program.customGameSetup(width, height, seed, players);
			}
		});
		
		sl_customGameSetupPanel.putConstraint(SpringLayout.NORTH, btnPlay, 0, SpringLayout.NORTH, btnBack);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanel("mainMenuPanel");
			}
		});
		
		
		switchPanel("mainMenuPanel");
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
	
	public void showPause(boolean willBePaused) {
		if (willBePaused) {
			mazeView.setEnabled(false);
			mazeView.setVisible(false);
			mazeViewPauseLabel.setEnabled(true);
			mazeViewPauseLabel.setVisible(true);
		} else {
			mazeView.setEnabled(true);
			mazeView.setVisible(true);
			mazeViewPauseLabel.setEnabled(false);
			mazeViewPauseLabel.setVisible(false);
		}
	}
}
