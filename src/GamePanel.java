import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.*;

import java.util.*;


public class GamePanel extends JPanel implements Runnable
{
	public static final int DRAWING_WIDTH = 800;
	public static final int DRAWING_HEIGHT = 600;

	private Rectangle screenRect;

	private Mario mario;
	private Enemy enemy1;
	private ArrayList<Shape> obstacles;
	private double mX, mY, mouseAngle;

	
	private KeyHandler keyControl;
	private MouseHandler mouseControl;


	public GamePanel () {
		super();

		mX = MouseInfo.getPointerInfo().getLocation().getX();
		mY = MouseInfo.getPointerInfo().getLocation().getY();

		keyControl = new KeyHandler();
		mouseControl = new MouseHandler();
		setBackground(Color.CYAN);
		screenRect = new Rectangle(0,0,DRAWING_WIDTH,DRAWING_HEIGHT);
		obstacles = new ArrayList<Shape>();
		obstacles.add(new Rectangle(200,400,400,50));
		obstacles.add(new Rectangle(0,250,100,50));
		obstacles.add(new Rectangle(700,250,100,50));
		obstacles.add(new Rectangle(375,300,50,100));
		obstacles.add(new Rectangle(300,250,200,50));
		spawnNewMario();
		spawnNewEnemy(100,100);
		new Thread(this).start();
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);  // Call JPanel's paintComponent method to paint the background

		Graphics2D g2 = (Graphics2D)g;

		int width = getWidth();
		int height = getHeight();

		double ratioX = (double)width/DRAWING_WIDTH;
		double ratioY = (double)height/DRAWING_HEIGHT;

		AffineTransform at = g2.getTransform();
		g2.scale(ratioX, ratioY);

		g.setColor(new Color(205,102,29));
		for (Shape s : obstacles) {
			g2.fill(s);
		}
		
	
		Point mousePoint = this.getMousePosition();
		
		try{
		mX = mousePoint.getX();
		mY = mousePoint.getY();
		}
		catch(NullPointerException e){
			System.out.println("Mouse is out of bounds");
		}
		
		double dY = mY - mario.getCenterY();
		double dX = mX - mario.getCenterX();
		
		Line2D.Double trackingLine = new Line2D.Double(mX, mY, mario.getCenterX(), mario.getCenterY());
		
		//idk if this belongs here
		//basically its a hit scan detection, if the tracking line is in contact with the enemy, you can hit the enemey
		
		//this way you can make your own hitbox
		//if(trackingLine.intersects(enemy1.makeHitBox())){
		if(trackingLine.intersects(enemy1.getBounds2D())){
			//System.out.println("hit!");
			enemy1.setIsHit(true);
			//draws a box where the eney can be hit
			g2.draw( new Rectangle2D.Double(mX,mY,6,6));
			
		}
		else{
			enemy1.setIsHit(false);
		}
		
		
		g2.draw(trackingLine);
		
		
		double scalar = dY/dX;
		
		
		if (mX < mario.getCenterX()){
			mouseAngle = Math.PI + Math.atan(scalar);
		} else {
			mouseAngle = Math.atan(scalar);
		}
		
		enemy1.draw(g2, null);
		
		g2.rotate(mouseAngle, mario.getCenterX(), mario.getCenterY());
		
		mario.draw(g2, null);
		
		// TODO Add any custom drawings here
	}


	public void spawnNewMario() {
		mario = new Mario(DRAWING_WIDTH/2-Mario.MARIO_WIDTH/2,50);
	}
	
	public void spawnNewEnemy(int locX, int locY) {
		enemy1 = new Enemy(locX,locY);
	}
	
	public void removeEnemy(){
		enemy1 = null;
	}


	public KeyHandler getKeyHandler() {
		return keyControl;
	}
	
	public MouseHandler getMouseHandler(){
		return mouseControl;
	}

	public void run() {
		while (true) { // Modify this to allow quitting
			long startTime = System.currentTimeMillis();





			if (keyControl.isPressed(KeyEvent.VK_A))
				mario.walk(-1);
			if (keyControl.isPressed(KeyEvent.VK_D))
				mario.walk(1);
			//if (keyControl.isPressed(KeyEvent.VK_UP))
				//mario.jump();
			if (keyControl.isPressed(KeyEvent.VK_W))
				mario.walk(-2);
			if (keyControl.isPressed(KeyEvent.VK_S))
				mario.walk(2);
			
			
			if(mouseControl.isClicked(MouseEvent.BUTTON1)){
				if(enemy1.getIsHit()){
					removeEnemy();
					//Spawns new enemy and if we do not spawn one right away it crashes
					spawnNewEnemy(0, 100);
					System.out.println("??");
				}
			}
			
			mario.act(obstacles);
			enemy1.act(obstacles);


			if (!screenRect.intersects(mario))
				spawnNewMario();

			repaint();

			long waitTime = 10 - (System.currentTimeMillis()-startTime);
			try {
				if (waitTime > 0)
					Thread.sleep(waitTime);
				else
					Thread.yield();
			} catch (InterruptedException e) {}
		}
	}



	public class KeyHandler implements KeyListener {

		private ArrayList<Integer> keys;

		public KeyHandler() {
			keys = new ArrayList<Integer>();
		}

		public void keyPressed(KeyEvent e) {
			keys.add(e.getKeyCode());
			
		}

		public void keyReleased(KeyEvent e) {
			Integer code = e.getKeyCode();
			while(keys.contains(code))
				keys.remove(code);
		}

		public void keyTyped(KeyEvent e) {

		}

		public boolean isPressed(int code) {
			return keys.contains(code);
		}
	}
	
	
	//I have no clue how mouse handler works
	public class MouseHandler implements MouseListener {
		private int mX;
		private int mY;
		private ArrayList<Integer> mouses;

		public MouseHandler() {
			mouses = new ArrayList<Integer>();
		}

		
		
		public boolean isClicked(int code) {
			return mouses.contains(code);
		}
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			//mX = e.getX();
			//mY = e.getY();
			//mouses.add(e.getButton());
			//System.out.println("hi");
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
		
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			mouses.add(e.getButton());
			System.out.println("hi");
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			Integer code = e.getButton();
			while(mouses.contains(code))
				mouses.remove(code);
		}

/*		private ArrayList<Integer> keys;

		public KeyHandler() {
			keys = new ArrayList<Integer>();
		*/

		/*public void keyPressed(KeyEvent e) {
			keys.add(e.getKeyCode());
		}*/

//		public void keyReleased(KeyEvent e) {
//			Integer code = e.getKeyCode();
//			while(keys.contains(code))
//				keys.remove(code);
//		}
//
//		public void keyTyped(KeyEvent e) {
//
//		}
//
//		public boolean isPressed(int code) {
//			return keys.contains(code);
//		}

		
		
	}

}
