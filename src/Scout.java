import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/* Class which controls the player's piece in the game
 * */
public class Scout {
	// Constant speed for the piece 
	private final static float speed=0.25f;
	
	// Storing x and y coordinates of the piece in variables x and y
	private float x;
	private float y;
	
	// Storing x and y coordinates of the destination where the piece is meant to go
	private float destX;
	private float destY;
	
	// Storing the angle between current position and destination using atan2() function in variable angle
	private double angle;
    
	// Initializing the variable image which is an object of class Image that will store the image of the piece
	private static Image image;
	
	// Constructor to instantiate the player's piece
	public Scout(float x,float y) throws SlickException
	{
		// Creating an object of class Image, image and giving it details from scout.png file
		image=new Image("assets/scout.png");
		this.x=x;
		this.y=y;
		this.destX=x;
		this.destY=y;
		angle=0;
	}
	
	//Getters for x and y coordinates
	public float getX() {
        return x;
    }
	
    public float getY() {
        return y;
    }
    
    /** Updates the piece's position after user right clicks
     * @param input The Slick object for user inputs.
     * @param delta Time passed since last frame (milliseconds).
     * @param map The Entire map of the game
     */
    public void move(Input input,int delta,TiledMap map,Camera camera) {
    	
    	// Check for a right click by the player
		if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
			
			// Getting the coordinates of the mouse when user right clicks and stores is destX and destY
			destX=input.getAbsoluteMouseX()-camera.getX();
			destY=input.getAbsoluteMouseY()-camera.getY();
			
			// Using atan2 to find the angle required to travel by the piece
			angle = Math.atan2(destY-this.y,destX-this.x);	
		}
		
		/* Using math.hypot() to find the distance between current position and destination and checking if its greater
		 * than the speed at which travels
		 */		
		if(Math.hypot(x-destX, y-destY)>speed) {
			
			// Computing the new position of the piece as it moves towards its destination
			float new_x=this.x+(float)Math.cos(angle) *delta*speed;
			float new_y=this.y+(float)Math.sin(angle) *delta*speed;
			
			// Computing the tile number of the new position of the piece 
			int tileNumberX=(int)new_x/map.getTileWidth();
			int tileNumberY=(int)new_y/map.getTileHeight();
			
			// Finding the Tile ID using the tile numbers computed above 
			int currTileId=map.getTileId(tileNumberX,tileNumberY,0);
			
			// Checking if the tile is solid or not. If it is not solid, the piece moves towards its destination
			if(map.getTileProperty(currTileId,"solid","").equals("false")) {
			angle = Math.atan2(destY-this.y,destX-this.x);
			this.x=new_x;
			this.y= new_y;
			}
		}
    }
    
    // Renders the piece onto the screen
    public void render() {
        image.drawCentered(x, y);
    }
}
