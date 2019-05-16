import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Metal extends Resource {
	public Metal(float x,float y) throws SlickException
	{
		super(x,y);
		amount=3;
		image=new Image("assets/resources/metal_mine.png");
	}
	
}
