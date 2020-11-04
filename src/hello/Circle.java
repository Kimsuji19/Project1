package hello;


import java.awt.Graphics;

public class Circle extends Shape {
	
	public Circle(int x, int y, int w, int h) {
		super(x, y, x+w, y+h);
		select = 1;
	}
	
	public void paint(Graphics g) {
		if(x <= X) {
			if(y <= Y) {
				g.drawOval(x, y, X - x, Y - y);
			} else {
				g.drawOval(x, Y, X - x, y - Y);
			}
		} else {
			if(y <= Y) {
				g.drawOval(X, Y, x - X, Y - y);
			} else {
				g.drawOval(X, Y, x - X, y - Y);
			}
		}
		
	}

}
