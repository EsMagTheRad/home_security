package simulation;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Floor extends Collideable{

	public Floor(int x, int y, int id, int scale) {
		super(x, y, id, scale);
	}
	public void render(Graphics g){
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect( x*15, y*15, 15, 15);
	}
	public Rectangle getBounds() {
		return new Rectangle(100, 100, 0, 0);
	}
}
