import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;

public class Camera {
	/** window width, in pixels */
    public static final int WINDOW_WIDTH = 1024;
    /** window height, in pixels */
    public static final int WINDOW_HEIGHT = 768;

    private float x, y;
    private int mapWidth, mapHeight;

    public Camera(TiledMap map, int mapWidth, int mapHeight) {
        x = 0;
        y = 0;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }
    public float getX() {
    	return x;
    }
    public float getY() {
    	return y;
    }

    public void translate(Graphics g, Scout scout) {

        if (scout.getX() - WINDOW_WIDTH / 2 < 0) {
            x = 0;
        } else if (scout.getX() + WINDOW_WIDTH / 2 > mapWidth) {
            x = -mapWidth + WINDOW_WIDTH;
        } else {
            x =  -scout.getX() + WINDOW_WIDTH / 2;
        }

        if (scout.getY() - WINDOW_HEIGHT / 2 < 0) {
            y = 0;
        } else if (scout.getY() + WINDOW_HEIGHT / 2 > mapHeight) {
            y = -mapHeight + WINDOW_HEIGHT;
        } else {
            y =  -scout.getY() + WINDOW_HEIGHT / 2;
        }
        g.translate(x, y);
    }
}