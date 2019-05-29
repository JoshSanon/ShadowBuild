//Abstract class of every game piece containing just coordinates
public abstract class GamePiece {
	
	// Storing x and y coordinates of the piece in variables x and y
	private float x;
	private float y;
		
	// Constructor of a GamePiece, it is an abstract class and can not be instantiated	
	public GamePiece(float x,float y){
		this.setX(x);
		this.setY(y);
	}
		
	//Getters and setters for the function
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
}
