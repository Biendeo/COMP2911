
// TODO: Formally decide what this class is.
// Thomas: I'm using this more as a struct than a class.
public class Coord {
	public int x;
	public int y;
	
	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Coord clone() {
		return new Coord(x, y);
	}
	
	public boolean equals(Coord c) {
		if (x == c.x && y == c.y) {
			return true;
		} else {
			return false;
		}
	}
}
