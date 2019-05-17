import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.tiled.TiledMap;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
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
	
	private ArrayList<Building> buildings;
	
	// Initializing variables which will hold the values of height and width in pixels of the map
	private static int mapWidthPix;
	private static int mapHeightPix;
	
	// Initializing the variable Unit, which is an object and the player's piece in the game
	private ArrayList<Unit> units;
	public ArrayList<Resource>resources;
	public Unit selectedUnit;
	public Building selectedBuilding;
	public TextField text;
	public int currMetal;
	public int currUnobtainium;
	public boolean unitMoving;

	public Unit truckdestroyed;
	
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
		currMetal=0;
		currUnobtainium=0;
		buildings= new ArrayList<Building>();
		units= new ArrayList<Unit>();
		resources= new ArrayList<Resource>();
		buildings.add(new Factory(100,100));
		initialMap(buildings,units,resources);
		selectedUnit=null;
		selectedBuilding=null;
		truckdestroyed=null;
		
	}
	
	private void initialMap(ArrayList<Building> buildings, ArrayList<Unit> units, ArrayList<Resource> resources) throws NumberFormatException, SlickException {
		try (Scanner scanner = new Scanner(new FileReader("assets/objects.csv"))) {
		    while (scanner.hasNextLine()) {
		    	String text=scanner.nextLine();
		    	String[] columns = text.split(",");
		    	String temp=columns[0];
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
	
	public Unit selectUnit(ArrayList<Unit> units,Input input) {
		for (Unit unit:units){
			if(Math.hypot(unit.getX()-(input.getAbsoluteMouseX()-camera.getX()), unit.getY()-(input.getAbsoluteMouseY()-camera.getY()))<selectDistance) {
				unit.isSelected=true;
				worldX=unit.getX();
				worldY=unit.getY();
				camera.snap(this);
				return unit;
			}
		}
		return null;
	}
	public Building selectBuilding(ArrayList<Building> buildings,Input input) {
		for (Building building:buildings){
			if(building instanceof Pylon) {
				continue;
			}
			
			if(Math.hypot(building.getX()-(input.getAbsoluteMouseX()-camera.getX()), building.getY()-(input.getAbsoluteMouseY()-camera.getY()))<selectDistance) {
				building.isSelected=true;
				worldX=building.getX();
				worldY=building.getY();
				camera.snap(this);
				return building;
			}
		}
		return null;
		
	}
	public Building closestCmdCent(Unit unit,ArrayList<Building> buildings) {
		Building temp=null;
		float mindist= Float.MAX_VALUE;
		for(Building building:buildings) {
			if (building instanceof CommandCentre) {
				if(Math.hypot(unit.getX()-building.getX(), unit.getY()-building.getY())<mindist) {
					temp=building;
					mindist=(float) Math.hypot(unit.getX()-building.getX(), unit.getY()-building.getY());
				}
			}
		}
		return temp;
		
	}
	/** Update the game state for a frame.
     * @param input The Slick object for user inputs.
     * @param delta Time passed since last frame (milliseconds).
	 * @throws SlickException 
     */
	
	public void update(Input input, int delta) throws SlickException {
		
		if(input.isKeyDown(Input.KEY_W)||input.isKeyDown(Input.KEY_S)||input.isKeyDown(Input.KEY_A)||input.isKeyDown(Input.KEY_D)) {
			camera.KeyMove(input, this,delta);
			unitMoving=false;
			camera.isOffset=true;
		}
		
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if(selectedUnit!=null) {
				selectedUnit.isSelected=false;
				if(Math.hypot(selectedUnit.getX()-selectedUnit.getDestX(), selectedUnit.getY()-selectedUnit.getDestY())>35) {
					unitMoving=true;
				}
			}if(selectedBuilding!=null) {
				selectedBuilding.isSelected=false;
			}
			selectedUnit=selectUnit(units,input);
			if(selectedUnit==null) {
			selectedBuilding=selectBuilding(buildings,input);
			}
			else{
				selectedBuilding=null;
			}
			
		}
		
		// Calling the method move in the class Unit which allows player to move their piece
		if(selectedUnit!=null) {
			
		if(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
			if(selectedUnit instanceof Engineer) {
				((Engineer) selectedUnit).isMining=false;
				((Engineer) selectedUnit).pastTime=0;
			}
			
			if(selectedUnit instanceof Builder && ((Builder )selectedUnit).isCreating) {
				
			}
			else {
			selectedUnit.setdestX(input.getAbsoluteMouseX()-camera.getX());
			selectedUnit.setdestY(input.getAbsoluteMouseY()-camera.getY());
			unitMoving=true;
			}
			
		}
		
		if(unitMoving==true) {
			
			if (camera.isOffset==false) {
				camera.UnitMove(selectedUnit,this);
			}
		}
	}
		
		for (Unit unit:units){
			for(Building building : buildings) {
				if(building instanceof Pylon && Math.hypot(building.getX()-unit.getX(), building.getY()-unit.getY())<35&&((Pylon)building).isActivated==false) {
					((Pylon)building).activate(units);
				}
			}
			
			if(unit instanceof Engineer) {
				if(((Engineer) unit).isMining==false) {
					if(((Engineer) unit).canMine(resources))
					{
						((Engineer) unit).isMining=true;
					}		
				}
				for(Building building : buildings) {
					if(building instanceof CommandCentre && Math.hypot(building.getX()-unit.getX(), building.getY()-unit.getY())<35) {
						if (((Engineer) unit).carryType=='M') {
							currMetal+=((Engineer) unit).currCarry;
							if(((Engineer) unit).currMined!=null) {
								((Engineer) unit).isMining=true;
							}
							((Engineer) unit).currCarry=0;
						}
						if (((Engineer) unit).carryType=='U') {
							currUnobtainium+=((Engineer) unit).currCarry;
							if(((Engineer) unit).currMined!=null) {
								((Engineer) unit).isMining=true;
							}
							((Engineer) unit).currCarry=0;
						}
					}
				}
				if(((Engineer) unit).isMining==true) {
					((Engineer) unit).mineMaterial(closestCmdCent(unit,buildings),delta,this);
				}
			}
			if(unit instanceof Builder) {
				if(unit.isSelected) {
					if(tileOccupied(unit)==false) {
						((Builder) unit).canCreateFact(input,this);
					}
				}
				((Builder) unit).createFactory(buildings, delta);
				if(((Builder )unit).isCreating) {
					unit.setdestX(unit.getX());
					unit.setdestY(unit.getY());
					continue;
				}
			}
			if(unit instanceof Truck) {
				if(unit.isSelected) {
					if(tileOccupied(unit)==false) {
						((Truck) unit).canCreateCmdCent(input);
					}
				}
				((Truck) unit).createCmdCent(buildings, delta);
				if(((Truck )unit).isCreating) {
					unit.setdestX(unit.getX());
					unit.setdestY(unit.getY());
					continue;
				}
				if(((Truck )unit).toBeDestroyed) {
					truckdestroyed=unit;
				}
			}
			
			unit.move(delta,map,camera);
		}
		if(truckdestroyed!=null) {
			units.remove(truckdestroyed);
			units.trimToSize();
			truckdestroyed=null;
		}
		for(Building building:buildings) {
			if(building instanceof CommandCentre) {
				if(building.isSelected) {
					((CommandCentre) selectedBuilding).canCreate(input,this);
				}
				((CommandCentre) building).createUnit(units, delta);
			}
			if(building instanceof Factory) {
				if(building.isSelected) {
					((Factory) selectedBuilding).canCreate(input,this);
				}
				((Factory) building).createUnit(units, delta);
			}
		}
		
	}
	
	private boolean tileOccupied(Unit unit) {
		int tileNumberX=(int)unit.getX()/map.getTileWidth();
		int tileNumberY=(int)unit.getY()/map.getTileHeight();
		int currTileId=map.getTileId(tileNumberX,tileNumberY,0);
		if(map.getTileProperty(currTileId,"occupied","").equals("false")) {
			return false;
		}
		return true;
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
		for (Building building:buildings){
			building.render();

		}
		for (Resource resource: resources){
			resource.render();
			
		}
		for (Unit unit : units){
			unit.render();
		}
		
		g.drawString("Metal: "+currMetal+"\nUnobtainium: "+currUnobtainium, 32-camera.getX(), 32-camera.getY());
		if(selectedBuilding !=null) {
			selectedBuilding.printInfo(g,camera);
		}
		if(selectedUnit!=null) {
			selectedUnit.printInfo(g,camera);
		}
		
	}
}
