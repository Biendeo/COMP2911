import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * A config object that stores all data that will be saved between sessions.
 *
 */
public class Config implements Serializable {
	private static final long serialVersionUID = -9101085459063432816L;

	private static String configLocation = "cfg/";
	private static String configFileName = "settings.cfg";

	private char player1Up;
	private char player1Right;
	private char player1Down;
	private char player1Left;
	private char player2Up;
	private char player2Right;
	private char player2Down;
	private char player2Left;
	
	/**
	 * Creates a new config object with default controls.
	 */
	public Config() {
		player1Up = 'w';
		player1Right = 'd';
		player1Down = 's';
		player1Left = 'a';
		player2Up = 'i';
		player2Right = 'l';
		player2Down = 'k';
		player2Left = 'j';
	}
	
	// TODO: Formalise these automatic comments.
	/**
	 * @return the player1Up
	 */
	public char getPlayer1Up() {
		return player1Up;
	}

	/**
	 * @param player1Up the player1Up to set
	 */
	public void setPlayer1Up(char player1Up) {
		this.player1Up = player1Up;
	}

	/**
	 * @return the player1Right
	 */
	public char getPlayer1Right() {
		return player1Right;
	}

	/**
	 * @param player1Right the player1Right to set
	 */
	public void setPlayer1Right(char player1Right) {
		this.player1Right = player1Right;
	}

	/**
	 * @return the player1Down
	 */
	public char getPlayer1Down() {
		return player1Down;
	}

	/**
	 * @param player1Down the player1Down to set
	 */
	public void setPlayer1Down(char player1Down) {
		this.player1Down = player1Down;
	}

	/**
	 * @return the player1Left
	 */
	public char getPlayer1Left() {
		return player1Left;
	}

	/**
	 * @param player1Left the player1Left to set
	 */
	public void setPlayer1Left(char player1Left) {
		this.player1Left = player1Left;
	}

	/**
	 * @return the player2Up
	 */
	public char getPlayer2Up() {
		return player2Up;
	}

	/**
	 * @param player2Up the player2Up to set
	 */
	public void setPlayer2Up(char player2Up) {
		this.player2Up = player2Up;
	}

	/**
	 * @return the player2Right
	 */
	public char getPlayer2Right() {
		return player2Right;
	}

	/**
	 * @param player2Right the player2Right to set
	 */
	public void setPlayer2Right(char player2Right) {
		this.player2Right = player2Right;
	}

	/**
	 * @return the player2Down
	 */
	public char getPlayer2Down() {
		return player2Down;
	}

	/**
	 * @param player2Down the player2Down to set
	 */
	public void setPlayer2Down(char player2Down) {
		this.player2Down = player2Down;
	}

	/**
	 * @return the player2Left
	 */
	public char getPlayer2Left() {
		return player2Left;
	}

	/**
	 * @param player2Left the player2Left to set
	 */
	public void setPlayer2Left(char player2Left) {
		this.player2Left = player2Left;
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
	// TODO: Figure out how to not have this exception.
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
	 * Allows the user to input in their config settings through the console.
	 */
	// TODO: Use the UI to do this.
	// TODO: Figure out how to not have this exception.
	public void editConfig() {
		// TODO: Get this to work via a function similar to getch() rather than just line by line input.
		try {
			System.in.skip(System.in.available());
			System.out.println("Player 1 Up: ");
			player1Up = (char)System.in.read();
			System.in.skip(System.in.available());
			System.out.println("Player 1 Right: ");
			player1Right = (char)System.in.read();
			System.in.skip(System.in.available());
			System.out.println("Player 1 Down: ");
			player1Down = (char)System.in.read();
			System.in.skip(System.in.available());
			System.out.println("Player 1 Left: ");
			player1Left = (char)System.in.read();
			System.in.skip(System.in.available());
			System.out.println("Player 2 Up: ");
			player2Up = (char)System.in.read();
			System.in.skip(System.in.available());
			System.out.println("Player 2 Right: ");
			player2Right = (char)System.in.read();
			System.in.skip(System.in.available());
			System.out.println("Player 2 Down: ");
			player2Down = (char)System.in.read();
			System.in.skip(System.in.available());
			System.out.println("Player 2 Left: ");
			player2Left = (char)System.in.read();
			System.in.skip(System.in.available());
		} catch (IOException e) {
			
		}
	}
	
	/**
	 * Copies a config object to this object.
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
	}
}
