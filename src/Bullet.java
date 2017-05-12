import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;

public class Bullet extends MovingImage{

	private int bulletVelocity;
	private double bulletAngle;

	public Bullet(String filename, int x, int y, int w, int h, double crosshairX, double crosshairY ) {
		super(filename, x, y, w, h);
		bulletVelocity = 2;
		bulletAngle = 0;
		generateAngle(crosshairX, crosshairY);
		// TODO Auto-generated constructor stub
	}

	public void fire(double crosshairX, double crosshairY, Rectangle border){
		
//
//		if (crosshairX < x){
//			//mouseAngle = Math.PI + Math.atan(scalar);
//			bulletAngle = Math.PI + Math.atan((crosshairY-y)/(crosshairX - x));
//
//		} else {
//			bulletAngle = Math.atan((crosshairY-y)/(crosshairX - x));
//		}

		//while(this.intersects(border) == false){


		x += Math.cos(bulletAngle) * bulletVelocity;
		y += Math.sin(bulletAngle) * bulletVelocity;
	}
	
	private void generateAngle(double crosshairX, double crosshairY){
		if (crosshairX < x){
			//mouseAngle = Math.PI + Math.atan(scalar);
			bulletAngle = Math.PI + Math.atan((crosshairY-y)/(crosshairX - x));

		} else {
			bulletAngle = Math.atan((crosshairY-y)/(crosshairX - x));
		}
	}
	
	public void hitObstacle(ArrayList<Shape> obstacles){
		for (Shape s : obstacles) {
		if(this.getBounds2D().intersects(s.getBounds2D())){
			bulletVelocity = 0;
			x= 0;
			y = 0;
		}
			
		}
	}
	

}




