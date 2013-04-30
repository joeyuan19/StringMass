
import java.awt.*;

public class testing extends BufferedApplet{
	public testing(){}
	Mass m = new Mass(10, 50, 50, 0, 0);
	public void render(Graphics g) {
		m.draw(g, 10);
		System.out.println(m.contains(mouseX, mouseY));
		animating = true;
	}
}
