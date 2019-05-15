import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Pylon extends Building{
	public boolean isActivated;
	public final boolean isSelected=false;
	public Pylon(float x,float y) throws SlickException
	{
		super(x,y);
		image=new Image("assets/buildings/pylon.png");
		image_highlight= new Image("assets/buildings/pylon_active.png");
		isActivated=false;
	}
	
	public void render() {
    	if(isActivated==true) {
    		image_highlight.drawCentered(this.getX(),this.getY());
    	}
    	image.drawCentered(this.getX(), this.getY());
    }
}
