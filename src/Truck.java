import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Truck extends Unit {
	private final float speed=0.25f;
	public boolean isCreating;
	public long pastTime;
	public int createSpd=5;
	public boolean toBeDestroyed;
	public Truck(float x,float y) throws SlickException
	{
		super(x,y);
		image=new Image("assets/units/Truck.png");
		image_highlight= new Image("assets/highlight.png");
		isCreating=false;
		pastTime=0;
		toBeDestroyed=false;
	}
	
	public float getSpeed()
	{
		return this.speed;
	}
	public void printInfo(Graphics g, Camera camera) {
		g.drawString("1- Create Command Centre", 32-camera.getX(), 100-camera.getY());
	}
	
	public void canCreateCmdCent(Input input) throws SlickException {
		
		if(input.isKeyPressed(Input.KEY_1)&&isCreating==false) {
			isCreating=true;
		}
	}
	
	public void createCmdCent(ArrayList<Building> buildings, int delta) throws SlickException {
		if (isCreating==true&&pastTime<createSpd*1000) {
			pastTime+=delta;
			
		}
		else if(isCreating==true&&pastTime>=createSpd*1000) {
			pastTime=0;
			buildings.add(new CommandCentre(getX(),getY()));
			isCreating=false;
			toBeDestroyed=true;
		}
	}
}
