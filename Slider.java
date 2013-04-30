
import java.awt.*;

public class Slider {
	private static final long serialVersionUID = 1L;
	
	int x, y, w, h, slideW, slideH, slideY, slideMax, slideMin, slideLength, gap, c;
	boolean sliderClicked;
	double slideX;
	Color inactive = new Color(200,200,200);
	Color inactive2 = new Color(150,150,150);
	Color active = new Color(100,100,100);

	public Slider(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.gap = w/40;
		this.slideW = w/10 - gap/5;
		this.slideH = h/2 - gap;
		this.slideMax = x + w - (slideW/2 + gap);
		this.slideMin = x + (slideW/2 + gap);
		this.slideLength = slideMax - slideMin;
		this.slideX = x + gap + 4*slideLength/9;
		this.slideY = y + gap + slideH;
		this.sliderClicked = false;
	}
	public int getx(){
		return x;
	}
	public int gety(){
		return y;
	}
	public int getw(){
		return w;
	}
	public int geth(){
		return h;
	}
	public void draw(Graphics g) {
		
		boolean clicked;
		
		if (sliderClicked) {
			clicked = true;
		}
		else {
			clicked = false;
		}
		Color c = (clicked ? active : inactive2);
		
		g.setColor(inactive);
		g.fillRect(x,y,w,h);
		g.setColor(Color.black);
		g.drawRect(x,y,w,h);
		
		g.drawLine(slideMin, slideY + slideH/2, slideMax, slideY + slideH/2);
		
		g.setColor(c);
		g.fillRect((int)slideX, slideY, slideW, slideH);
		g.setColor(Color.black);
		g.drawRect((int)slideX, slideY, slideW, slideH);
	}
	public double behavior(int mouseX, int activeX, int activeY, boolean clickedUp, boolean clickedDown, boolean wasMouseDown, boolean mouseDown) {
		if (activeX >= slideMin - slideW/2 && activeX <= slideMax + slideW/2 && activeY >= slideY && activeY <= slideY + slideH && clickedDown) {
			sliderClicked = true;
		}
		else if (clickedUp) {
			sliderClicked = false;
		}
		if (sliderClicked && mouseDown && wasMouseDown) {
			int _mouseX = (mouseX < slideMin ? slideMin : mouseX > slideMax ? slideMax : mouseX);
			this.slideX = _mouseX - slideW/2;
		}
		
		return ((double) ((slideX + slideW/2)-slideMin) )/((double) slideLength);
	}
	
}
