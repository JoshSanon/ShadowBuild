import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Factory extends Building {
	
	public int costTruck=150;
	public boolean isTraining;
	private long pastTime;
	private int createSpd=5;
	public Factory(float x,float y) throws SlickException
	{
		super(x,y);
		image=new Image("assets/buildings/factory.png");
		image_highlight= new Image("assets/highlight_large.png");
		isSelected=false;
		isTraining=false;
		pastTime=0;
	}
	
	public void render() {
    	if(isSelected==true) {
    		image_highlight.drawCentered(this.getX(),this.getY());
    	}
    	image.drawCentered(this.getX(), this.getY());
    }
	public void printInfo(Graphics g, Camera camera) {
		g.drawString("1- Create Truck", 32-camera.getX(), 100-camera.getY());
	}
	
	public void canCreate(Input input,World world) throws SlickException {
		
		if(input.isKeyPressed(Input.KEY_1) &&world.currMetal>=costTruck &&isTraining==false) {
			world.currMetal-=costTruck;
			isTraining=true;
		}
	
	}
	
	public void createUnit(ArrayList<Unit> units, int delta) throws SlickException {
		if (isTraining==true&&pastTime<createSpd*1000) {
			pastTime+=delta;
			
		}
		else if(isTraining==true&&pastTime>=createSpd*1000) {
			pastTime=0;
			units.add(new Truck(getX(),getY()));
			isTraining=false;
			
		}
	}
}
