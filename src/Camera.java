import org.newdawn.slick.Input;
import org.newdawn.slick.tiled.TiledMap;

public class Camera {
	
	private final static float camera_spd=0.4f;
	
	// Initializing variables x and y to hold the x-y offset coordinates for the camera 
    private float x, y;
    
    // Initializing variables mapWidthPix and mapHeightPix storing the width and height of the map in pixels respectively.
    private int mapWidthPix, mapHeightPix;
    
    boolean isOffset;
    
    // Constructor to instantiate Camera
    public Camera(TiledMap map, int mapWidthPix, int mapHeightPix) {
        x = App.HALF_WINDOW_WIDTH-mapWidthPix/2;
        y = App.HALF_WINDOW_HEIGHT-mapHeightPix/2;
        this.mapWidthPix = mapWidthPix;
        this.mapHeightPix = mapHeightPix;
        isOffset=true;
    }
    
    // Getters for x and y
    public float getX() {
    	return x;
    }
    public float getY() {
    	return y;
    }

    /** Translate computes the offset value for x and y to make sure the camera moves with the Unit in the middle of the screen,
     * unless the Unit moves too close to the edge of the map.
     * @param g The Slick graphics object, used for drawing.
     * @param Unit Unit object representing the player piece.
     */
    public void UnitMove(Unit Unit,World world) {
    	
    	// Making sure the camera does not show off the map otherwise computes the x offset to keep the player at the center of screen
        if (Unit.getX() - App.HALF_WINDOW_WIDTH < 0) {
            x = 0;
        } else if (Unit.getX() + App.HALF_WINDOW_WIDTH > mapWidthPix) {
            x = App.WINDOW_WIDTH-mapWidthPix;
        } else {
            x =  App.HALF_WINDOW_WIDTH-Unit.getX();
        }
        
        // Making sure the camera does not show off the map otherwise computes the y offset to keep the player at the center of screen
        if (Unit.getY() - App.HALF_WINDOW_HEIGHT < 0) {
            y = 0;
        } else if (Unit.getY() + App.HALF_WINDOW_HEIGHT > mapHeightPix) {
            y = App.WINDOW_HEIGHT-mapHeightPix ;
        } else {
            y =  App.HALF_WINDOW_HEIGHT-Unit.getY() ;
        }
        world.worldX=Unit.getX();
        world.worldY=Unit.getY();
                
    }
    
    public void KeyMove(Input input,World world,int delta) {
    	if(input.isKeyDown(Input.KEY_W)){
    		if (world.worldY - App.HALF_WINDOW_HEIGHT < 0) {
                y = 0;
            } 
    		else {
    			world.worldY-=(delta*camera_spd);
    			this.y+=(delta*camera_spd);
            }
    	}
    	if(input.isKeyDown(Input.KEY_S)){
    		if (world.worldY + App.HALF_WINDOW_HEIGHT > mapHeightPix) {
                y = App.WINDOW_HEIGHT-mapHeightPix;
            } 
    		else {
    			world.worldY+=(delta*camera_spd);
    			this.y-=(delta*camera_spd);
            }
    	}
    	if(input.isKeyDown(Input.KEY_A)){
    		if (world.worldX - App.HALF_WINDOW_WIDTH < 0) {
                x = 0;
            } 
    		else {
    			world.worldX-=(delta*camera_spd);
    			this.x+=(delta*camera_spd);
            }
    	}
    	if(input.isKeyDown(Input.KEY_D)){
    		if (world.worldX + App.HALF_WINDOW_WIDTH > mapWidthPix) {
                x = App.WINDOW_WIDTH-mapWidthPix;
            } 
    		else {
    			world.worldX+=(delta*camera_spd);
    			this.x-=(delta*camera_spd);
            }
    	}
    }
    public void snap(World world) {
    	if (world.worldX- App.HALF_WINDOW_WIDTH < 0) {
            x = 0;
        } else if (world.worldX + App.HALF_WINDOW_WIDTH > mapWidthPix) {
            x = App.WINDOW_WIDTH-mapWidthPix;
        } else {
            x =  App.HALF_WINDOW_WIDTH-world.worldX;
        }
    	
    	if (world.worldY - App.HALF_WINDOW_HEIGHT < 0) {
            y = 0;
        } else if (world.worldY + App.HALF_WINDOW_HEIGHT > mapHeightPix) {
            y = App.WINDOW_HEIGHT-mapHeightPix ;
        } else {
            y =  App.HALF_WINDOW_HEIGHT-world.worldY ;
        }
    	isOffset=false;
    }
}