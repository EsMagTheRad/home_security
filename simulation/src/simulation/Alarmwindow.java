package simulation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Alarmwindow extends Collideable{
	
	public Alarmwindow(int x, int y, int id, int scale) {
		super(x, y, id, scale);
	}
	
	public Rectangle getBounds() {
		return new Rectangle( x*15, y*15, 15, 15);
		
	}
	public void render(Graphics g){
		g.setColor(Color.CYAN);
		g.fillRect( x*15, y*15, 15, 15);
	}
}
