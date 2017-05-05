
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/*
 * Represents a moving image.
 *
 * by: Shelby
 * on: 5/3/13
 */
 
public class MovingImage extends Rectangle2D.Double {
	
	// FIELDS
	private Image image;
	
	
	// CONSTRUCTORS
	public MovingImage(String filename, int x, int y, int w, int h) {
		this((new ImageIcon(filename)).getImage(),x,y,w,h);
		
	}
	
	public MovingImage(Image img, int x, int y, int w, int h) {
		super(x,y,w,h);
		image = img;
	}
	
	
	// METHODS	
	public void moveToLocation(double x, double y) {
		super.x = x;
		super.y = y;
	}
	
	public void moveByAmount(double x, double y) {
		super.x += x;
		super.y += y;
	}
	
	public void applyWindowLimits(int windowWidth, int windowHeight) {
		x = Math.min(x,windowWidth-width);
		y = Math.min(y,windowHeight-height);
		x = Math.max(0,x);
		y = Math.max(0,y);
	}
	
	
	public void draw(Graphics g, ImageObserver io) {
		g.drawImage(image,(int)x,(int)y,(int)width,(int)height,io);
		AffineTransform at = AffineTransform.getTranslateInstance(100,100);
	}
	
	private BufferedImage loadImage(String filename){
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(filename));
		} catch (IOException e){
			
		}
		return img;
	}
	
	
}










