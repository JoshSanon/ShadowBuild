import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Builder extends Unit{
	private final float speed=0.25f;
	public Builder(float x,float y) throws SlickException
	{
		super(x,y);
		image=new Image("assets/units/Builder.png");
		image_highlight= new Image("assets/highlight.png");
		
	}
	
	public float getSpeed()
	{
		return this.speed;
	}
}
