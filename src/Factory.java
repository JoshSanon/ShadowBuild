import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Factory extends Building {
	
	public boolean isSelected;
	public Factory(float x,float y) throws SlickException
	{
		super(x,y);
		image=new Image("assets/buildings/factory.png");
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
