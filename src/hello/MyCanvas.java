package hello;


import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JPanel;

public class MyCanvas extends JPanel {
	
	Vector<Shape> s;
	int x, y, w, h, dx, dy;
	protected Object m;
	
	public MyCanvas() {
		s = new Vector<Shape>();
		}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		for(int k = 0; k < s.size(); k++) {
			Shape s1 = (Shape) s.elementAt(k);
			s1.paint(g);
		}
		
	}
	
	public Shape add(Shape s1) {
			s.add(s1);
			repaint();
			return s1;
	}

	public Shape getShape(int n) {
		return (Shape) s.elementAt(n);
	}
	
	public Shape position(int x, int y) {
		for(int i=s.size()-1;i>=0;i--) {
			Shape s2 = s.elementAt(i);
			if(x>=s2.x && x<=(s2.x+s2.X) && y>=s2.y && y<=(s2.y+s2.Y)) {
				s.add(s2);
				s.remove(i);
				
				return s2;
			}
		}
		return null;
	}
	
	public void clearRect(int i, int j, int k, int l) {
		// TODO Auto-generated method stub
		s.clear();
		repaint();
	}
	
	
	
}
