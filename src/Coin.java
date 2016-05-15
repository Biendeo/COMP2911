
public class Coin {
	private Coord pos;
	private boolean collectedPlayer1;
	private boolean collectedPlayer2;
	
	public Coin(Coord pos) {
		this.pos = pos;
		collectedPlayer1 = false;
		collectedPlayer2 = false;
	}
	
	public Coord getPos() {
		return pos.clone();
	}
	
	public boolean getCollectedPlayer1() {
		return collectedPlayer1;
	}
	
	public void setCollectedPlayer1(boolean collected) {
		collectedPlayer1 = collected;
	}
	
	public boolean getCollectedPlayer2() {
		return collectedPlayer2;
	}
	
	public void setCollectedPlayer2(boolean collected) {
		collectedPlayer2 = collected;
	}
}
