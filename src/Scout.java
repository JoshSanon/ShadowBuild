import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

//Scout class which is a subclass of Unit is a a fast moving unit used for exploring the map
public class Scout extends Unit{
	
	//Initializing the speed of the scout
	private final float speed=0.3f;
	
	//Constructor to instantiate an object of Scout
	public Scout(float x,float y) throws SlickException
	{
		super(x,y);
		image=new Image("assets/units/Scout.png");
		image_highlight= new Image("assets/highlight.png");
		
	}
	
	@Override
	//Getter function for speed
	public float getSpeed()
	{
		return this.speed;
	}
}

