import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Engineer extends Unit{
	private final float speed=0.3f;
	public final int mineSpeed=1;
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
	public boolean canMine(ArrayList<Resource> resources) {
		if(currCarry==carryAmount) {
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
	public void mineMaterial(Building building , int delta,World world){
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
				if(currMined.amount-carryAmount<=0) {
					currCarry=currMined.amount;
					currMined.amount=0;
					world.resources.remove(currMined);
					world.resources.trimToSize();
					currMined=null;
					isMining=false;
				}
				else {
					currCarry=carryAmount;
					currMined.amount-=carryAmount;
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