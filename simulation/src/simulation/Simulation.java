package simulation;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;
public class Simulation extends Canvas implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7661068758602793816L;
	private BufferedImage outline, exclamation;
	private boolean running = false, intersected = false, alarm = false;
	private Thread thread;
	private Graphics g;
	private int xpos = -100, ypos = -100, scale = 0, t = 0, t2 = 0, xmov = 1, ymov = 1, rand;
	private Random random;;
	private Window window;
	private LinkedList<Collideable> objectList;
	String timeStamp;
	
	public Simulation(Window window){
		random = new Random();
		objectList = new LinkedList<Collideable>();
		this.window = window;
		try{
			outline = ImageIO.read(getClass().getResource("outline.png"));
		}catch (IOException e){
			e.printStackTrace();
		}
		try{
			exclamation = ImageIO.read(getClass().getResource("exclam.png"));
		}catch (IOException e){
			e.printStackTrace();
		}
		this.addMouseListener(new MouseListener() {
			
			public void mouseClicked(MouseEvent e) {
					xpos = e.getX();
					ypos = e.getY();
				}
	
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
				public void mousePressed(MouseEvent e) 	{}
				public void mouseReleased(MouseEvent e) {}
			});
		
	}


	public void start(){
		if(running){
			return;
			}
		
			else{
			running = true;
			thread = new Thread(this);
			thread.start();
		}		
	}
	public void run (){

		init();
		this.requestFocus();
		long lastTime = System.nanoTime();
		double ns = 1000000000 / 60; //one sec div by desired FPS
		double delta = 0;
		
		while (running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while(delta >= 1){
				//tick();
				render();
				delta--;
			}
	
		}
	}
	public void init(){
		//int rand = random.nextInt(40);
		int w = outline.getWidth();
		int h = outline.getHeight();
		for(int i = 0; i< w; i++){
			for(int k = 0; k<h; k++){
				int pixel = outline.getRGB(i, k);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				if(red == 255 && green == 0 && blue == 0) {objectList.add(new Wall(i, k, 1, 1));}
				if(red == 0 && green == 0 && blue == 255) {objectList.add(new Floor(i, k, 5, 1));}
				if(red == 0 && green == 255 && blue == 0) {objectList.add(new PropertyAlarm(i, k, 4, 1));}
				if(red == 255 && green == 255 && blue == 255) {objectList.add(new Alarmwindow(i, k, 2, 1));}
			}
		}
	}
	private void render(){
		this.createBufferStrategy(3);
		boolean running = true;
		
		BufferStrategy strat;
		
		while(running){
			strat = this.getBufferStrategy();
			g = strat.getDrawGraphics();
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 1000, 1000);
			g.setColor(Color.green);
			g.drawString("Window: Light_Blue", 780, 540);
			g.drawString("Property: Pink", 780, 570);
			g.drawString("Door: Deep_Blue", 780, 600);
			if(alarm == true){
				g.drawString("Alarm Activated: " + timeStamp , 20,600);
			}
			
			
			for(int i = 0; i < objectList.size(); i++){
				Collideable tempObject = objectList.get(i);
				
				if (tempObject.getId() == 1 ){tempObject.render(g);}
				if (tempObject.getId() == 5 ){tempObject.render(g);}
				if (tempObject.getId() == 2 && window.isTestingWindow()){tempObject.render(g);}
				if (tempObject.getId() == 4 && window.isSurveillance()){tempObject.render(g);}
			}
			
			Collideable simobject = new SimulatedObject(xpos, ypos, 0, 0);
			scale = window.getScale();
			simobject.setScale(scale);
			simobject.render(g);
			
			if(t < 15){
				t++;
			}else if (window.hasStarted()==true){
				
				simobject.render(g);
				
				if(t2 < rand){
					t2++;
				}else{
					xmov = random.nextInt(3) -1;
					ymov = random.nextInt(3) -1;
					t2 = 0;
					rand = random.nextInt((80 - 15)+1)+15;
					intersected = false;
					}
				
				xpos = xpos+window.getSpeed()*xmov;
				ypos = ypos+window.getSpeed()*ymov;
				t = 0;
			}else if (window.hasStarted()==false){
				simobject.render(g);
				t=0;
			}

			for(int i = 0; i < objectList.size(); i++){
				Collideable tempobject = objectList.get(i);
				if(tempobject.getBounds().intersects(simobject.getBounds())&& tempobject.getId()==2 && window.isTestingWindow()==true) {
					g.setColor(Color.GREEN);
					g.drawString("Alarm activated: Window", 400, 600);
					g.drawImage(exclamation, tempobject.getX()*15, tempobject.getY()*15, null);
					timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
					alarm = true;
					}
					if(tempobject.getBounds().intersects(simobject.getBounds())&& tempobject.getId()==4 && window.isSurveillance()==true) {
					g.setColor(Color.GREEN);
					g.drawString("Alarm activated: Property", 400, 600);
					g.drawImage(exclamation, tempobject.getX()*15, tempobject.getY()*15, null);
					timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
					alarm = true;
					}
				if(tempobject.getBounds().intersects(simobject.getBounds())&&tempobject.getId()==1 && intersected ==false) {
				
						xmov = xmov*-1;
						ymov = ymov*-1;
						intersected = true;
						rand = 10;t2 =0;
					}

			}
			
			
		/**	for (int i = 0; i<objectList.size(); i++){
				Collideable tempObject = objectList.get(i);
				if (simobject.getBounds().intersects(tempObject.getBounds()));
				System.out.println("detected");
			}**/
				
			strat.show();
			//g.dispose();
		}
	}
	
}
	/**public void init(Graphics g){
		int w = outline.getWidth();
		int h = outline.getHeight();
		scale = window.getScale();
	
		for(int i = 0; i< w; i++){
			for(int k = 0; k<h; k++){
				int pixel = outline.getRGB(i, k);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
					

					if(red == 255 && green == 0 && blue == 0) {
						g.setColor(Color.GRAY);
						g.fillRect(i*13, k*13, 13, 13);
					}
					if(red == 0 && green == 0 && blue == 255) {
						g.setColor(Color.LIGHT_GRAY);
						g.fillRect(i*13, k*13, 13, 13);
					}
					else if(red == 0 && green == 255 && blue == 0 && window.isSurveillance()==true) {
						g.setColor(Color.pink);
						g.fillRect(i*13, k*13, 13, 13);
					}
					else if(red == 255 && green == 255 && blue == 255 && window.isTestingWindow() == true) {
						g.setColor(Color.cyan);
						g.fillRect(i*13, k*13, 13, 13);
					}
					else if(red == 96 && green == 96 && blue == 96 && window.isTestingDoor() == true) {
						g.setColor(Color.BLUE);
						g.fillRect(i*13, k*13, 13, 13);
					}
				}
			
			
			g.setColor(Color.RED);//Draws Red Simulation Area
			g.fillOval(xpos, ypos, 6* scale, 6* scale);

			if(t < 10000){
				t++;
			}else if (window.hasStarted()==true){

				g.fillOval(xpos, ypos, 6* scale, 6* scale);

				xpos = xpos-window.getSpeed();
				ypos = ypos-window.getSpeed();
				t = 0;
			}else if (window.hasStarted()==false){
				g.fillOval(xpos, ypos, 6* scale, 6* scale);
				t=0;
			}
			}
	}**/

