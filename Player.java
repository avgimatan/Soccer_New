import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Player implements Moveable {
	public final int LEFT_SIDE = 0;
	public final int RIGHT_SIDE = 1;
	public final int RADIUS = 10;

	protected double cx, cy;
	protected double alpha;
	protected double playerSpeed;
	protected Color teamColor;
	protected int side;

	public Player(int x, int y, double a, Color c, int s) {
		setCenter(x, y);
		setAlpha(a);
		teamColor = c;
		side = s;
	}

	public Player(Color c, int s) { // gets color and side
		this(0, 0, 0, c, s);
		if (s == 1) // if this is a right side player
			setAlpha(Math.PI);
	}

	public void setCenter(double x, double y) {
		cx = x;
		cy = y;
	}

	public void setAlpha(double a) {
		alpha = a;
	}

	public void drawMe(Graphics g) {
		g.setColor(teamColor);
		g.fillOval((int) cx - RADIUS, (int) cy - RADIUS, 2 * RADIUS, 2 * RADIUS);
		g.setColor(Color.BLACK);
		g.drawOval((int) cx - RADIUS, (int) cy - RADIUS, 2 * RADIUS, 2 * RADIUS);
		g.drawLine((int) cx, (int) cy, (int) (cx + RADIUS * Math.cos(alpha)), (int) (cy + RADIUS * Math.sin(alpha)));
	}

	public double getY() {
		return cy;
	}

	public double getX() {
		return cx;
	}

	public void shotBall(Ball b, SoccerField sf) {
		double angle = 0;
		if (playerBorders().contains(b.getX(), b.getY())) {
			double randY = Math.random() * 140 + (sf.getHight() / 2 - 90);
			if (side == LEFT_SIDE) {
				b.setCenter(cx + 2 * RADIUS, cy + 2 * RADIUS);
				angle = Math.atan2(randY - cy, sf.getWidth() - 20 - cx);
				b.setAlpha(angle);
			} else {
				b.setCenter(cx - 2 * RADIUS, cy + 2 * RADIUS);
				angle = Math.atan2(randY - cy, 20 - cx);
				b.setAlpha(angle);
			}
		}
	}

	public Rectangle playerBorders() {
		return new Rectangle((int) cx - RADIUS, (int) cy - RADIUS, 2 * RADIUS, 2 * RADIUS);
	}

	public abstract void decideWhatToDo(Ball b, boolean touchBall, SoccerField sf);

	public double getSpeed() {
		return playerSpeed;
	}

	public double getAlpha() {
		return alpha;
	}

}
