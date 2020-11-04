package hello;

import java.awt.Graphics;

public abstract class Shape extends MyCanvas {
	int x, y, X, Y;
	public int dx,dy;
	protected int select;
	
	
	public Shape(int x, int y, int X, int Y) {
		this.x = x;
		this.y = y;
		this.X = X;
		this.Y = Y;
	}
	
	public abstract void paint(Graphics g);
	
	
	public void moveBy(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void moveTo(int newx, int newy) {
		x = newx;
		y = newy;
		
	}
	
	public boolean Isin(int tx, int ty) {
		if(x <= tx && tx <= X) {
			if(y <= ty && ty <= Y) {
			return true;
		}
	}
	return false;
	}

}


	
