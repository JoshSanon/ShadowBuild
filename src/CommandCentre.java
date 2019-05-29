import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class CommandCentre extends Building{
	
	/*Initializing boolean isTraining which stores information on whether
	 * or not a CommandCentre is training a unit
	 */
	public boolean isTraining;
	
	//Initializing pastTime which stores the amount of time passed since the CommandCentre began making a Unit
	private float pastTime;
	
	//Initializing unitType which is a character representing the type of Unit being created
	private char unitType;
	
	/*Initialzing createSpd and (costScout,costBuild, and costEng) which refers to the amount of time in seconds a CommandCentre takes
	 * to create a Unit and the cost of metal for the units respectively
	 */
	private final int createSpd=5;
	public int costScout=5;
	public int costBuild=10;
	public int costEng=20;
	
	//Constructor to instantiate an object of CommandCentre
	public CommandCentre(float x,float y) throws SlickException
	{
		super(x,y);
		image=new Image("assets/buildings/command_centre.png");
		image_highlight= new Image("assets/highlight_large.png");
		
		//Default value of isTraining is set to False and pastTime set to 0 and unitType to 'X'
		isTraining=false;
		unitType='X';
		pastTime=0;
	}
	
	/** Checks whether or not all the requirements are met for training a Unit, if so 
	 *  sets isTraining to True and unitType to its respective unit's first char.
     * @param input The Slick object for user inputs.
     * @param world The world in which the unit is being accessed.
     * @throws SlickException
     */
	public void canCreate(Input input,World world) throws SlickException {
		
		//Checks for training a Scout
		if(input.isKeyPressed(Input.KEY_1)&&world.currMetal>=costScout &&isTraining==false) {
			
			//Using the metal from the player's resource
			world.currMetal-=costScout;
			unitType='S';
			isTraining=true;
		}
		
		//Checks for training a Builder
		if(input.isKeyPressed(Input.KEY_2)&&world.currMetal>=costBuild &&isTraining==false) {
			
			//Using the metal from the player's resource
			world.currMetal-=costBuild;
			unitType='B';
			isTraining=true;
		}
		
		//Checks for training an Engineer
		if(input.isKeyPressed(Input.KEY_3)&&world.currMetal>=costEng &&isTraining==false) {
			
			//Using the metal from the player's resource
			world.currMetal-=costEng;
			unitType='E';
			isTraining=true;
		}
	}
	
	/** Creates a Unit after 5 seconds 
     * @param buildings ArrayList of type Unit referring to all units in world.
     * @param delta Time passed since last frame (milliseconds).
     * @throws SlickException
     */
	public void createUnit(ArrayList<Unit> units, int delta) throws SlickException {
		
		//Check for whether or not 5 seconds have passed since the command was given
		if (isTraining==true&&pastTime<createSpd*1000) {
			pastTime+=delta;
		}
		
		//After the time has passed, creates the Unit and sets isTraining to false
		else if(isTraining==true&&pastTime>=createSpd*1000) {
			pastTime=0;
			
			//Creating a Scout
			if(unitType=='S') {
				units.add(new Scout(getX(),getY()));
			}
			
			//Creating a Builder
			if(unitType=='B') {
				units.add(new Builder(getX(),getY()));
			}
			
			//Creating an Engineer
			if(unitType=='E') {
				units.add(new Engineer(getX(),getY()));
			}
			isTraining=false;
			
		}
	}
	
	@Override
	//Displays the functionality of the CommandCentre
	public void printInfo(Graphics g, Camera camera) {
		g.drawString("1- Create Scout\n2- Create Builder\n3- Create Engineer\n", 32-camera.getX(), 100-camera.getY());
	}
}
