package ea;

import java.awt.Color;
import java.awt.Graphics;

public class Point {
	
	public float x, y;
	Color color;
	
	Point (float x, float y){
		this.x = x;
		this.y = y;
		this.color = Color.black;
	}
	
	public String toString(){
		return "("+x+","+y+")";
	}
	
	void draw(Graphics g, int MIN, int scale, int offsetX, int offsetY, boolean text){
		g.setColor(color);
		g.fillRect(((int)x-MIN)*scale+offsetX, ((int)y-MIN)*scale+offsetY, scale, scale);
		if (text){
			g.drawString(toString(), ((int)x-MIN)*scale+offsetX, ((int)y-MIN)*scale+offsetY-1*scale);
		}
	}
	
	void draw(Graphics g, int MIN, int scale, int offsetX, int offsetY, Color color){
		g.setColor(color);
		g.fillRect(((int)x-MIN)*scale+offsetX, ((int)y-MIN)*scale+offsetY, scale, scale);
	}
}
