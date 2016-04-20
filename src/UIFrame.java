import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Dimension;

/**
 * Provides a graphical UI for the maze.
 *
 */
public class UIFrame extends JFrame {
	
	// This exists purely for the JFrame.
	private static final long serialVersionUID = -5356384863762278628L;
	
	// The maze image.
	private BufferedImage mazeImg;
	
	/**
	 * Creates a UIFrame with some basic properties. This also includes a paint
	 * function that gets called when repaint() is called.
	 */
	public UIFrame() {
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
					// Currently all that is draw is the maze background.
					g.drawImage(mazeImg, 0, 0, null);
				}
			}
		});
	}
	
	/**
	 * Draws a given maze to the UIFrame, and repaints the frame at the end.
	 * @param m
	 * The maze to be drawn.
	 */
	public void drawMazeSwingOne(Maze m) {
		final int tileSize = 16;
		final int wallSize = 3;
		
		setBounds(100, 100, tileSize * m.getWidth(), tileSize * m.getHeight());
		BufferedImage image = new BufferedImage(tileSize * m.getWidth(), tileSize * m.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
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
							if (m.isConnectedLeft(x, y)) {
								if (m.getStart().equals(new Coord(x, y))) {
									image.setRGB(x * tileSize + a, y * tileSize + b, start.getRGB());	
								} else if (m.getEnd().equals(new Coord(x, y))) {
									image.setRGB(x * tileSize + a, y * tileSize + b, end.getRGB());
								} else {
									image.setRGB(x * tileSize + a, y * tileSize + b, space.getRGB());
								}
							} else {
								image.setRGB(x * tileSize + a, y * tileSize + b, wall.getRGB());
							}
						} else if (a >= tileSize - wallSize) { // If on the right side.
							if (m.isConnectedRight(x, y)) {
								if (m.getStart().equals(new Coord(x, y))) {
									image.setRGB(x * tileSize + a, y * tileSize + b, start.getRGB());	
								} else if (m.getEnd().equals(new Coord(x, y))) {
									image.setRGB(x * tileSize + a, y * tileSize + b, end.getRGB());
								} else {
									image.setRGB(x * tileSize + a, y * tileSize + b, space.getRGB());
								}
							} else {
								image.setRGB(x * tileSize + a, y * tileSize + b, wall.getRGB());
							}
						} else if (b < wallSize) { // If on the top side.
							if (m.isConnectedUp(x, y)) {
								if (m.getStart().equals(new Coord(x, y))) {
									image.setRGB(x * tileSize + a, y * tileSize + b, start.getRGB());	
								} else if (m.getEnd().equals(new Coord(x, y))) {
									image.setRGB(x * tileSize + a, y * tileSize + b, end.getRGB());
								} else {
									image.setRGB(x * tileSize + a, y * tileSize + b, space.getRGB());
								}
							} else {
								image.setRGB(x * tileSize + a, y * tileSize + b, wall.getRGB());
							}
						} else if (b >= tileSize - wallSize) { // If on the bottom side.
							if (m.isConnectedDown(x, y)) {
								if (m.getStart().equals(new Coord(x, y))) {
									image.setRGB(x * tileSize + a, y * tileSize + b, start.getRGB());	
								} else if (m.getEnd().equals(new Coord(x, y))) {
									image.setRGB(x * tileSize + a, y * tileSize + b, end.getRGB());
								} else {
									image.setRGB(x * tileSize + a, y * tileSize + b, space.getRGB());
								}
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

}
