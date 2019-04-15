import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

public class Camera {
	/** window width, in pixels */
    public static final int WINDOW_WIDTH = 1024;
    /** window height, in pixels */
    public static final int WINDOW_HEIGHT = 768;

    public int x, y;
    private int mapWidth, mapHeight;
    private Rectangle viewPort;

    public Camera(TiledMap map, int mapWidth, int mapHeight) {
        x = 0;
        y = 0;
        viewPort = new Rectangle(WINDOW_WIDTH/2,WINDOW_HEIGHT/2 , WINDOW_WIDTH, WINDOW_HEIGHT);
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    public void translate(Graphics g, Scout scout) {

        if (scout.getX() - WINDOW_WIDTH / 2 + 1 < 0) {
            x = 0;
        } else if (scout.getX() + WINDOW_WIDTH / 2 + 16 > mapWidth) {
            x = -mapWidth + WINDOW_WIDTH;
        } else {
            x = (int) -scout.getX() + WINDOW_WIDTH / 2 - 16;
        }

        if (scout.getY() - WINDOW_HEIGHT / 2 + 16 < 0) {
            y = 0;
        } else if (scout.getY() + WINDOW_HEIGHT / 2 + 16 > mapHeight) {
            y = -mapHeight + WINDOW_HEIGHT;
        } else {
            y = (int) -scout.getY() + WINDOW_HEIGHT / 2 - 16;
        }
        g.translate(x, y);
        viewPort.setX(-x);
        viewPort.setY(-y);
    }
}