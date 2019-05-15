import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * This class is used to contain all the different objects in your game world, and schedule their interactions.
 */

public class World {
	// Initializing the variable map which is an object that stores the entire map of the game
	private static TiledMap map;
	
	public float worldX;
	public float worldY;
	
	public final static int selectDistance=35;
	// Initializing the variable camera which is an object that stores the camera of the game, showing a specific part of the map
	private static Camera camera;
	
	private Building[] buildings;
	
	// Initializing variables which will hold the values of height and width in pixels of the map
	private static int mapWidthPix;
	private static int mapHeightPix;
	
	// Initializing the variable Unit, which is an object and the player's piece in the game
	private Unit units[];
	private Resource resources[];
	public int unitsCnt;
	public int buildingsCnt;
	public int resourcesCnt;
	boolean unitMoving;
	public Unit selectedUnit;
	public Building selectedBuilding;
	
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
		worldX= mapWidthPix/2;
		worldY=mapHeightPix/2;
		unitMoving=false;
		unitsCnt=0;
		buildingsCnt=0;
		resourcesCnt=0;
		buildings=new Building[100];
		units= new Unit[100];
		resources= new Resource[100];
		initialMap(buildings,units,resources);
		selectedUnit=null;
		selectedBuilding=null;
		
	}
	
	private void initialMap(Building[] buildings, Unit[] units, Resource[] resources) throws NumberFormatException, SlickException {
		try (Scanner scanner = new Scanner(new FileReader("assets/objects.csv"))) {
		    while (scanner.hasNextLine()) {
		    	String text=scanner.nextLine();
		    	String[] columns = text.split(",");
		    	String temp=columns[0];
		    	switch(temp) {
		    	case "command_centre":
		    		buildings[buildingsCnt++]=new CommandCentre(Integer.parseInt(columns[1]),Integer.parseInt(columns[2]));
		    		break;
		    		
		    	
		    	case "engineer":
		    		units[unitsCnt++]=new Engineer(Integer.parseInt(columns[1]),Integer.parseInt(columns[2]));
		    		break;
		    		
		    	case "metal_mine":
		    		resources[resourcesCnt++]=new Metal(Integer.parseInt(columns[1]),Integer.parseInt(columns[2]));
		    		break;
		    		
		    	case "unobtainium_mine":
		    		resources[resourcesCnt++]=new Unobtainium(Integer.parseInt(columns[1]),Integer.parseInt(columns[2]));
		    		break;
		    	
		    	case "pylon":
		    		buildings[buildingsCnt++]=new Pylon(Integer.parseInt(columns[1]),Integer.parseInt(columns[2]));
		    		break;
		    	}
		    }
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Unit select(Unit[] units,Input input) {
		for (int i=0; i<unitsCnt;i++){
			if(Math.hypot(units[i].getX()-(input.getAbsoluteMouseX()-camera.getX()), units[i].getY()-(input.getAbsoluteMouseY()-camera.getY()))<selectDistance) {
				units[i].isSelected=true;
				worldX=units[i].getX();
				worldY=units[i].getY();
				camera.snap(this);
				return units[i];
			}
		}
		return null;
	}
	public Building select(Building[] buildings,Input input) {
		for (int i=0; i<buildingsCnt;i++){
			
			if(Math.hypot(buildings[i].getX()-(input.getAbsoluteMouseX()-camera.getX()), buildings[i].getY()-(input.getAbsoluteMouseY()-camera.getY()))<selectDistance) {
				buildings[i].isSelected=true;
				worldX=buildings[i].getX();
				worldY=buildings[i].getY();
				camera.snap(this);
				return buildings[i];
			}
		}
		return null;
		
	}
	/** Update the game state for a frame.
     * @param input The Slick object for user inputs.
     * @param delta Time passed since last frame (milliseconds).
     */
	
	public void update(Input input, int delta) {
		
		if(input.isKeyDown(Input.KEY_W)||input.isKeyDown(Input.KEY_S)||input.isKeyDown(Input.KEY_A)||input.isKeyDown(Input.KEY_D)) {
			camera.KeyMove(input, this,delta);
			unitMoving=false;
			camera.isOffset=true;
		}
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			selectedUnit=select(units,input);
			selectedBuilding=select(buildings,input);
		}
		// Calling the method move in the class Unit which allows player to move their piece
		if(selectedUnit!=null) {
			
		if(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
			selectedUnit.setdestX(input.getAbsoluteMouseX()-camera.getX());
			selectedUnit.setdestY(input.getAbsoluteMouseY()-camera.getY());
			unitMoving=true;
		}
		
		selectedUnit.move(delta,map,camera);
		if(unitMoving==true) {
			
			if (camera.isOffset==false) {
				camera.UnitMove(selectedUnit,this);
				
			}
		}
		}
	}
	
	/** Render the entire screen, so it reflects the current game state.
     * @param g The Slick graphics object, used for drawing.
     */
	
	public void render(Graphics g) {
		// Rendering the camera (or screen) using translate() and offsetting the map according to the values of x and y of camera
        g.translate(camera.getX(), camera.getY());
		
		// Rendering the map
		map.render(0,0);
		//Rendering the player's piece- Unit
		for (int i=0; i<buildingsCnt;i++){
			buildings[i].render();

		}
		for (Unit unit : units){
			if(unit!=null) {
			unit.render();
			}
		}
		for (Resource resource: resources){
			if(resource!=null) {
			resource.render();
			}
		}
		
	}
}
