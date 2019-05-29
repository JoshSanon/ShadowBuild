import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

//Class Factory, subclass of Building. Can create a Truck for 150 metal.
public class Factory extends Building {
	
	/*Initializing boolean isTraining which stores information on whether
	 * or not a Factory is training a Truck
	 */
	public boolean isTraining;
	
	//Initializing pastTime which stores the amount of time passed since the Factory began making a Truck
	private long pastTime;
	
	/*Initialzing createSpd and costTruck which refers to the amount of time in seconds a Factory takes
	 * to create a Truck and the cost of metal for it respectively
	 */
	private int createSpd=5;
	public int costTruck=150;
	
	//Constructor to instantiate an object of Factory
	public Factory(float x,float y) throws SlickException
	{
		super(x,y);
		image=new Image("assets/buildings/factory.png");
		image_highlight= new Image("assets/highlight_large.png");
		
		//Default value of isTraining is set to False and pastTime set to 0
		isTraining=false;
		pastTime=0;
	}
	
	/** Checks whether or not all the requirements are met for training a Truck,
	 *  if so sets isTraining to True.
     * @param input The Slick object for user inputs.
     * @param world The world in which the unit is being accessed.
     * @throws SlickException
     */
	public void canCreate(Input input,World world) throws SlickException {
		
		if(input.isKeyPressed(Input.KEY_1) &&world.currMetal>=costTruck &&isTraining==false) {
			
			//Using the metal from the player's resource
			world.currMetal-=costTruck;
			isTraining=true;
		}
	
	}
	
	/** Creates a Truck after 5 seconds 
     * @param buildings ArrayList of type Unit referring to all units in world.
     * @param delta Time passed since last frame (milliseconds).
     * @throws SlickException
     */
	public void createUnit(ArrayList<Unit> units, int delta) throws SlickException {
		
		//Check for whether or not 5 seconds have passed since the command was given
		if (isTraining==true&&pastTime<createSpd*1000) {
			pastTime+=delta;
			
		}
		
		//After the time has passed, creates the Truck and sets isTraining to false
		else if(isTraining==true&&pastTime>=createSpd*1000) {
			pastTime=0;
			
			//Creating a truck
			units.add(new Truck(getX(),getY()));
			isTraining=false;
			
		}
	}
	
	@Override
	//Displays the functionality of the Factory
	public void printInfo(Graphics g, Camera camera) {
		g.drawString("1- Create Truck", 32-camera.getX(), 100-camera.getY());
	}
}
