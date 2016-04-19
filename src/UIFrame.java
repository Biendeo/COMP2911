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

public class UIFrame extends JFrame {

	private static final long serialVersionUID = -5356384863762278628L;
	// TODO: Clean up the contentPane business.
	private JPanel contentPane;
	
	// My actual UI stuff.
	BufferedImage mazeImg;

	public UIFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 320, 240);
		//contentPane = new JPanel();
		this.add(new JComponent(){
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -89098975220986245L;

			@Override
			public void paintComponent(Graphics g) {
				if (mazeImg != null) {
					g.drawImage(mazeImg, 0, 0, null);
				}
			}
		});
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//contentPane.setLayout(new BorderLayout(0, 0));
		//setContentPane(contentPane);
	}

	public void drawMazeSwingOne(Maze m) {
		final int tileSize = 16;
		final int wallSize = 3;
		
		setBounds(100, 100, tileSize * m.getWidth(), tileSize * m.getHeight());
		BufferedImage image = new BufferedImage(tileSize * m.getWidth(), tileSize * m.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		Color wall = Color.BLACK;
		Color space = Color.WHITE;
		
		for (int y = 0; y < m.getHeight(); y++) {
			for (int x = 0; x < m.getWidth(); x++) {
				for (int b = 0; b < tileSize; b++) {
					for (int a = 0; a < tileSize; a++) {
						if ((a < wallSize || a >= tileSize - wallSize) && (b < wallSize || b >= tileSize - wallSize)) {
							image.setRGB(x * tileSize + a, y * tileSize + b, wall.getRGB());
						} else if (a < wallSize) {
							if (m.isConnectedLeft(x, y)) {
								image.setRGB(x * tileSize + a, y * tileSize + b, space.getRGB());
							} else {
								image.setRGB(x * tileSize + a, y * tileSize + b, wall.getRGB());
							}
						} else if (a >= tileSize - wallSize) {
							if (m.isConnectedRight(x, y)) {
								image.setRGB(x * tileSize + a, y * tileSize + b, space.getRGB());
							} else {
								image.setRGB(x * tileSize + a, y * tileSize + b, wall.getRGB());
							}
						} else if (b < wallSize) {
							if (m.isConnectedUp(x, y)) {
								image.setRGB(x * tileSize + a, y * tileSize + b, space.getRGB());
							} else {
								image.setRGB(x * tileSize + a, y * tileSize + b, wall.getRGB());
							}
						} else if (b >= tileSize - wallSize) {
							if (m.isConnectedDown(x, y)) {
								image.setRGB(x * tileSize + a, y * tileSize + b, space.getRGB());
							} else {
								image.setRGB(x * tileSize + a, y * tileSize + b, wall.getRGB());
							}
						} else {
							image.setRGB(x * tileSize + a, y * tileSize + b, space.getRGB());
						}
					}
				}
			}
		}
		mazeImg = image;
		System.out.println(mazeImg.getWidth());
		System.out.println(mazeImg.getHeight());
		// I need a bit of padding because it overlaps a little.
		this.setPreferredSize(new Dimension(mazeImg.getWidth() + 16, mazeImg.getHeight() + 39));
		this.pack();
		this.repaint();
	}

}
