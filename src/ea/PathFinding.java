package ea;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class PathFinding extends JPanel {
        
        ArrayList<Point> points;
        static PathFinding pf;
        int scale = 5;
        int MAX = 100;
        int MIN = 0;
        int AMOUNT = 900;
        ConvexHull hull;
        ConvexHull hull2;
        
        Point A, B, C, D;

        public static void main(String args[]){
                pf = new PathFinding();
                pf.run();
        }
        
        void setupGUI(){
                JFrame frame = new JFrame();
                frame.pack();
                //Assign it the custom panel so we can draw a rect for each bug
                frame.add(this);
                frame.setSize((MAX-MIN+10)*scale*3,(MAX-MIN+10)*scale*2);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().setBackground(Color.white);
                this.setBackground(Color.white);
                frame.setVisible(true);
        }
        
        void run(){                               
                //System.out.println(new ConvexHull(points).toString());
                hull = new ConvexHull(10*scale,10*scale, 20, 0,100);
                hull2 = new ConvexHull(50*scale,10*scale, 20, 0, 100);
                
                A = new Point(10,75);
                B = new Point(175,75);
                C = new Point(320, 75);
                D = new Point(250, 75);

                
                setupGUI();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            A.draw(g, MIN, scale, 1*scale, 1*scale, true);
            B.draw(g, MIN, scale, 1*scale, 1*scale, true);
            C.draw(g, MIN, scale, 1*scale, 1*scale, true);
            
            if (hull.isDone && hull2.isDone){
           
            	hull.drawLowerHull(g, MIN, scale);
	            hull.drawUpperHull(g, MIN, scale);
	            hull.drawBasicPoints(g, MIN, scale);
	            //new Line(A,hull.top).draw(g, MIN, scale, 1,1, Color.red);
	            findPathTo(g, A, hull);
	            findPathFrom(g,B,hull);
	            findPathTo(g, B, hull2);
	            findPathFrom(g,C, hull2);
	            
	            hull2.drawLowerHull(g, MIN, scale);
	            hull2.drawUpperHull(g, MIN, scale);
	            hull2.drawBasicPoints(g, MIN, scale);

            }
            
//            Line u = new Line(new Point(100,3), new Point(100,71));
//            for (Point mp : u.miniPoints){
//            	g.fillRect(((int)mp.x-MIN)*scale+100, ((int)mp.y-MIN)*scale+100, scale, scale);
//            }
        }
        
        void findPathTo(Graphics g, Point p, ConvexHull h){
        	boolean does = false;
        	Line main, inter;
        	//System.out.println(h.L.indexOf(h.top));
        	for (int i = h.L.indexOf(h.top); i < h.L.size(); i++){
        		main = new Line(p,h.L.get(i));//.drpw(g, MIN, scple, 1, 1, Color.red);
        		for (int j = 0; j < h.L.size()-1; j++){
        			if (i<=j){
	        			inter = new Line(h.L.get(j),h.L.get(j+1));//.drpw(g, MIN, scple, 1, 1, Color.red);
	        			if (main.doesIntersect(inter)){
	        				does = true;
	        			}
        			}
        		}
        		System.out.println("Checking yellows");
        		for (int j = 0; j < h.U.size()-1; j++){
        			if (j<=i){
	        			inter = new Line(h.U.get(j),h.U.get(j+1));//.drpw(g, MIN, scple, 1, 1, Color.red);
	        			if (main.doesIntersect(inter)){
	        				does = true;
	        			}
        			}
        		}
        		if (!does){
        			new Line(p,h.L.get(i)).draw(g, MIN, scale, 1, 1, Color.red);
        			return;
        		}
        		does = false;
        	}
        }
        
        void findPathFrom(Graphics g, Point p, ConvexHull h){
        	boolean does = false;
        	Line main, inter;
        	//new Line(p,h.L.get(0)).draw(g, MIN, scale, 1, 1, Color.red);
        	for (int i = h.L.indexOf(h.top); i >= 0; i--){
        		main = new Line(p,h.L.get(i));//.drpw(g, MIN, scple, 1, 1, Color.red);
        		for (int j = h.L.size()-1; j > 0; j--){
        			if (true){
	        			inter = new Line(h.L.get(j),h.L.get(j-1));//.drpw(g, MIN, scple, 1, 1, Color.red);
	        			if (main.doesIntersect(inter)){
	        				does = true;
	        			}
        			}
        		}
        		System.out.println("Checking yellows");
        		for (int j = h.U.size()-1; j > 0; j--){
        			if (i<=j){
	        			inter = new Line(h.U.get(j),h.U.get(j-1));//.drpw(g, MIN, scple, 1, 1, Color.red);
	        			if (main.doesIntersect(inter)){
	        				does = true;
	        			}
        			}
        		}
        		if (!does){
        			new Line(p,h.L.get(i)).draw(g, MIN, scale, 1, 1, Color.red);
        			return;
        		}
        		does = false;
        	}
        }
}
