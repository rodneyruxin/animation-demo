

import java.awt.*;
import java.awt.geom.Rectangle2D;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Enemy extends MovingImage {

	public static final int ENEMY_WIDTH = 40;
	public static final int ENEMY_HEIGHT = 60;
	
	private double dX, dY;
	private BufferedImage img;
	private boolean isHit;
	//the hitbox field(rect) should move with the image, so that way you won't have to create new hitboxes every second
	//not implemented yet
	private Rectangle2D.Double hitbox;
	
	public Enemy(int x, int y) {
		super("survivor-idle_rifle_0.png", x, y, ENEMY_WIDTH, ENEMY_HEIGHT);
		dX = 0;
		dY = 0;
		loadImage("mario.png");
	}

	// METHODS
	public void walk(int dir) {
		// WALK!
		//THIS MAKES IT NOT MERGE!!!
		
	}
	
	public BufferedImage getImage(){
		return img;
	}

	public void jump() {
		// JUMP!
	}

	public void act(ArrayList<Shape> obstacles) {
		//dY += 0.5;
		x += 0.5;
		
		
	}
	
	private void loadImage(String filename){
		img = null;
		try{
			img = ImageIO.read(new File(filename));
		} catch(IOException e){
			
		}
	}
	
	//should only be called once within this class for most efficiency
	public Rectangle2D.Double makeHitBox(){
		return  new Rectangle2D.Double(x, y, ENEMY_WIDTH, ENEMY_HEIGHT);
	}
	
	public void setIsHit(boolean hit){
		isHit = hit;
	}
	public boolean getIsHit(){
	return isHit;
	}
	
	//doesn't reallly work
	/*
	public void removeEnemy(){
		//hitbox = null;
		//img = null;
		dX = 0;
		dY = 0;
		
		
		x= -100;
		System.out.println("remove");
	}
	*/
	
}
