
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Ball implements Moveable {
	public final int RADIUS = 8;
	private double bx, by;
	private double ballSpeed;
	private double alpha;
	private boolean touchBorders;

	public Ball() {
		ballSpeed = 4;
		alpha = 2 * Math.PI * Math.random();
		touchBorders = false;
	}

	@Override
	public void goToInitialPosition(int w, int h) {
		bx = w / 2;
		by = h / 2;
	}

	public void move() {
		bx = (bx + ballSpeed * dirX());
		by = (by + ballSpeed * dirY());

	}

	// return the x-component of ball direction
	public double dirX() {
		return Math.cos(alpha);
	}

	// return the y-component of ball direction
	public double dirY() {
		return Math.sin(alpha);
	}

	public void drawMe(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(new File("ball.jpg"));
		} catch (IOException ex) {
			System.out.println("File ball.jpg was not found\n");
		}
		TexturePaint tp = new TexturePaint(bi, new Rectangle((int) bx + 10, (int) by, 50, 50));
		g2d.setPaint(tp);

		g2d.fillOval((int) bx - RADIUS, (int) by - RADIUS, 2 * RADIUS, 2 * RADIUS);
		g2d.setColor(Color.BLACK);
		g2d.drawOval((int) bx - RADIUS, (int) by - RADIUS, 2 * RADIUS, 2 * RADIUS);
		g2d.dispose();

	}

	// change angle when ball meets borders
	public void checkAngle(SoccerField sf, int w, int h) {
		Rectangle r = sf.getBorders();
		if (dirX() > 0) { // check right border
			if (bx + RADIUS + dirX() * ballSpeed > r.getMaxX()) {
				touchBorders = true;
				alpha = Math.PI - alpha;
			}
		} else { // check left border
			if (bx - RADIUS + dirX() * ballSpeed < r.getMinX()) {
				touchBorders = true;
				alpha = Math.PI - alpha;
			}
		}
		if (dirY() > 0) { // check bottom border
			if (by + RADIUS + dirY() * ballSpeed > r.getMaxY())
				alpha = -alpha;
		} else { // check top border
			if (by - RADIUS + dirY() * ballSpeed < r.getMinY())
				alpha = -alpha;
		}
		// if ball getting out of borders
		if (bx > r.getMaxX() || by > r.getMaxY() || bx < r.getMinX() || by < r.getMinY()) {
			goToInitialPosition(w, h);
		}

	}

	public double getX() {
		return bx;
	}

	public double getY() {
		return by;
	}

	public void setAlpha(double a) {
		alpha = a;
	}

	public void setCenter(double x, double y) {
		bx = x;
		by = y;
	}

	public boolean getTouchBorders() {
		return touchBorders;
	}

	public void setTouchBorders(boolean b) {
		touchBorders = b;
	}

}
