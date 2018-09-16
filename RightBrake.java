
import java.awt.Color;
public class RightBrake extends Player {

	public RightBrake(int x, int y, double a, Color tc, int s) {
		super(x, y, a, tc, s);
	}

	public RightBrake(Color tc, int s) {
		super(tc, s);
		playerSpeed = 2;
	}
	
	@Override
	public void decideWhatToDo(Ball b, boolean touchBall, SoccerField sf) {
		if (!touchBall) {
			// player follows the ball alpha
			alpha = Math.atan2(b.getY() - cy, b.getX() - cx);
			move(sf, b);
			shotBall(b, sf);
		} else { // we control the ball
					// change direction of ball
			if (b.dirX() > 0) { // -PI/2<alpha<PI/2
				b.setAlpha(Math.PI * Math.random() + Math.PI / 2);
			} else {// PI/2<alpha<3*PI/2
				b.setAlpha(Math.PI * Math.random() - Math.PI / 2);
			}
		}
	}

	@Override
	public void goToInitialPosition(int w, int h) {
		if (side == LEFT_SIDE)
			setCenter(w / 4 - 20, h * 3 / 4 + 20);
		else
			setCenter(w * 3 / 4 + 20, h / 4 - 20);
	}

	public void move(SoccerField sf, Ball b) {
		double dx, dy;
		dy = Math.sin(alpha);
		dx = Math.cos(alpha);
		if (side == LEFT_SIDE) {
			if (sf.rightBrake0_leftForward1().contains(b.getX(), b.getY())) {
				cx = (int) (cx + playerSpeed * dx);
				cy = (int) (cy + playerSpeed * dy);
			}
		}
		if (side == RIGHT_SIDE) {
			if (sf.leftForward0_rightBrake1().contains(b.getX(), b.getY())) {
				cx = (int) (cx + playerSpeed * dx);
				cy = (int) (cy + playerSpeed * dy);
			}
		}
	}
}
