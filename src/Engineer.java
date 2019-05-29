import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

// Engineer Class, subclass of Unit. Moves slow and mines materials for the player
public class Engineer extends Unit{
	
	//Initializing the speed of the Engineer
	private final float speed=0.1f;
	
	//Initializing mineSpeed which denotes how long (in seconds) it takes an Engineer to mine
	private final int mineSpeed=5;
	
	//Initializing carryAmount which stores the maximum amount of resources an Engineer can carry
	private int carryAmount;
	
	//Initializing currCarry which stores the current amount of resources the Engineer is carrying
	public int currCarry;
	
	//Initializing carryType which stores the frist char of the type of resource the Engineer is carrying
	public char carryType;
	
	//Initializing pastTime which stores the amount of time passed since the Engineer began mining
	public long pastTime;
	
	/*Initializing booleans isMining which stores information on whether or not
	 *the Engineer is mining 
	 */
	public boolean isMining;
	
	//Initializing currMined which stores the Resource that the minier is currently mining
	public Resource currMined;
	
	@Override
	//Getter function to get Builder's speed
	public float getSpeed()
	{
		return this.speed;
	}
	
	//Constructor to instantiate an object of Engineer
	public Engineer(float x,float y) throws SlickException
	{
		super(x,y);
		image=new Image("assets/units/Engineer.png");
		image_highlight= new Image("assets/highlight.png");
		
		//Default values of carryAmount i set to 2, currCarry is set to 0 and carryType is 'X'
		carryAmount=2;
		currCarry=0;
		carryType='X';
		
		//isMining initially set to false and currMined is set to null
		isMining=false;
		currMined=null;
	}
	
	/** Checks whether or not all the requirements are met for an Engineer to mine,
	 *  if so sets returns true.
     * @param input The Slick object for user inputs.
     * @param world The world in which the unit is being accessed.
     * @param map The Entire map of the game
     */
	public boolean canMine(ArrayList<Resource> resources,int numPylonActive) {
		
		//Checks if Engineer is already carrying maximum amount of resources
		if(currCarry==carryAmount+numPylonActive) {
			return false;
		}
		
		//Checks if Engineer is within 35 pixels of a resource
		if(hasReachedDest==true) {
			for(Resource resource: resources) {
				if (distanceUnit(resource.getX(),resource.getY())<selectDistance) {
					currMined= resource;
					return true;
				}
			}
		}
		return false;
	}
	
	/** Creates a Factory after 10 seconds 
     * @param building and object of type Building referring to the closest CommandCentre.
     * @param delta Time passed since last frame (milliseconds).
     * @param world The world in which the unit is being accessed.
     * @param numPylonActive int referring to the number of active Pylons.
     */
	public void mineMaterial(Building building , int delta,World world,int numPylonActive){
		
		//Checks if Resource is not depleted and within 35 pixels
		if(currMined.amount>0 && distanceUnit(currMined.getX(), currMined.getY())<selectDistance) {
			
			//Check for whether or not 5 seconds have passed since the Engineer began mining
			if (pastTime<mineSpeed*1000) {
				pastTime+=delta;
			}
			
			
			else {
				
				//Checks the type of resource collected
				if(currMined instanceof Unobtainium) {
					carryType='U';
				}
				else {
					carryType='M';

				}
				pastTime=0;
				
				//Checks if resource is completely depleted, if so removes it from the world
				if(currMined.amount-(carryAmount+numPylonActive)<=0) {
					currCarry=currMined.amount;
					currMined.amount=0;
					world.resources.remove(currMined);
					world.resources.trimToSize();
					currMined=null;
					isMining=false;
				}
				else {
					currCarry=carryAmount+numPylonActive;
					currMined.amount-=carryAmount+numPylonActive;
				}
				
				//Sets the destination of the miner to the coordinates of the nearest CommandCentre
				setdestX(building.getX());
				setdestY(building.getY());
			}
		}
		
		/*Check if Engineer has reached the nearest CommandCentre, if so sets destination
		 * to the coordinates of the resource it was mining
		 */
		else if(distanceUnit(building.getX(),building.getY())<selectDistance) {
				setdestX(currMined.getX());
				setdestY(currMined.getY());
		}
	}
}