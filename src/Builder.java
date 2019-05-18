import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Builder extends Unit{
	private final float speed=0.1f;
	public boolean isCreating;
	public long pastTime;
	public int costFact=100;
	public int createSpd=10;
	public Builder(float x,float y) throws SlickException
	{
		super(x,y);
		image=new Image("assets/units/Builder.png");
		image_highlight= new Image("assets/highlight.png");
		isCreating=false;
		pastTime=0;
	}
	
	public float getSpeed()
	{
		return this.speed;
	}
	public void printInfo(Graphics g, Camera camera) {
		g.drawString("1- Create Factroy", 32-camera.getX(), 100-camera.getY());
	}
	
	public void canCreateFact(Input input,World world) throws SlickException {
		
		if(input.isKeyPressed(Input.KEY_1)&&world.currMetal>=costFact &&isCreating==false) {
			world.currMetal-=costFact;
			isCreating=true;
		}
	}
	
	public void createFactory(ArrayList<Building> buildings, int delta) throws SlickException {
		if (isCreating==true&&pastTime<createSpd*1000) {
			pastTime+=delta;
			
		}
		else if(isCreating==true&&pastTime>=createSpd*1000) {
			pastTime=0;
			buildings.add(new Factory(getX(),getY()));
			isCreating=false;
			
		}
	}
}
