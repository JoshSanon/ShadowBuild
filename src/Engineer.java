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
	public Engineer(float x,float y) throws SlickException
	{
		super(x,y);
		image=new Image("assets/units/Engineer.png");
		image_highlight= new Image("assets/highlight.png");
		carryAmount=2;
		currCarry=0;
		carryType='X';
		isMining=false;
		
	}
	
	public float getSpeed()
	{
		return this.speed;
	}
	public Resource canMine(ArrayList<Resource> resources) {
		if(currCarry==carryAmount) {
			return null;
		}
		if(hasReachedDest==true) {
			for(Resource resource: resources) {
				if (Math.hypot(getX()-resource.getX(), getY()-resource.getY())<35) {
					return resource;
					
				}
			}
		}
		return null;
	}
	public void mineMaterial(Building building , int delta,Resource resource,World world){
		if(world.currMined.amount>0&&Math.hypot(world.currMined.getX()-getX(), world.currMined.getY()-getY())<35) {
			if (pastTime<mineSpeed*1000) {
				pastTime+=delta;
			}
			else {
				if(world.currMined instanceof Unobtainium) {
					carryType='U';
				}
				else {
					carryType='M';

				}
				pastTime=0;
				if(world.currMined.amount-carryAmount<=0) {
					currCarry=world.currMined.amount;
					world.currMined.amount=0;
					world.resources.remove(world.currMined);
					world.resources.trimToSize();
					isMining=false;
				}
				else {
					currCarry=carryAmount;
					world.currMined.amount-=carryAmount;
				}
				setdestX(building.getX());
				setdestY(building.getY());
			}
		}
		else if(Math.hypot(building.getX()-getX(), building.getY()-getY())<35) {
				setdestX(world.currMined.getX());
				setdestY(world.currMined.getY());
		}
	}
}