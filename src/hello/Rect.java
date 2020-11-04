package hello;

import java.awt.Graphics;

public class Rect extends Shape {
	
	public Rect(int x, int y, int w, int h) {
		super(x, y, x+w, y+h);
		select = 2;
	}
	
	public void paint(Graphics g) {
		if(x <= X) {
			if(y <= Y) {
				g.drawRect(x, y, X - x, Y - y);
			} else {
				g.drawRect(x, Y, X - x, y - Y);
			}
		} else {
			if(y <= Y) {
				g.drawRect(X, Y, x - X, Y - y);
			} else {
				g.drawRect(X, Y, x - X, y - Y);
			}
		}
	}


}
