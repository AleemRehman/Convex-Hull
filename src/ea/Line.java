package ea;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Line {
	ArrayList<Point> miniPoints;
	
	Point one, two;
	double distance;
	float gradient;
	float c;
	
	Line(Point one, Point two){
		
		miniPoints = new ArrayList<Point>();
		this.one = one;
		this.two = two;
		
		distance = getDistance();
		
		//(0,3)(10,10)
		//y = mx + c
		//x = (y-c)/m
		//Gradient is (y2-y1)/(x2-x1)
		if (two.x-one.x != 0){
			gradient = ((float)two.y-(float)one.y)/((float)two.x-(float)one.x);
		} else {
			gradient = 0;
		}
		c = one.y - gradient*one.x;
		
		if (two.x > one.x){
			Point temp = one;
			one = two;
			two = temp;
		}
		if (two.x - one.x != 0){
			for (float i = two.x; i<=one.x+0.01f ; i = i+0.01f){
				miniPoints.add(new Point(i,(int) (gradient*i+c)));
			}
		} else {
			if (two.y > one.y){
				Point temp = one;
				one = two;
				two = temp;
			}
			for (float i = two.y; i<=one.y+0.01f ; i = i+0.5f){
				miniPoints.add(new Point(one.x,i));
			}
		}
	}
	
	public void draw(Graphics g,int MIN,int scale,int offsetX,int offsetY, Color color){
		for (Point mp : miniPoints){
        	mp.draw(g, MIN, scale, offsetX, offsetY, color);
        }
	}
	
	double getDistance(){
		return Math.sqrt((two.x-one.x)*(two.x-one.x) + (two.y-one.y)*(two.y-one.y));
	}
	
	boolean doesIntersect(Line l2){
		float a = gradient;
		float c = this.c;
		float b = l2.gradient;
		float d = l2.c;
		Point ofIntersect = new Point((d-c)/(a-b), a*((d-c)/(a-b))+c);
		
		float left, right, up, down;
		
		if (l2.one.x < l2.two.x){
			left = l2.one.x+0.01f;
			right = l2.two.x-0.01f;
		} else {
			right = l2.one.x-0.01f;
			left = l2.two.x+0.01f;
		}
		if (l2.one.y < l2.two.y){
			up = l2.one.y;
			down = l2.two.y;
		} else {
			down = l2.one.y;
			up = l2.two.y;
		}
		System.out.print("Line "+toStringMin()+" and "+l2.toStringMin() +" intersect at "+ofIntersect+" L/R-U/D "+left+"/"+right+"-"+up+"/"+down);
		if (ofIntersect.x > left && ofIntersect.x < right
				&& ofIntersect.y > up && ofIntersect.y < down){
			System.out.println(" so return true");
			return true;
		}
		else{
			System.out.println(" so return false");
			return false;
		}
	}
	
	public String toString(){
		String temp = "";
		for (Point p: miniPoints){
			temp = temp + p.toString()+"\n";
		}
		return temp;
	}
	
	public String toStringMin(){
		return ("("+one.x+","+one.y+")-("+two.x+","+two.y+")");
	}
}
