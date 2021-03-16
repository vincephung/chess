package pieces;

public abstract class Piece {
	private String color;
	private String type;
	private String position;
	
	public Piece(String color, String type, String position) {
		this.color = color;
		this.type = type;
		this.position = position;
	}
	
	public String getColor() {
		return color;
	}
	
	public String getType() {
		return type;
	}
	
	public String getPosition() {
		return position;
	}
	
	public void setPostion(String position) {
		this.position = position;
	}
	
	public String toString() {
		return color + type;
	}
	
	public abstract boolean move(String position, Piece[][] board);
}
