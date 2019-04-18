import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 * This class should be used to contain all the different objects in your game world, and schedule their interactions.
 * 
 * You are free to make ANY modifications you see fit.
 * These classes are provided simply as a starting point. You are not strictly required to use them.
 */

public class World {
	// Initializing the variable map which is an object that stores the entire map of the game
	private static TiledMap map;
	
	// Initializing the variable camera which is an object that stores the camera of the game, showing a specific part of the map
	private static Camera camera;
	
	// Initializing variables which will hold the values of height and width in pixels of the map
	private static int mapWidthPix;
	private static int mapHeightPix;
	
	// Initializing the variable scout, which is an object and the player's piece in the game
	private Scout scout;
	
	// Constructor to instantiate World
	
	public World() throws SlickException {
		// Creating an object of class TiledMap, map and giving it details from main.tmx file
		map= new TiledMap("assets/main.tmx");
		
		// Computing the height and width of the map in pixels
		mapWidthPix = map.getWidth() * map.getTileWidth();
		mapHeightPix = map.getHeight() * map.getTileHeight();
		
		// Creating an object of class Camera, camera and giving it the map and its pixel height and width
		camera= new Camera(map,mapWidthPix,mapHeightPix);
		
		// Creating the object of class Scout, scout and giving its initial coordinates on the map
		scout=new Scout(mapWidthPix/2,mapHeightPix/2);
	}
	
	/** Update the game state for a frame.
     * @param input The Slick object for user inputs.
     * @param delta Time passed since last frame (milliseconds).
     */
	
	public void update(Input input, int delta) {
		
		// Calling the method move in the class Scout which allows player to move their piece
		scout.move(input,delta,map,camera);
	}
	
	/** Render the entire screen, so it reflects the current game state.
     * @param g The Slick graphics object, used for drawing.
     */
	
	public void render(Graphics g) {
		// Rendering the camera (or screen)
		camera.translate(g, scout);
		
		// Rendering the map
		map.render(0,0);
		
		//Rendering the player's piece- scout
		scout.render();
	}
}
