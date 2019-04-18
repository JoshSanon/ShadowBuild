import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Scout {
	final static float speed=0.25f;
	private float x;
	private float y;
	private float destX;
	private float destY;   
	private double angle;
    
	private static Image image;
	public Scout(float x,float y) throws SlickException
	{
		image=new Image("assets/scout.png");
		this.x=x;
		this.y=y;
		this.destX=x;
		this.destY=y;
		angle=0;
	}
	
	public float getX() {
        return x;
    }
	
    public float getY() {
        return y;
    }
    public void move(Input input,int delta,TiledMap map,Camera camera) {
		if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
			
			destX=input.getAbsoluteMouseX()-camera.getX();
			destY=input.getAbsoluteMouseY()-camera.getY();
			angle = Math.atan2(destY-this.y,destX-this.x);	
		}
		if(Math.hypot(x-destX, y-destY)>speed) {
			
			float new_x=this.x+(float)Math.cos(angle) *delta*speed;
			float new_y=this.y+(float)Math.sin(angle) *delta*speed;
			
			int tileNumberX=(int)new_x/map.getTileWidth();
			int tileNumberY=(int)new_y/map.getTileHeight();
			
			int currTileId=map.getTileId(tileNumberX,tileNumberY,0);
			if(map.getTileProperty(currTileId,"solid","").equals("false")) {
			angle = Math.atan2(destY-this.y,destX-this.x);
			this.x=new_x;
			this.y= new_y;
			}
		}
    }
    public void render() {
        image.drawCentered(x, y);
    }
}
