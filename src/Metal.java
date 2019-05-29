import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/*Metal class which is a subclass of Resource is a resource which can be mined
*and used to create buildings and units
*/
public class Metal extends Resource {
	
	//Constructer to instantiate an object of Metal
	public Metal(float x,float y) throws SlickException
	{
		super(x,y);
		
		//Initially holding 500 as its amount
		amount=500;
		image=new Image("assets/resources/metal_mine.png");
	}
	
}
