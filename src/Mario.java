

import java.awt.*;
import java.awt.geom.Rectangle2D;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Mario extends MovingImage {

	public static final int MARIO_WIDTH = 40;
	public static final int MARIO_HEIGHT = 60;
	
	private double dX, dY;
	private BufferedImage img;

	
	public Mario(int x, int y) {
		super("survivor-idle_rifle_0.png", x, y, MARIO_WIDTH, MARIO_HEIGHT);
		dX = 0;
		dY = 0;
		loadImage("survivor-idle_rifle_0.png");
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
		dY += 0.5;
		
		
	}
	
	private void loadImage(String filename){
		img = null;
		try{
			img = ImageIO.read(new File(filename));
		} catch(IOException e){
			
		}
	}


}
