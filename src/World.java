import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is used to contain all the different objects in your game world, and schedule their interactions.
 */

public class World {
	//Initializing the variable map which is an object that stores the entire map of the game
	private static TiledMap map;
	
	//Initializing variables worldX and worldY which are used for camera purposes
	public float worldX;
	public float worldY;
	
	public final static int selectDistance=35;
	//Initializing the variable camera which is an object that stores the camera of the game, showing a specific part of the map
	public Camera camera;
	
	//Initializing variables which will hold the values of height and width in pixels of the map
	private static int mapWidthPix;
	private static int mapHeightPix;
	
	/*Initializing the variable units,resources and buildings which are array lists consisting
	 *  of objects of the Unit, Resource and Building class respectively
	 */
	public ArrayList<Unit> units;
	public ArrayList<Resource>resources;
	public ArrayList<Building> buildings;
	
	/*Initializing selectedUnit and selectedBuilding which will store the currently selected unit
	 *  or building respectively
	 */
	public Unit selectedUnit;
	public Building selectedBuilding;
	
	/*Initializing currMetal and currUbonbtainium which will store the player's metal values
	 *  and unobtainium values respectively
	 */
	public int currMetal;
	public int currUnobtainium;
	
	//Initializing unitMoving which is a boolean to check whether a unit is moving or not
	public boolean unitMoving;
	
	//Initializing truckdestroyed which will store the truck to be destroyed
	public Unit truckdestroyed;
	
	//Initializing numPulonActive which will store the number of active Pylons
	public int numPylonActive;
	
	//Getters and Setters for worldX and worldY
	public void setWorldX(float x) {
    	worldX=x;
    }
    public void setworldY(float y) {
    	worldY=y;
    }
    
    
	// Constructor to instantiate World
	public World() throws SlickException {
		// Creating an object of class TiledMap, map and giving it details from main.tmx file
		map= new TiledMap("assets/main.tmx");
		
		// Computing the height and width of the map in pixels
		mapWidthPix = map.getWidth() * map.getTileWidth();
		mapHeightPix = map.getHeight() * map.getTileHeight();
		
		// Creating an object of class Camera, camera and giving it the map and its pixel height and width
		camera= new Camera(map,mapWidthPix,mapHeightPix);
		
		//Declaring worldY and worldY values as to point to the centre of the map
		worldX= mapWidthPix/2;
		worldY=mapHeightPix/2;
		
		//Declaring unitMoving to be false
		unitMoving=false;
		
		//Declaring currMetal and currUnobtainium to both be 0
		currMetal=0;
		currUnobtainium=0;
		
		//Creating ArrayLists of their respective objects
		buildings= new ArrayList<Building>();
		units= new ArrayList<Unit>();
		resources= new ArrayList<Resource>();
		
		//Declaring the following variables to be null
		selectedUnit=null;
		selectedBuilding=null;
		truckdestroyed=null;
		
		//Declaring numPylonActive to be 0
		numPylonActive=0;
		
		//Calling the initialMap() function to fill in the ArrayLists
		initialMap(buildings,units,resources);
		
	}
	
	/** Reads csv file "objects.csv" and stores the relevant data in corresponding array lists
	 * @param buildings, and ArrayList containing all the buildings in world
     * @param units, and ArrayList containing all the units in world
     * @param resources, and ArrayList containing all the resources in world
     * @throws SlickException 
     * @throws NumberFormatException
     */
	private void initialMap(ArrayList<Building> buildings, ArrayList<Unit> units, ArrayList<Resource> resources) throws NumberFormatException, SlickException {
		
		//Using try catch for any errors and reading the csv file
		try (Scanner scanner = new Scanner(new FileReader("assets/objects.csv"))) {
			
			//Using scanner in a while to read the data of the file
		    while (scanner.hasNextLine()) {
		    	String text=scanner.nextLine();
		    	String[] columns = text.split(",");
		    	String temp=columns[0];
		    	
		    	/*Using a switch case to create and store required buildings, resources and units 
		    	 * in their respective array lists
		    	 */
		    	switch(temp) {
		    	case "command_centre":
		    		buildings.add(new CommandCentre(Integer.parseInt(columns[1]),Integer.parseInt(columns[2])));
		    		break;
		    		
		    	case "engineer":
		    		units.add(new Engineer(Integer.parseInt(columns[1]),Integer.parseInt(columns[2])));
		    		break;
		    		
		    	case "metal_mine":
		    		resources.add(new Metal(Integer.parseInt(columns[1]),Integer.parseInt(columns[2])));
		    		break;
		    		
		    	case "unobtainium_mine":
		    		resources.add(new Unobtainium(Integer.parseInt(columns[1]),Integer.parseInt(columns[2])));
		    		break;
		    	
		    	case "pylon":
		    		buildings.add(new Pylon(Integer.parseInt(columns[1]),Integer.parseInt(columns[2])));
		    		break;
		    	}
		    }
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/** Checks if a unit is to be selected, returns unit if true and null otherwise
     * @param units, and ArrayList containing all the units in world
     * @param input The Slick object for user inputs.
     */
	public Unit selectUnit(ArrayList<Unit> units,Input input) {

		//Looping through all the units in world
		for (Unit unit:units){
			
			/*Checking if the click is within 'selectDistance' amount of pixels away if true selects the unit,
			 *  snaps the camera to its position and returns the unit
			 */
			if(unit.distanceUnit(input.getAbsoluteMouseX()-camera.getX(),input.getAbsoluteMouseY()-camera.getY())<selectDistance) {
				unit.setisSelected(true);
				worldX=unit.getX();
				worldY=unit.getY();
				camera.snap(this);
				return unit;
			}
		}
		
		//If no unit is to be selected return null
		return null;
	}
	
	/** Checks if a building is to be selected, returns building if true and null otherwise
     * @param buildings, and ArrayList containing all the buildings in world
     * @param input The Slick object for user inputs.
     */
	public Building selectBuilding(ArrayList<Building> buildings,Input input) {
		
		//Looping through all the buildings in world
		for (Building building:buildings){
			
			/*Checking if the click is within 'selectDistance' amount of pixels away if true selects the building,
			 *  snaps the camera to its position and returns the building
			 */
			if(building.distanceBuilding(input.getAbsoluteMouseX()-camera.getX(),input.getAbsoluteMouseY()-camera.getY())<selectDistance) {
				building.isSelected=true;
				worldX=building.getX();
				worldY=building.getY();
				camera.snap(this);
				return building;
			}
		}
		
		//If no building is to be selected return null
		return null;
	}
	
	/** Finds the closest CommandCentre to a unit
     * @param unit an object of class Unit
     * @param buildings, and ArrayList containing all the buildings in world
     */
	public Building closestCmdCent(Unit unit,ArrayList<Building> buildings) {
		Building temp=null;
		float mindist= Float.MAX_VALUE;
		
		//Loops through all buildings in world
		for(Building building:buildings) {
			
			//Checks whether building is a CommandCentre
			if (building instanceof CommandCentre) 
			{
				//Checks if the CommandCentre is the closest one or not
				if(building.distanceBuilding(unit.getX(),unit.getY())<mindist) {
					temp=building;
					mindist=(float) building.distanceBuilding(unit.getX(),unit.getY());
				}
			}
		}
		
		//Returns closest CommandCentre
		return temp;
	}
	
	
	/** Update the game state for a frame.
     * @param input The Slick object for user inputs.
     * @param delta Time passed since last frame (milliseconds).
	 * @throws SlickException 
     */
	
	public void update(Input input, int delta) throws SlickException {
		
		//Movement of the camera using keys WASD
		if(input.isKeyDown(Input.KEY_W)||input.isKeyDown(Input.KEY_S)||input.isKeyDown(Input.KEY_A)||input.isKeyDown(Input.KEY_D)) {
			camera.KeyMove(input, this,delta);
			unitMoving=false;
			camera.isOffset=true;
		}
		
		//Check for if a unit or building is to be selected
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			
			//First deselect any previously selected Unit if selected
			if(selectedUnit!=null) {
				selectedUnit.setisSelected(false);
				if(selectedUnit.distanceUnit(selectedUnit.getDestX(), selectedUnit.getDestY())>selectDistance) {
					unitMoving=true;
				}
			}
			
			//Then deselect any previously selected building if selected
			if(selectedBuilding!=null) {
				selectedBuilding.isSelected=false;
			}
			
			//Calling selectedUnits and then selectBuilding if no unit is selected
			selectedUnit=selectUnit(units,input);
			if(selectedUnit==null) {
			selectedBuilding=selectBuilding(buildings,input);
			}
			else{
				selectedBuilding=null;
			}
			
		}
		
		//Checks for when a unit is selected
		if(selectedUnit!=null) {
			
			//Checks for when mouse right clicks while a unit is selected
			if(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
				
				//Discontinuing mining
				if(selectedUnit instanceof Engineer) {
					((Engineer) selectedUnit).isMining=false;
					((Engineer) selectedUnit).pastTime=0;
				}
				
				//Setting destination of unit to mouse coordinates unless a builder is building
				if(!(selectedUnit instanceof Builder && ((Builder )selectedUnit).isCreating)) {
					selectedUnit.setdestX(input.getAbsoluteMouseX()-camera.getX());
					selectedUnit.setdestY(input.getAbsoluteMouseY()-camera.getY());
					unitMoving=true;
				}
			}
			
			//Checks for when a unit is moving and is selected, adjusts camera accordingly
			if(unitMoving==true) {
				if (camera.isOffset==false) {
					camera.UnitMove(selectedUnit,this);
				}
			}
		}
		
		
		//Calling Unit.update to update all units in world
		Unit.update(this, delta,input,map);
		
		//Check to see if any truck has build a CommandCentre and needs to be destroyed
		if(truckdestroyed!=null) {
			units.remove(truckdestroyed);
			units.trimToSize();
			truckdestroyed=null;
		}
		//Calling Building.update to update all buildings in world
		Building.update(this,input,delta);
	}
	
	/** Render the entire screen, so it reflects the current game state.
     * @param g The Slick graphics object, used for drawing.
	 * @throws SlickException 
     */
	
	public void render(Graphics g) throws SlickException {
		// Rendering the camera (or screen) using translate() and off setting the map according to the values of x and y of camera
        g.translate(camera.getX(), camera.getY());
		
		// Rendering the map
		map.render(0,0);
		
		//Rendering all buildings in world
		for (Building building:buildings){
			building.render();

		}
		
		//Rendering all resources in world
		for (Resource resource: resources){
			resource.render();
			
		}
		
		//Rendering all units in world
		for (Unit unit : units){
			unit.render();
		}
		
		//Displaying player's Metal and Unobtainium values
		g.drawString("Metal: "+currMetal+"\nUnobtainium: "+currUnobtainium, 32-camera.getX(), 32-camera.getY());
		
		//Printing relevant information if a building or unit is selected
		if(selectedBuilding !=null) {
			selectedBuilding.printInfo(g,camera);
		}
		
		if(selectedUnit!=null) {
			selectedUnit.printInfo(g,camera);
		}
		
	}
}
