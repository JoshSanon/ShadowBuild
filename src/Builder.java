import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/*Class Builder, subclass of Unit. Moves slowly and can build a Factory
 *for the cost of 100 metal.
 */
public class Builder extends Unit{
	
	//Initializing the speed of the Builder
	private final float speed=0.1f;
	
	/*Initializing booleans isCreating which stores information on whether or not
	 *a Builder is creating a Factory
	 */
	public boolean isCreating;
	
	//Initializing pastTime which stores the amount of time passed since the Builder began building a Factory
	public long pastTime;
	
	/*Initialzing createSpd and costFact which refers to the amount of time in seconds a Builder takes
	 * to build a Factory and the cost of metal for it respectively
	 */
	public int costFact=100;
	public int createSpd=10;
	
	@Override
	//Getter function to get Builder's speed
	public float getSpeed()
	{
		return this.speed;
	}
	
	//Constructor to instantiate an object of Builder
	public Builder(float x,float y) throws SlickException
	{
		super(x,y);
		image=new Image("assets/units/Builder.png");
		image_highlight= new Image("assets/highlight.png");
		
		//Default value of isCreating is set to False and pastTime set to 0
		isCreating=false;
		pastTime=0;
	}
	
	/** Checks whether or not all the requirements are met for building a Factory,
	 *  if so sets isCreating to true.
     * @param input The Slick object for user inputs.
     * @param world The world in which the unit is being accessed.
     * @param map The Entire map of the game
     * @throws SlickException
     */
	public void canCreateFact(Input input,World world,TiledMap map) throws SlickException {
		
		if(input.isKeyPressed(Input.KEY_1)&&world.currMetal>=costFact &&isCreating==false&&tileOccupied(this,map)==false) {
			
			//Using the metal from the player's resource
			world.currMetal-=costFact;
			isCreating=true;
		}
	}
	
	/** Creates a Factory after 10 seconds 
     * @param buildings ArrayList of type Building referring to all Buildings in world.
     * @param delta Time passed since last frame (milliseconds).
     * @throws SlickException
     */
	public void createFactory(ArrayList<Building> buildings, int delta) throws SlickException {
		
		//Check for whether or not 10 seconds have passed since the command was given
		if (isCreating==true&&pastTime<createSpd*1000) {
			pastTime+=delta;
			
		}
		//After the time has passed, creates the Factory and sets isCreating to false
		else if(isCreating==true&&pastTime>=createSpd*1000) {
			pastTime=0;
			buildings.add(new Factory(getX(),getY()));
			isCreating=false;
			
		}
	}
	
	@Override
	//Displays the functionality of the Builder
	public void printInfo(Graphics g, Camera camera) {
		g.drawString("1- Create Factory", 32-camera.getX(), 100-camera.getY());
	}
}
