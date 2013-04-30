
import java.awt.*;

public class StringThing {
	int[] x;
	double[] y, dy, a;
	int N;
	double yi;
	
	public StringThing (int[] x, double yi, double[] y, double[] dy, double[] a) {
		this.x = x;
		this.y = y;
		this.dy = dy;
		this.N = x.length;
		this.a = a;
		this.yi = yi;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.black);
		for (int n = 0; n < N - 1; n++) {
			g.drawLine(x[n], (int) y[n], x[n + 1], (int) y[n + 1]);
		}
	}
	public void physics(double k, double t, double g){
		for (int n = 1; n < N - 1; n++) {
			a[n] = -k*( (yi - y[n-1]) - 2*(yi - y[n]) + (yi - y[n+1]) ) + g;
		}
		for (int n = 1; n < N - 1; n++) {
			this.dy[n] += t*a[n];
			this.y[n]  += t*dy[n];
		}
	}
	public void interact(int mouseY, int index, boolean mouseDown, boolean wasMouseDown, boolean drag) {
		if (drag && mouseDown && wasMouseDown) {
			this.dy[index] = 0.0; 
			this.y[index] = mouseY;
		}
	}
}
