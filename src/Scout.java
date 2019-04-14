import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Scout {
	final static float speed=0.25f;
	private float x;
	private float y;
	private float dest_x;
	private float dest_y;   
	private float angle;
    
	private Image image;
	public Scout(float x,float y) throws SlickException
	{
		image=new Image("assets/scout.png");
		this.x=x;
		this.y=y;
		this.dest_x=x;
		this.dest_y=y;
		angle=0;
	}
	
	public float getX() {
        return x;
    }
	
    public float getY() {
        return y;
    }
    public void move(Input input,int delta) {
		if (input.isMousePressed(input.MOUSE_RIGHT_BUTTON)) {
			dest_x=input.getMouseX();
			dest_y=input.getMouseY();
			angle = (float)Math.atan2(dest_y-this.y,dest_x-this.x);	
		}
		if(Math.abs(Math.hypot(x-dest_x, y-dest_y)) >speed) {
			angle = (float)Math.atan2(dest_y-this.y,dest_x-this.x);	
			this.x+=(float)Math.cos(angle) *delta*speed;
			this.y+= (float)Math.sin(angle)*delta*speed;
		}
    }
    public void render() {
        image.drawCentered(x, y);
    }
}
