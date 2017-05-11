

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;

public class Character extends MovingImage {

	public static final int MARIO_WIDTH = 40;
	public static final int MARIO_HEIGHT = 60;

	private double xVelocity, yVelocity;
	//private boolean onASurface;
	private double friction;
	private double gravity;
	private double jumpStrength;

	public Character(String name, int x, int y, int width, int height) {
		super(name, x, y, width, height);
		xVelocity = 0;
		yVelocity = 0;
		//onASurface = false;
		//gravity = 0.7;
		friction = .85;
		//jumpStrength = 15;
	}

	// METHODS
	public void walk(int dir) {
		
		
		
	if(dir == -2 || dir == 2){
			
			int newdir = dir/2;
			
			//y = y + newdir*yVelocity;
			//y += newdir*4;
			if (yVelocity <= 4 && yVelocity >= -4){
				yVelocity += newdir;
				//y += newdir;
			}
		}
		
	
		if(dir == -1 || dir == 1){
			/*
			if (xVelocity <= 4 && xVelocity >= -4){
				xVelocity += dir;
			}
		x+= dir*xVelocity;
		*/
			if (xVelocity <= 4 && xVelocity >= -4)
				xVelocity += dir;
		}
		
	}

	public void jump() {
		//if (onASurface)
			//yVelocity -= jumpStrength;
	}

	public void act(ArrayList<Shape> obstacles) {
		double xCoord = getX();
		double yCoord = getY();
		double width = getWidth();
		double height = getHeight();

		// ***********Y AXIS***********

		//yVelocity += gravity; // GRAVITY
		//yVelocity = 1;
		
		yVelocity *= friction;
		double yCoord2 = yCoord + yVelocity;

		Rectangle2D.Double strechY = new Rectangle2D.Double(xCoord,Math.min(yCoord,yCoord2),width,height+Math.abs(yVelocity));

		//onASurface = false;
		
		//Problem is that is is testing to see if the mario is on the surface,
		//if mario is not on a surface, he keeps going.
		
		
		
		
		
		
		
		if (yVelocity > 0) {
			Shape standingSurface = null;
			for (Shape s : obstacles) {
				if (s.intersects(strechY)) {
					//onASurface = true;
					standingSurface = s;
					yVelocity = 0;

				}
			}
			if (standingSurface != null) {
				Rectangle r = standingSurface.getBounds();
				yCoord2 = r.getY()-height;
			
			}
		} else if (yVelocity < 0) {
			Shape headSurface = null;
			for (Shape s : obstacles) {
				if (s.intersects(strechY)) {
					headSurface = s;
					yVelocity = 0;
				}
			}
			if (headSurface != null) {
				Rectangle r = headSurface.getBounds();
				yCoord2 = r.getY()+r.getHeight();
			}
		}

		if (Math.abs(yVelocity) < .2)
			yVelocity = 0;
		
		
		/*if (yVelocity > 0) {
			Shape standingSurface = null;
			for (Shape s : obstacles) {
				if (s.intersects(strechY)) {
					standingSurface = s;
					yVelocity = 0;
				}
			}
			if (standingSurface != null) {
				Rectangle r = standingSurface.getBounds();
				yCoord2 = r.getX()-width;
			}
		} else if (yVelocity < 0) {
			Shape headSurface = null;
			for (Shape s : obstacles) {
				if (s.intersects(strechY)) {
					headSurface = s;
					yVelocity = 0;
				}
			}
			if (headSurface != null) {
				Rectangle r = headSurface.getBounds();
				yCoord2 = r.getX()+r.getWidth();
			}
		}


		if (Math.abs(yVelocity) < .2)
			yVelocity = 0;
*/
		
		
		
		
		
		

		// ***********X AXIS***********


		xVelocity *= friction;

		double xCoord2 = xCoord + xVelocity;

		Rectangle2D.Double strechX = new Rectangle2D.Double(Math.min(xCoord,xCoord2),yCoord2,width+Math.abs(xVelocity),height);

		if (xVelocity > 0) {
			Shape rightSurface = null;
			for (Shape s : obstacles) {
				if (s.intersects(strechX)) {
					rightSurface = s;
					xVelocity = 0;
				}
			}
			if (rightSurface != null) {
				Rectangle r = rightSurface.getBounds();
				xCoord2 = r.getX()-width;
			}
		} else if (xVelocity < 0) {
			Shape leftSurface = null;
			for (Shape s : obstacles) {
				if (s.intersects(strechX)) {
					leftSurface = s;
					xVelocity = 0;
				}
			}
			if (leftSurface != null) {
				Rectangle r = leftSurface.getBounds();
				xCoord2 = r.getX()+r.getWidth();
			}
		}


		if (Math.abs(xVelocity) < .2)
			xVelocity = 0;

		moveToLocation(xCoord2,yCoord2);

	}


}
