import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class UIImageComponent extends JComponent {
	private static final long serialVersionUID = 1L;
	private BufferedImage mazeImg;
	private MazeGame game;
	
	private int tileSize;
	
	public UIImageComponent(BufferedImage mazeImg, MazeGame g) {
		setMazeImg(mazeImg);
		this.game = g;
		tileSize = 16;
	}
	
	public void setMazeImg(BufferedImage mazeImg) {
		this.mazeImg = mazeImg;
		
		if (mazeImg != null) {
			this.getParent().setSize(mazeImg.getWidth(), mazeImg.getHeight());
			this.setSize(mazeImg.getWidth(), mazeImg.getHeight());
		}
	}
	
	public void setGame(MazeGame g) {
		this.game = g;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (mazeImg != null) {
			g.drawImage(mazeImg, 0, 0, null);
		}
		
		Player[] players;
		
		if (game != null) {
			players = game.getPlayers();
			for (int i = 0; i < players.length; i++) {
				g.drawImage(players[i].getImg(), tileSize * players[i].getPos().x, tileSize * players[i].getPos().y, null);
			}
		}
	}
	
}
