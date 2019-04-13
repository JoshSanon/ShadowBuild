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
	public TiledMap map;
	public Scout scout;
	public World() throws SlickException {
		map= new TiledMap("assets/main.tmx");
		scout=new Scout(0,0);
	}
	public void update(Input input, int delta) {
		scout.move(input,delta);
	}
	
	public void render(Graphics g) {
		map.render(0,0);
		scout.render();
	}
}
