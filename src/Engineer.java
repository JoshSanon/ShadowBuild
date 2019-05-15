import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Engineer extends Unit{
	private final float speed=0.1f;
	public Engineer(float x,float y) throws SlickException
	{
		super(x,y);
		image=new Image("assets/units/Engineer.png");
		image_highlight= new Image("assets/highlight.png");
		
	}
	
	public float getSpeed()
	{
		return this.speed;
	}

}

