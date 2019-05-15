import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/* Class which controls the player's piece in the game
 * */
public abstract class  Unit {
	// Constant speed for the piece 
	private float speed;
	
	// Storing x and y coordinates of the piece in variables x and y
	private float x;
	private float y;
	
	// Storing x and y coordinates of the destination where the piece is meant to go
	private float destX;
	private float destY;
		
	// Storing the angle between current position and destination using atan2() function in variable angle
	private double angle;
	
	public boolean isSelected;
    
	// Initializing the variable image which is an object of class Image that will store the image of the piece
	public  Image image;
	public Image image_highlight;
	
	// Constructor to instantiate the player's piece
	public Unit(float x,float y) throws SlickException
	{
		// Creating an object of class Image, image and giving it details from Unit.png file
		this.x=x;
		this.y=y;
		this.destX=x;
		this.destY=y;
		angle=0;
		isSelected=false;
	}
	
	//Getters for x and y coordinates
	public float getX() {
        return x;
    }
	
    public float getY() {
        return y;
    }
    public void setdestX(float x) {
    	destX=x;
    }
    public void setdestY(float y) {
    	destY=y;
    }
    /** Updates the piece's position after user right clicks
     * @param input The Slick object for user inputs.
     * @param delta Time passed since last frame (milliseconds).
     * @param map The Entire map of the game
     */
    public void move(int delta,TiledMap map,Camera camera) {
		
    	angle = Math.atan2(destY-this.y,destX-this.x);	
		
		/* Using math.hypot() to find the distance between current position and destination and checking if its greater
		 * than the speed at which travels
		 */		
		if(Math.hypot(x-destX, y-destY)>this.getSpeed()) {
			
			// Computing the new position of the piece as it moves towards its destination
			float new_x=this.x+(float)Math.cos(angle) *delta*this.getSpeed();
			float new_y=this.y+(float)Math.sin(angle) *delta*this.getSpeed();
			
			// Computing the tile number of the new position of the piece 
			int tileNumberX=(int)new_x/map.getTileWidth();
			int tileNumberY=(int)new_y/map.getTileHeight();
			
			// Does not allow Unit to go out of the map
			if (tileNumberX>=map.getWidth()) {
				tileNumberX=map.getWidth()-1;
			}
			if (tileNumberY>=map.getHeight()) {
				tileNumberY=map.getHeight()-1;
			}
			
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
    	if(isSelected==true) {
    		image_highlight.drawCentered(this.getX(),this.getY());
    	}
    	image.drawCentered(this.x, this.y);
    }
	public float getSpeed()
	{
		return this.speed;
	}
}