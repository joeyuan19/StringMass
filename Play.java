
import java.awt.*;

public class Play extends Button{
	public Play(int x, int y, int w, int h) {
		super(x,y,w,h);
	}
	public void Icon(Graphics g, boolean play) {
		int rectw = 2*h/3, gap = (h - rectw)/2, xMid = (x + w/2);
		
		if (! play) {
			int[] triX = {xMid - rectw/2, xMid + rectw/2, xMid - rectw/2};
			int[] triY = {y + gap, y + h/2, y + h - gap};
			
			g.setColor(Color.green);
			g.fillPolygon(triX,triY,3);
			
			g.setColor(Color.black);
			g.drawPolygon(triX,triY,3);
		}
		else {
			g.setColor(Color.yellow);
			g.fillRect(xMid - rectw/2, y + gap, rectw/2 - gap/2, rectw );
			
			g.setColor(Color.black);
			g.drawRect(xMid - rectw/2, y + gap, rectw/2 - gap/2, rectw );
			
			g.setColor(Color.yellow);
			g.fillRect(xMid + gap/2, y + gap, rectw/2 - gap/2, rectw );
			
			g.setColor(Color.black);
			g.drawRect(xMid + gap/2, y + gap, rectw/2 - gap/2, rectw );
		}
	} 
}
