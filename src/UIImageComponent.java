import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class UIImageComponent extends JComponent {
	private static final long serialVersionUID = 1L;
	private BufferedImage mazeImg;
	private MazeGame game;
	
	private int tileSize;
	
	/**
	 * Creates a new UIIageComponent with the rendered maze image and the game.
	 * @param mazeImg
	 * The maze image.
	 * @param g
	 * The game.
	 */
	public UIImageComponent(BufferedImage mazeImg, MazeGame g) {
		setMazeImg(mazeImg);
		setGame(g);
		tileSize = 16;
	}
	
	/**
	 * Sets the maze image.
	 * @param mazeImg
	 * The maze image.
	 */
	public void setMazeImg(BufferedImage mazeImg) {
		this.mazeImg = mazeImg;
	}
	
	/**
	 * Sets the game.
	 * @param g
	 * The game.
	 */
	public void setGame(MazeGame g) {
		this.game = g;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (mazeImg != null && game != null) {
			Player[] players = game.getPlayers();
			int panelWidth = getWidth();
			int panelHeight = getHeight();
			int imageWidth = mazeImg.getWidth();
			int imageHeight = mazeImg.getHeight();
			int decidedX = 0;
			int decidedY = 0;
			
			if (panelWidth > imageWidth) {
				decidedX = (panelWidth - imageWidth) / 2;
			} else if (players[0].getPos().x * tileSize < panelWidth / 2) {
				decidedX = 0;
			} else if (players[0].getPos().x * tileSize >= imageWidth - (panelWidth / 2)) {
				decidedX = panelWidth - imageWidth;
			} else {
				decidedX = panelWidth / 2 - players[0].getPos().x * tileSize;
			}
			
			if (panelHeight > imageHeight) {
				decidedY = (panelHeight - imageHeight) / 2;
			} else if (players[0].getPos().y * tileSize < panelHeight / 2) {
				decidedY = 0;
			} else if (players[0].getPos().y * tileSize >= imageHeight - (panelHeight / 2)) {
				decidedY = panelHeight - imageHeight;
			} else {
				decidedY = panelHeight / 2 - players[0].getPos().y * tileSize;
			}
			
			g.drawImage(mazeImg, decidedX, decidedY, null);

			for (int i = 0; i < players.length; i++) {
				g.drawImage(players[i].getImg(), tileSize * players[i].getPos().x + decidedX, tileSize * players[i].getPos().y + decidedY, null);
			}
		}
	}
	
}
