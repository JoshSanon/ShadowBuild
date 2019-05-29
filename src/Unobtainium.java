import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/*Metal class which is a subclass of Resource is a resource and mining it is the
* main objective of the game
*/
public class Unobtainium extends Resource{
	
	//Constructer to instantiate an object of Unobtainium
	public Unobtainium(float x,float y) throws SlickException
	{
		super(x,y);
		
		//Initially holding 50 as its amount
		amount=50;
		image=new Image("assets/resources/unobtainium_mine.png");
	}

}
