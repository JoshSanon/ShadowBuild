import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Unobtainium extends Resource{
	public Unobtainium(float x,float y) throws SlickException
	{
		super(x,y);
		amount=50;
		image=new Image("assets/resources/unobtainium_mine.png");
	}

}
