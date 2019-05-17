import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class Building {
	// Storing x and y coordinates of the piece in variables x and y
	private float x;
	private float y; 
	
	public Image image;
	public Image image_highlight;
	public boolean isSelected;
	
	//Getters for x and y coordinates
	public float getX() {
	    return x;
	}
		
	public float getY() {
	    return y;
	}
	
	public Building(float x,float y) throws SlickException
	{
		// Creating an object of class Image, image and giving it details from Unit.png file
		this.x=x;
		this.y=y;

	}
	
	public void render() {
	}
	
	public void printInfo(Graphics g, Camera camera) {
		g.drawString("1- Create Scout\n2- Create Builder\n3- Create Engineer\n", 32-camera.getX(), 100-camera.getY());
	}
}
