
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Mass {
	int x, r;
	double m, a, dy, y;
	Ellipse2D circle;
	boolean drag;
	
	public Mass(double m, int x, double y, double dy, double a) {
		this.m  =  m;
		this.x  =  x;
		this.y  =  y;
		this.dy = dy;
		this.a  = a;
	}
	public void draw(Graphics g, double c) {
		this.r = (int) ((c/10)*49 + 1);
		
		g.setColor(Color.red);
		g.fillOval(x - r/2,(int) (y - r/2), r, r);
		
		g.setColor(Color.black);
		g.drawOval(x - r/2,(int) (y - r/2), r, r);
	}
	public double getY(){
		return y;
	}
	public void setY(double y){
		this.y = y;
	}
	public void setDy(double dy){
		this.dy = dy;
	}
	public void setAy(double a){
		this.a = a;
	}
	public void physics(double k, double t, double y0, double y1, double y2, double g) {
		a = -k*( y0 - 2*y1 + y2 ) + g;
		this.dy += t*a;
		this.y  += t*dy;  
	}
	public boolean contains(int x, int y) {
		double c = Math.sqrt((x - this.x)*(x - this.x) + (y - this.y)*(y - this.y));
		if (c <= r/2) {
			return true;
		}
		else {
			return false;
		}
	}
}
