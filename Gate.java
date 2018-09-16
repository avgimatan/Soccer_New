
import java.awt.Color;
import java.awt.Graphics;

public class Gate {

	private int left, top, w, h;

	public void setAll(int l, int t, int w, int h) {
		left = l;
		top = t;
		this.w = w;
		this.h = h;
	}

	public void drawMe(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawRect(left, top, w, h);
	}

}

