package ea;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConvexHull {
        
        List<Point> points, U, L;
        double distanceU, distanceL;
        boolean isDone = false;
        int offsetX, offsetY;
        int offX, offY;
        Point top, bottom;

        ConvexHull(int offX, int offY, int amountOfPoints, int MIN, int MAX){
        		this.offX = offX;
        		this.offY = offY;
	        	points = new ArrayList<>();
	            for (int i = 0; i<amountOfPoints; i++){
	                    points.add(new Point(offX + new Random().nextInt((MAX-MIN)+1)+MIN,offY + new Random().nextInt((MAX-MIN)+1)+MIN));
	            }    
        	
                this.offsetX = 0;
                this.offsetY = 0;
                sort();
                //findClosestPointTo(new Point(0,0));
                //fixSort(findClosestPointTo(new Point(0,0)));
                findTopAndBottom();
                UpperHull(points);
                LowerHull(points);
                
                isDone = true;
                }
        
        public String toString(){
                String temp = "";
                for (Point p: points){
                        temp = temp + p.toString()+"\n";
                }
                return temp;
        }
        
        private void findTopAndBottom(){
        	top = new Point(0,10000);
        	bottom = new Point(0,0);
        	for (Point p : points){
        		if (p.y < top.y){
        			top = p;
        		}
        		if (p.y > bottom.y){
        			bottom = p;
        		}
        	}
        }
        
        private int findClosestPointTo(Point p){
        	boolean isSorted = false;
        	Point current = points.get(0);
        	int index = 0;
            for (int j = 0; j<points.size(); j++){
            	if (new Line(p,points.get(j)).getDistance() < new Line(p,current).getDistance()){
            		current = points.get(j);
            		index = j;
            	}
            }
            current.color = Color.pink;
            System.out.println(current.x+","+current.y+"which is index "+index);
            return index;
        }
        
        private void fixSort(int i){
        	if (i==0) return;
        	List<Point> temp1 = points.subList(0, i-1);
        	List<Point> temp2 = points.subList(0, points.size()-1);
        	temp2.addAll(temp1);
        	points = temp2;
        }
        
        private void sort(){
                boolean isSorted = false;
                for (int j = 0; j<points.size()-1; j++){
                        for (int i = 0; i<points.size()-1; i++){
                                isSorted = true;
                                if (points.get(i).x > points.get(i+1).x){
                                        float tempX, tempY;
                                        tempX = points.get(i).x;
                                        tempY = points.get(i).y;
                                        points.get(i).x = points.get(i+1).x;
                                        points.get(i).y = points.get(i+1).y;
                                        points.get(i+1).x = tempX;
                                        points.get(i+1).y = tempY;
                                        isSorted = false;
                                }
                                //if (isSorted) return;
                        }
                }
        }


        private boolean makeRightTurn(Point p1, Point p2, Point p3) {
                //System.out.print(p1.toString() + " "+p2.toString()+ " "+p3.toString());
                if ((p2.x-p1.x)*(p3.y-p1.y)-(p2.y-p1.y)*(p3.x-p1.x)<0){
                //        System.out.println(" is.");
                        return true;
                }
                //System.out.println(" isn't.");
                return false;
        }

        private void UpperHull(List<Point> points2) {

                U = new ArrayList<Point>();
                int currentUPos = -1;
                for (int i = 0; i<points.size(); i++){
                        currentUPos++;
                        U.add(currentUPos, points.get(i));
                                while (U.size() > 2 && !makeRightTurn(U.get(currentUPos-2), U.get(currentUPos-1), U.get(currentUPos))){
                                        U.remove(currentUPos-1);
                                        currentUPos--;
                                }
                }
                for (Point p : U){
                        p.color = Color.red;
                }
                
        }
        
                
        private void LowerHull(List<Point> points2) {
                L = new ArrayList<Point>();
                
                int currentLPos = -1;
                
                for (int i = points2.size()-1; i>=0; i--){
                        currentLPos++;
                        L.add(currentLPos, points2.get(i));
                                while (L.size() > 2 
                                		&& !makeRightTurn(
                                				L.get(currentLPos-2), 
                                				L.get(currentLPos-1), 
                                				L.get(currentLPos)))
                                {
                                        L.remove(currentLPos-1);
                                        //U.add(currentUPos-1,U.get(currentUPos));
                                        currentLPos--;
                                }                        
                }
                
                for (Point p : L) p.color = Color.green;                
        }
               
        
        void drawBasicPoints(Graphics g, int MIN, int scale){
        	for (Point p : points){                
                if (p.color == Color.red || p.color == Color.green) p.draw(g, MIN, scale, offsetX, offsetY, true);
                else p.draw(g, MIN, scale, offsetX, offsetY, false);
            }
        }

		 void drawUpperHull(Graphics g, int MIN, int scale) {
			 g.setColor(Color.green);
			 distanceU = 0;
			 for (int i = 0; i<U.size()-1 ; i++){
		            Line u = new Line(U.get(i), U.get(i+1));
		            distanceU += u.getDistance();
		            u.draw(g, MIN, scale, offsetX, offsetY, Color.cyan);
	         }		
			 g.drawString("Distance = "+distanceU, offX*scale+50*scale, offY*scale+100*scale );
		 }
		 
		 void drawLowerHull(Graphics g, int MIN, int scale) {
			 g.setColor(Color.red);
			 distanceL = 0;
			 for (int i = 0; i<L.size()-1 ; i++){
		            Line l = new Line(L.get(i), L.get(i+1));
		            distanceL += l.getDistance();
		            l.draw(g, MIN, scale, offsetX, offsetY, Color.orange);
	            }	
			 g.drawString("Distance = "+distanceL, offX*scale+50*scale, offY*scale-10*scale );
		 }
        
        
}
