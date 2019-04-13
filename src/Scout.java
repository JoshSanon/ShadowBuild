import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Scout {
	final static double speed=0.25;
	public float x;
    public float y;

	private Image image;
	public Scout(float x,float y) throws SlickException
	{
		image=new Image("assets/scout.png");
		this.x=x;
		this.y=y;
	}
	
	public float getX() {
        return x;
    }
	
    public float getY() {
        return y;
    }
    public void move(Input input,int delta) {
    	float angle;
		if (input.isMousePressed(input.MOUSE_RIGHT_BUTTON)) {
			angle = (float)Math.atan2(input.getMouseY()-this.y,input.getMouseX()-this.x);	
		}
    	this.x+=(float)Math.cos(angle) *delta*speed;
    	this.y+= (float)Math.sin(angle)*delta*speed;
    }
    public void render() {
        image.drawCentered(x, y);
    }

}
