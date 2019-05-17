import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Scout extends Unit{
	private final float speed=0.3f;
	public Scout(float x,float y) throws SlickException
	{
		super(x,y);
		image=new Image("assets/units/Scout.png");
		image_highlight= new Image("assets/highlight.png");
		
	}
	
	public float getSpeed()
	{
		return this.speed;
	}
}

