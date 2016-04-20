import java.awt.Color;

public class Player {
	private Maze m;
	private Coord pos;
	private Color color;
	private int id;
	
	private static int nextId = 1;
	
	public Player(Maze m, Coord pos, Color color) {
		this.m = m;
		this.pos = pos;
		this.color = color;
		this.id = nextId++;
	}
	
	public Player(Maze m, Coord pos) {
		this.m = m;
		this.pos = pos;
		this.color = UI.getRandomColor();
		this.id = nextId++;
	}
	
	public Coord getPos() {
		return pos.clone();
	}
	
	public Color getColor() {
		return new Color(color.getRGB());
	}
	
	public int getId() {
		return id;
	}
	
	public boolean move(Direction d) {
		if (m.canMove(pos, d)) {
			pos.move(d);
			return true;
		} else {
			return false;
		}
	}
}
