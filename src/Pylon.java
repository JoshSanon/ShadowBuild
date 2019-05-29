import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/*Pylon class, a subclass of Building, it can not create anything but can be activated
 * as a unit touches it and allows Engineers to mine faster.
 */
public class Pylon extends Building{
	//Initializing boolean isActivated which stores information on whether a Pylon is activated or not
	public boolean isActivated;
	
	//Constructer to instantiate an object of the Pylon class
	public Pylon(float x,float y) throws SlickException
	{
		super(x,y);
		image=new Image("assets/buildings/pylon.png");
		image_highlight= new Image("assets/highlight_large.png");
		
		//isActivated is given a default value of false
		isActivated=false;
	}
	
	@Override
	//Renders the Pylon onto the screen
	public void render() throws SlickException {
    	if(isSelected==true) {
    		image_highlight.drawCentered(this.getX(),this.getY());
    	}
    	
    	//Its image changes when activated
    	if(isActivated==true) {
    		image=new Image("assets/buildings/pylon_active.png");
    	}
    	image.drawCentered(this.getX(), this.getY());
    }

}
