import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/*Class Truck, subclass of Unit. Moves quick and can create a CommandCentre
 *for no metal cost but is destroyed upon doing so
 */
public class Truck extends Unit {
	
	//Initializing the speed of the Truck
	private final float speed=0.25f;
	
	//Initializing pastTime which stores the amount of time passed since the Truck began creating a CommandCentre
	public long pastTime;
	
	//Initialzing createSpd which refers to the amount of time in seconds a Truck takes to create a CommandCentre
	public int createSpd=15;
	
	/*Initializing booleans isCreating and toBeDestroyed which store information on whether a Truck is creating a 
	 * CommandCentre or has finished creating one and is about to be destroyed
	 */
	public boolean isCreating;
	public boolean toBeDestroyed;
	
	@Override
	//Getter function to get Truck's speed
	public float getSpeed()
	{
		return this.speed;
	}
	
	//Constructor to instantiate an object of Truck
	public Truck(float x,float y) throws SlickException
	{
		super(x,y);
		image=new Image("assets/units/Truck.png");
		image_highlight= new Image("assets/highlight.png");
		
		//Default values of isCreating and toBeDestroyed set to False and pastTime set to 0
		isCreating=false;
		toBeDestroyed=false;
		pastTime=0;
	}
	
	/** Checks whether or not all the requirements are met for creating a CommandCentre,
	 *  if so sets isCreating to True.
     * @param input The Slick object for user inputs.
     * @param map The Entire map of the game
     * @throws SlickException
     */
	public void canCreateCmdCent(Input input,TiledMap map) throws SlickException {
		
		if(input.isKeyPressed(Input.KEY_1)&&isCreating==false&&tileOccupied(this,map)==false) {
			isCreating=true;
		}
	}
	
	
	/** Creates a CommandCentre after 15 seconds and sets toBeDestroyed to true
     * @param buildings ArrayList of type Building referring to all Buildings in world.
     * @param delta Time passed since last frame (milliseconds).
     * @throws SlickException
     */
	public void createCmdCent(ArrayList<Building> buildings, int delta) throws SlickException {
		
		//Check for whether or not 15 seconds have passed since the command was given
		if (isCreating==true&&pastTime<createSpd*1000) {
			pastTime+=delta;
			
		}
		
		//After the time has passed, creates the CommandCentre and sets toBeDestroyed to true
		else if(isCreating==true&&pastTime>=createSpd*1000) {
			pastTime=0;
			
			//Creating a new CommandCentre
			buildings.add(new CommandCentre(getX(),getY()));
			isCreating=false;
			toBeDestroyed=true;
		}
	}
	
	@Override
	//Displays the functionality of the Truck
	public void printInfo(Graphics g, Camera camera) {
		g.drawString("1- Create Command Centre", 32-camera.getX(), 100-camera.getY());
	}
	
}
