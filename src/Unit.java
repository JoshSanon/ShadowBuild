import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/* Class which controls the player's unit pieces in the game extends GamePiece and therefore has coordinates
 * */
public abstract class  Unit extends GamePiece{
	


	// Constant speed for the piece 
	private float speed;
	
	//Storing the number of pixels within which a unit can be selected
	public static int selectDistance=35;
	
	//Storing the number of pixels within which we consider a unit to have reached its destination
	private final float destinationDist=0.25f;

	// Storing x and y coordinates of the destination where the unit is meant to go
	private float destX;
	private float destY;
		
	// Storing the angle between current position and destination using atan2() function in variable angle
	private double angle;
	
	/*Initializing booleans isSelected and hasReachedDest which store information on whether a unit is selected
	 *or has reached its destination
	 */
	private boolean isSelected;
	public boolean hasReachedDest;
    
	/* Initializing the variable image and image_highlight which are objects of class Image that will store the image of the piece 
	 * and its highlighted version when selected
	 */
	public  Image image;
	public Image image_highlight;
	
	// Constructor of a Unit, it is an abstract class and can not be instantiated
	public Unit(float x,float y) throws SlickException
	{
		super(x,y);
		this.destX=x;
		this.destY=y;
		angle=0;
		setisSelected(false);
		hasReachedDest=true;
	}
	
	//Getters and Setters for the class

    public void setdestX(float x) {
    	destX=x;
    }
    public void setdestY(float y) {
    	destY=y;
    }
    public float getSpeed()
	{
		return this.speed;
	}

	public float getDestX() {
		return destX;
	}

	public float getDestY() {
		return destY;
	}

	public boolean getisSelected() {
		return isSelected;
	}

	public void setisSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
    
    /** Updates the unit's position and allows/ forbids it from doing actions
     * @param world The world in which the unit is being accessed.
     * @param input The Slick object for user inputs.
     * @param delta Time passed since last frame (milliseconds).
     * @param map The Entire map of the game
     * @throws SlickException
     */
    
    public static void update(World world,int delta,Input input,TiledMap map) throws SlickException {
    	//Looping through all the units in map and updating them respectively
    	for (Unit unit:world.units){
    		
    		//Loop to check if a Unit has touched a Pylon and activate it if true
			for(Building building : world.buildings) {
				if(building instanceof Pylon && unit.distanceUnit(building.getX(),building.getY())<selectDistance
						&&((Pylon)building).isActivated==false) {
					
					//Activating Pylon
					((Pylon) building).isActivated=true;
					world.numPylonActive++;
				}
			}
			
			//Check for updating an Engineer
			if(unit instanceof Engineer) {
				
				//Checking whether or not an engineer is able to mine
				if(((Engineer) unit).isMining==false) {
					if(((Engineer) unit).canMine(world.resources,world.numPylonActive))
					{
						((Engineer) unit).isMining=true;
					}		
				}
				
				//Loop through buildings and check if an engineer comes in contact with a Command Centre 
				for(Building building : world.buildings) {
					
					//Drops of mined resources and continues mining
					if(building instanceof CommandCentre && unit.distanceUnit(building.getX(),building.getY())<selectDistance 
							&& building.distanceBuilding(unit.getDestX(),unit.getDestY())<=selectDistance) {
						
						/*Checks if mined material is Metal or Unobtainium, depletes engineer's resource and
						 * deposits to the player's resources 
						 */
						if (((Engineer) unit).carryType=='M') {
							world.currMetal+=((Engineer) unit).currCarry;
							if(((Engineer) unit).currMined!=null) {
								((Engineer) unit).isMining=true;
							}
							((Engineer) unit).currCarry=0;
						}
						if (((Engineer) unit).carryType=='U') {
							world.currUnobtainium+=((Engineer) unit).currCarry;
							if(((Engineer) unit).currMined!=null) {
								((Engineer) unit).isMining=true;
							}
							((Engineer) unit).currCarry=0;
						}
					}
				}
				
				//Checks if engineer isMining true and if so calls mineMaterial function
				if(((Engineer) unit).isMining==true) {
					((Engineer) unit).mineMaterial(world.closestCmdCent(unit,world.buildings),delta,world,world.numPylonActive);
				}
			}
			
			//Check for updating Builder
			if(unit instanceof Builder) {
				
				//Checks whether or not Builder is able to build a Factory
				if(unit.getisSelected()) {
						((Builder) unit).canCreateFact(input,world,map);
				}
				
				//Calls the createFactory function which creates a factory when the required conditions are met.
				((Builder) unit).createFactory(world.buildings, delta);
				
				//If Builder is creating a Factory, dont allow it to move
				if(((Builder )unit).isCreating) {
					unit.setdestX(unit.getX());
					unit.setdestY(unit.getY());
					continue;
				}
			}
			
			//Check for Truck updates
			if(unit instanceof Truck) {
				
				//Checks whether or not Truck is able to build a Command Centre
				if(unit.getisSelected()) {
						((Truck) unit).canCreateCmdCent(input,map);
				}
				
				//Calls the createCmdCent function which creates a Command Centre when the required conditions are met.
				((Truck) unit).createCmdCent(world.buildings, delta);
				
				//If Truck is creating a CommandCentre, dont allow it to move
				if(((Truck )unit).isCreating) {
					unit.setdestX(unit.getX());
					unit.setdestY(unit.getY());
					continue;
				}
				
				//Check if Truck has built the CommandCentre and is ready to be destroyed
				if(((Truck )unit).toBeDestroyed) {
					world.truckdestroyed=unit;
				}
			}
			//Calls move function
			unit.move(delta,map,world.camera);
		}
    }
    /** Moves a unit on the map.
     * @param input The Slick object for user inputs.
     * @param delta Time passed since last frame (milliseconds).
     * @param map The Entire map of the game
     */
    public void move(int delta,TiledMap map,Camera camera) {
    	hasReachedDest=false;
		
    	angle = Math.atan2(destY-this.getY(),destX-this.getX());	
		
		/* Using distanceUnit() to find the distance between current position and destination and checking if its greater
		 * than the speed at which travels
		 */		
		if(distanceUnit(destX,destY)>destinationDist) {
			
			// Computing the new position of the piece as it moves towards its destination
			float new_x=this.getX()+(float)Math.cos(angle) *delta*this.getSpeed();
			float new_y=this.getY()+(float)Math.sin(angle) *delta*this.getSpeed();
			
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
			angle = Math.atan2(destY-this.getY(),destX-this.getX());
			this.setX(new_x);
			this.setY(new_y);
			}
		}
		else {
			hasReachedDest=true;
		}
    }
    
    /**Checks and returns whether a tile is Occupied or not
      * @param unit an object of class Unit 
      * @param map The Entire map of the game
      */
    public static boolean tileOccupied(Unit unit,TiledMap map) {
		int tileNumberX=(int)unit.getX()/map.getTileWidth();
		int tileNumberY=(int)unit.getY()/map.getTileHeight();
		int currTileId=map.getTileId(tileNumberX,tileNumberY,0);
		if(map.getTileProperty(currTileId,"occupied","").equals("false")) {
			return false;
		}
		return true;
	}
    
    // Renders the unit onto the screen
    public void render() {
    	if(getisSelected()==true) {
    		image_highlight.drawCentered(this.getX(),this.getY());
    	}
    	image.drawCentered(this.getX(), this.getY());
    }
    
    //Dummy function which will be overidden 
	public void printInfo(Graphics g, Camera camera) {
		return;
	}
	
	//Returns distance between the unit and another (x,y)coordinate using Math.hypot()
	public double distanceUnit(float x,float y) {
		return Math.hypot(x-this.getX(), y-this.getY());
	}
}