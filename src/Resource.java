import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/* Class which controls the resources pieces in the game, extends GamePiece and therefore has coordinates
 * */
public abstract class Resource extends GamePiece{
	
	//Stores the amount of material the resource holds
	public int amount;
	
	// Initializing the variable image which is an object of class Image that will store the image of the piece
	public Image image;
	
	// Constructor of a Resource, it is an abstract class and can not be instantiated
	public Resource(float x,float y) throws SlickException
	{
		super(x,y);
	}
	
	// Renders the resource onto the screen
	public void render() {
		image.drawCentered(this.getX(), this.getY());
	}
}
