import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class Resource {
	// Storing x and y coordinates of the piece in variables x and y
		private float x;
		private float y; 
		public int amount;
		public Image image;
		
		//Getters for x and y coordinates
		public float getX() {
		    return x;
		}
			
		public float getY() {
		    return y;
		}
		
		public Resource(float x,float y) throws SlickException
		{
			// Creating an object of class Image, image and giving it details from Unit.png file
			this.x=x;
			this.y=y;

		}
		
		public void render() {
			image.drawCentered(this.getX(), this.getY());
		}

}
