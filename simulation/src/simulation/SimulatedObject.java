package simulation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class SimulatedObject extends Collideable{

	public SimulatedObject(int x, int y, int id, int scale){
		super(x, y, id, scale);
	}
	public Rectangle getBounds() {
		 return new Rectangle(x, y, 6* scale, 6*scale);
		
	}

	public void render(Graphics g){
		g.setColor(Color.RED);
		g.fillOval(x, y, 6* scale, 6*scale);
	}

}
