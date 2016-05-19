import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

/**
 * A config object that stores all data that will be saved between sessions.
 * Any operation that modifies its values will save the config to a file on
 * the spot.
 *
 */
public class Config implements Serializable {
	private static final long serialVersionUID = -9101085459063432816L;

	private static String configLocation = "dat/";
	private static String configFileName = "settings.dat";

	private int player1Up;
	private int player1Right;
	private int player1Down;
	private int player1Left;
	private int player2Up;
	private int player2Right;
	private int player2Down;
	private int player2Left;
	
	private ImageIcon player1Image;
	private ImageIcon player2Image;
	
	private ArrayList<LeaderboardEntry> leaderboard;
	
	/**
	 * Creates a new config object with default controls.
	 */
	public Config() {
		player1Up = KeyEvent.VK_UP;
		player1Right = KeyEvent.VK_RIGHT;
		player1Down = KeyEvent.VK_DOWN;
		player1Left = KeyEvent.VK_LEFT;
		player2Up = KeyEvent.VK_W;
		player2Right = KeyEvent.VK_D;
		player2Down = KeyEvent.VK_S;
		player2Left = KeyEvent.VK_A;
		player1Image = new ImageIcon(generateImage(16));
		player2Image = new ImageIcon(generateImage(16));
		leaderboard = new ArrayList<LeaderboardEntry>();
	}
	
	/**
	 * Returns the image used by player 1.
	 * @return
	 * The image used by player 1.
	 */
	public BufferedImage getPlayer1Image() {
		BufferedImage returnImage = new BufferedImage(14, 14, BufferedImage.TYPE_INT_ARGB);
		
		Graphics g = returnImage.createGraphics();
		g.drawImage(player1Image.getImage(), 0, 0, null);
		g.dispose();
		
		return returnImage;
	}
	
	/**
	 * Sets the image used by player 1.
	 * @param image
	 * The image to be used by player 1.
	 */
	public void setPlayer1Image(BufferedImage image) {
		if (image.getWidth() != 14 && image.getHeight() != 14) {
			player1Image = new ImageIcon(image.getScaledInstance(14, 14, Image.SCALE_FAST));
		} else {
			player1Image = new ImageIcon(image);
		}
		saveConfig();
	}
	
	/**
	 * Returns the image used by player 2.
	 * @return
	 * The image used by player 2.
	 */
	public BufferedImage getPlayer2Image() {
		BufferedImage returnImage = new BufferedImage(14, 14, BufferedImage.TYPE_INT_ARGB);
		
		Graphics g = returnImage.createGraphics();
		g.drawImage(player2Image.getImage(), 0, 0, null);
		g.dispose();
		
		return returnImage;
	}

	/**
	 * Sets the image used by player 2.
	 * @param image
	 * The image to be used by player 2.
	 */
	public void setPlayer2Image(BufferedImage image) {
		if (image.getWidth() != 14 && image.getHeight() != 14) {
			player2Image = new ImageIcon(image.getScaledInstance(14, 14, Image.SCALE_FAST));
		} else {
			player2Image = new ImageIcon(image);
		}
		saveConfig();
	}
	
	/**
	 * Gets the key given by a ControlCode.
	 * @param keyBinding
	 * @return
	 */
	public int getKeyBinding(ControlCode keyBinding) {
		switch (keyBinding) {
		case PLAYER1UP:
			return player1Up;
		case PLAYER1RIGHT:
			return player1Right;
		case PLAYER1DOWN:
			return player1Down;
		case PLAYER1LEFT:
			return player1Left;
		case PLAYER2UP:
			return player2Up;
		case PLAYER2RIGHT:
			return player2Right;
		case PLAYER2DOWN:
			return player2Down;
		case PLAYER2LEFT:
			return player2Left;
		default:
			return 0;
		}
	}
	
	public void setKeyBinding(ControlCode keyBinding, int keyCode) {
		switch (keyBinding) {
		case PLAYER1UP:
			player1Up = keyCode;
			break;
		case PLAYER1RIGHT:
			player1Right = keyCode;
			break;
		case PLAYER1DOWN:
			player1Down = keyCode;
			break;
		case PLAYER1LEFT:
			player1Left = keyCode;
			break;
		case PLAYER2UP:
			player2Up = keyCode;
			break;
		case PLAYER2RIGHT:
			player2Right = keyCode;
			break;
		case PLAYER2DOWN:
			player2Down = keyCode;
			break;
		case PLAYER2LEFT:
			player2Left = keyCode;
			break;
		default:
			break;
		}
		saveConfig();
	}

	/**
	 * Checks whether the config exists.
	 * @return
	 * Whether the config exists.
	 */
	public boolean doesConfigExist() {
		File configFile = new File(configLocation + configFileName);
		if (configFile.exists() && configFile.isFile()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Loads a config from the config location.
	 */
	public void loadConfig() {
		if (doesConfigExist()) {
			try {
				FileInputStream configIn = new FileInputStream(configLocation + configFileName);
				ObjectInputStream in = new ObjectInputStream(configIn);
				copyConfig((Config)in.readObject());
				in.close();
				configIn.close();
			} catch (IOException | ClassNotFoundException i) {
				System.err.println("loadConfig() couldn't read the file.");
			}
		}
	}
	
	/**
	 * Saves the config to the config location. It'll create a folder if
	 * necessary.
	 */
	public void saveConfig() {
		try {
			File location = new File(configLocation);
			if (!location.exists()) {
				location.mkdirs();
			}
			
			FileOutputStream configOut = new FileOutputStream(configLocation + configFileName);
			ObjectOutputStream out = new ObjectOutputStream(configOut);
			out.writeObject(this);
			out.close();
			configOut.close();
		} catch (IOException i) {
			System.err.println("saveConfig() couldn't write to the file.");
		}
	}
	
	/**
	 * Copies a config object to this object. This is used when loading a config.
	 * @param c
	 * The config in question.
	 */
	private void copyConfig(Config c) {
		player1Up = c.player1Up;
		player1Right = c.player1Right;
		player1Down = c.player1Down;
		player1Left = c.player1Left;
		player2Up = c.player2Up;
		player2Right = c.player2Right;
		player2Down = c.player2Down;
		player2Left = c.player2Left;
		player1Image = c.player1Image;
		player2Image = c.player2Image;
		leaderboard = c.leaderboard;
	}
	
	
	/**
	 * Creates a default player image based on a color and size.
	 * @param playerColor
	 * The player's color.
	 * @param tileSize
	 * The width and height of the image.
	 * @return
	 * The made image.
	 */
	private BufferedImage generateImage(int tileSize) {
		BufferedImage image = new BufferedImage(tileSize - 2, tileSize - 2, BufferedImage.TYPE_INT_ARGB);
		
		Random rand = new Random();
		
		Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
		
		// For now we make a basic 8x8 square.
		// TODO: Make a better shape (a circle would stand out).
		for (int y = 0; y < tileSize - 2; y++) {
			for (int x = 0; x < tileSize - 2; x++) {
				if (y < 3 || y >= 11 || x < 3 || x >= 11) {
					// Outside the square is transparent.
					image.setRGB(x, y, new Color(0, 0, 0, 0).getRGB());
				} else {
					image.setRGB(x, y, color.getRGB());
				}
			}
		}

		return image;
	}
	
	/**
	 * Sets a randomised image for player 1.
	 */
	public void setRandomPlayer1Image() {
		player1Image = new ImageIcon(generateImage(16));
		saveConfig();
	}
	
	/**
	 * Sets a randomised image for player 2.
	 */
	public void setRandomPlayer2Image() {
		player2Image = new ImageIcon(generateImage(16));
		saveConfig();
	}
	
	/**
	 * Adds a new entry to the leaderboard.
	 * @param mazeWidth
	 * The width of the maze.
	 * @param mazeHeight
	 * The height of the maze.
	 * @param mazeSeed
	 * The seed of the maze.
	 * @param mazeStrategy
	 * The strategy used by the maze.
	 * @param timeMillis
	 * The time it took the player to clear it (in milliseconds).
	 * @param movesTaken
	 * The moves taken by the player.
	 * @param coinsCollected
	 * The number of coins the player collected.
	 */
	public void addLeaderboardEntry(int mazeWidth, int mazeHeight, long mazeSeed, MazeGenerationStrategy mazeStrategy, int timeMillis, int movesTaken, int coinsCollected) {
		leaderboard.add(new LeaderboardEntry(mazeWidth, mazeHeight, mazeSeed, mazeStrategy, timeMillis, movesTaken, coinsCollected));
		saveConfig();
	}
	
	/**
	 * Returns the leaderboard as an array of LeaderboardEntries.
	 * @return
	 * The leaderboard as an array of LeaderboardEntries.
	 */
	public LeaderboardEntry[] getLeaderboard() {
		return leaderboard.toArray(new LeaderboardEntry[leaderboard.size()]);
	}
	
	/**
	 * Returns the leaderboard as a 2D array of strings.
	 * @return
	 * The leaderboard as a 2D array of strings.
	 */
	public String[][] getLeaderboardTable() {
		String[][] leaderboardTable = new String[leaderboard.size()][];
		for (int i = 0; i < leaderboard.size(); i++) {
			LeaderboardEntry entry = leaderboard.get(i);
			leaderboardTable[i] = new String[7];
			leaderboardTable[i][0] = entry.getMazeStrategy().toString();
			leaderboardTable[i][1] = Integer.toString(entry.getMazeWidth());
			leaderboardTable[i][2] = Integer.toString(entry.getMazeHeight());
			leaderboardTable[i][3] = String.format("%02d:%02d:%03d", entry.getTimeMillis() / 60000, entry.getTimeMillis() / 1000 % 60, entry.getTimeMillis() % 1000);
			leaderboardTable[i][4] = Integer.toString(entry.getMovesTaken());
			leaderboardTable[i][5] = Integer.toString(entry.getCoinsCollected());
			leaderboardTable[i][6] = Long.toString(entry.getMazeSeed());
		}
		
		return leaderboardTable;
	}
	
	/**
	 * Deletes a leaderboard entry given its position in the table.
	 * @param id
	 * The position of the entry.
	 */
	public void deleteLeaderboardEntry(int id) {
		leaderboard.remove(id);
		saveConfig();
	}
}
