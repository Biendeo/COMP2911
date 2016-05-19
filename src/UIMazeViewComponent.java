import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

/**
 * A class used to store properties and display the maze.
 *
 */
public class UIMazeViewComponent extends JComponent {
	private static final long serialVersionUID = 1L;
	private BufferedImage mazeImg;
	private BufferedImage coinImg;
	private BufferedImage coinPartialImg;
	private MazeGame game;
	
	private int tileSize;
	
	/**
	 * Creates a new UIMazeViewComponent.
	 * @param mazeImg
	 * The maze image (can be null and set later).
	 * @param coinImg
	 * The image used for a coin.
	 * @param coinPartialImg
	 * The image used for a partial coin.
	 * @param g
	 * The game.
	 */
	public UIMazeViewComponent(BufferedImage mazeImg, BufferedImage coinImg, BufferedImage coinPartialImg, MazeGame g) {
		setMazeImg(mazeImg);
		this.coinImg = coinImg;
		this.coinPartialImg = coinPartialImg;
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
			Coin[] coins = game.getCoins();
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
			
			for (Coin c : coins) {
				if (!c.getCollectedPlayer1() && !c.getCollectedPlayer2()) {
					g.drawImage(coinImg, tileSize * c.getPos().x + decidedX, tileSize * c.getPos().y + decidedY, null);
				} else if (players.length == 2 && (c.getCollectedPlayer1() ^ c.getCollectedPlayer2())) {
					g.drawImage(coinPartialImg, tileSize * c.getPos().x + decidedX, tileSize * c.getPos().y + decidedY, null);
				}
			}

			for (Player p : players) {
				g.drawImage(p.getImg(), tileSize * p.getPos().x + decidedX + 1, tileSize * p.getPos().y + decidedY + 1, null);
			}
		}
	}
	
}
