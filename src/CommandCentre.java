import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class CommandCentre extends Building{
	public boolean isTraining;
	private float pastTime;
	private final int createSpd=5;
	char unitType;
	public int costScout=5;
	public int costBuild=10;
	public int costEng=20;
	
	public CommandCentre(float x,float y) throws SlickException
	{
		
		super(x,y);
		image=new Image("assets/buildings/command_centre.png");
		image_highlight= new Image("assets/highlight_large.png");
		isSelected=false;
		isTraining=false;
		unitType='X';
		pastTime=0;
	}
	public void render() {
    	if(isSelected==true) {
    		image_highlight.drawCentered(this.getX(),this.getY());
    	}
    	image.drawCentered(this.getX(), this.getY());
    }
	public void printInfo(Graphics g, Camera camera) {
		g.drawString("1- Create Scout\n2- Create Builder\n3- Create Engineer\n", 32-camera.getX(), 100-camera.getY());
	}
	
	public void canCreate(Input input,World world) throws SlickException {
		
		if(input.isKeyPressed(Input.KEY_1)&&world.currMetal>=costScout &&isTraining==false) {
			world.currMetal-=costScout;
			unitType='S';
			isTraining=true;
		}
		if(input.isKeyPressed(Input.KEY_2)&&world.currMetal>=costBuild &&isTraining==false) {
			world.currMetal-=costBuild;
			unitType='B';
			isTraining=true;
		}
		if(input.isKeyPressed(Input.KEY_3)&&world.currMetal>=costEng &&isTraining==false) {
			world.currMetal-=costEng;
			unitType='E';
			isTraining=true;
		}
	}
	
	public void createUnit(ArrayList<Unit> units, int delta) throws SlickException {
		if (isTraining==true&&pastTime<createSpd*1000) {
			pastTime+=delta;
			
		}
		else if(isTraining==true&&pastTime>=createSpd*1000) {
			pastTime=0;
			
			if(unitType=='S') {
				units.add(new Scout(getX(),getY()));
			}
			if(unitType=='B') {
				units.add(new Builder(getX(),getY()));
			}
			if(unitType=='E') {
				units.add(new Engineer(getX(),getY()));
			}
			isTraining=false;
			
		}
	}
}
