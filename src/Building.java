import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

//Class which controls the player's building pieces in the game extends GamePiece and therefore has coordinates
public abstract class Building extends GamePiece{
	
	/* Initializing the variable image and image_highlight which are objects of class Image that will store the image of the piece 
	 * and its highlighted version when selected
	 */
	public Image image;
	public Image image_highlight;
	
	//Initializing boolean isSelected which stores information on whether a building is selected or not
	public boolean isSelected;

	// Constructor of a Building, it is an abstract class and can not be instantiated
	public Building(float x,float y) throws SlickException
	{
		super(x,y);
		isSelected= false;

	}
	
	/** Updates the unit's position and allows/ forbids it from doing actions
     * @param world The world in which the unit is being accessed.
     * @param input The Slick object for user inputs.
     * @param delta Time passed since last frame (milliseconds).
     * @throws SlickException
     */
	public static void update(World world,Input input, int delta) throws SlickException {
		
		//Looping through all the buildings in world
		for(Building building:world.buildings) {
			
			//Checks for if a building is a CommandCentre
			if(building instanceof CommandCentre) {
				
				//If it is selected, call the canCreate() function to check if it can create a unit
				if(building.isSelected) {
					((CommandCentre) world.selectedBuilding).canCreate(input,world);
				}
				
				//If it is able to, create a unit using the createUnit() function
				((CommandCentre) building).createUnit(world.units, delta);
			}
			
			//Checks for if a building is a Factory
			if(building instanceof Factory) {
				
				//If it is selected, call the canCreate() function to check if it can create a truck
				if(building.isSelected) {
					((Factory) world.selectedBuilding).canCreate(input,world);
				}
				
				//If it is able to, create a truck using the createUnit() function
				((Factory) building).createUnit(world.units, delta);
			}
		}
	}
	
	//Returns distance between the building and another (x,y)coordinate using Math.hypot()
	public double distanceBuilding(float x,float y) {
		return Math.hypot(x-this.getX(), y-this.getY());
	}
	
	//Renders the Building onto the screen
	public void render() throws SlickException{
		if(isSelected==true) {
    		image_highlight.drawCentered(this.getX(),this.getY());
    	}
    	image.drawCentered(this.getX(), this.getY());
	}
	
	//Dummy function which will be overriden in subclasses0
	public void printInfo(Graphics g, Camera camera) {
	}
}
