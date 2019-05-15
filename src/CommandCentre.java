import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class CommandCentre extends Building{
	
	public CommandCentre(float x,float y) throws SlickException
	{
		
		super(x,y);
		image=new Image("assets/buildings/command_centre.png");
		image_highlight= new Image("assets/highlight_large.png");
		isSelected=false;
	}
	public void render() {
    	if(isSelected==true) {
    		image_highlight.drawCentered(this.getX(),this.getY());
    	}
    	image.drawCentered(this.getX(), this.getY());
    }
}
