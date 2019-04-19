import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;

public class Camera {
	
	// Initializing variables x and y to hold the x-y offset coordinates for the camera 
    private float x, y;
    
    // Initializing variables mapWidthPix and mapHeightPix storing the width and height of the map in pixels respectively.
    private int mapWidthPix, mapHeightPix;
    
    // Constructor to instantiate Camera
    public Camera(TiledMap map, int mapWidthPix, int mapHeightPix) {
        x = 0;
        y = 0;
        this.mapWidthPix = mapWidthPix;
        this.mapHeightPix = mapHeightPix;
    }
    
    // Getters for x and y
    public float getX() {
    	return x;
    }
    public float getY() {
    	return y;
    }

    /** Translate computes the offset value for x and y to make sure the camera moves with the scout in the middle of the screen,
     * unless the scout moves too close to the edge of the map.
     * @param g The Slick graphics object, used for drawing.
     * @param scout Scout object representing the player piece.
     */
    public void translate(Graphics g, Scout scout) {
    	
    	// Making sure the camera does not show off the map otherwise computes the x offset to keep the player at the center of screen
        if (scout.getX() - App.HALF_WINDOW_WIDTH < 0) {
            x = 0;
        } else if (scout.getX() + App.HALF_WINDOW_WIDTH > mapWidthPix) {
            x = App.WINDOW_WIDTH-mapWidthPix;
        } else {
            x =  App.HALF_WINDOW_WIDTH-scout.getX();
        }
        
        // Making sure the camera does not show off the map otherwise computes the y offset to keep the player at the center of screen
        if (scout.getY() - App.HALF_WINDOW_HEIGHT < 0) {
            y = 0;
        } else if (scout.getY() + App.HALF_WINDOW_HEIGHT > mapHeightPix) {
            y = App.WINDOW_HEIGHT-mapHeightPix ;
        } else {
            y =  App.HALF_WINDOW_HEIGHT-scout.getY() ;
        }
        
        // Using translate() to offset the map according to the values of x and y
        g.translate(x, y);
    }
}