import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Engineer extends Unit{
	private final float speed=0.1f;
	public final int mineSpeed=5;
	public int carryAmount;
	public int currCarry;
	public char carryType;
	public long pastTime;
	public boolean isMining;
	public Resource currMined;
	public Engineer(float x,float y) throws SlickException
	{
		super(x,y);
		image=new Image("assets/units/Engineer.png");
		image_highlight= new Image("assets/highlight.png");
		carryAmount=2;
		currCarry=0;
		carryType='X';
		isMining=false;
		currMined=null;
		
	}
	
	public float getSpeed()
	{
		return this.speed;
	}
	public boolean canMine(ArrayList<Resource> resources,int numPylonActive) {
		if(currCarry==carryAmount+numPylonActive) {
			return false;
		}
		if(hasReachedDest==true) {
			for(Resource resource: resources) {
				if (Math.hypot(getX()-resource.getX(), getY()-resource.getY())<35) {
					currMined= resource;
					return true;
				}
			}
		}
		return false;
	}
	public void mineMaterial(Building building , int delta,World world,int numPylonActive){
		if(currMined.amount>0 && Math.hypot(currMined.getX()-getX(), currMined.getY()-getY())<35) {
			if (pastTime<mineSpeed*1000) {
				pastTime+=delta;
			}
			else {
				if(currMined instanceof Unobtainium) {
					carryType='U';
				}
				else {
					carryType='M';

				}
				pastTime=0;
				System.out.println(currMined.amount);
				if(currMined.amount-(carryAmount+numPylonActive)<=0) {
					System.out.println(currMined.amount);
					currCarry=currMined.amount;
					currMined.amount=0;
					System.out.println(currMined.amount);
					world.resources.remove(currMined);
					world.resources.trimToSize();
					currMined=null;
					isMining=false;
				}
				else {
					currCarry=carryAmount+numPylonActive;
					currMined.amount-=carryAmount+numPylonActive;
				}
				setdestX(building.getX());
				setdestY(building.getY());
			}
		}
		else if(Math.hypot(building.getX()-getX(), building.getY()-getY())<35) {
				setdestX(currMined.getX());
				setdestY(currMined.getY());
		}
	}
}