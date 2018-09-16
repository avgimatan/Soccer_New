import java.awt.Color;

public class Goalkeeper extends Player {

	public Goalkeeper(int x, int y, double a, Color tc, int s) {
		super(x, y, a, tc, s);
	}

	public Goalkeeper(Color tc, int s) {
		super(tc, s);
		playerSpeed = 2.5;
	}

	@Override
	public void decideWhatToDo(Ball b, boolean touchBall, SoccerField sf) {
		if (!touchBall) {
			move(sf);
			if (b.getY() > cy)
				alpha = Math.PI / 2;
			else
				alpha = -Math.PI / 2;
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
			setCenter(30, h / 2);
		else
			setCenter(w - 30, h / 2);
	}

	public void move(SoccerField sf) {
		double dy;
		dy = Math.sin(alpha);
		cy = (int) (cy + playerSpeed * dy);
		if (cy < sf.getHight() / 2 - 70) {
			cy = sf.getHight() / 2 - 70;
		} else if (cy > sf.getHight() / 2 + 70) {
			cy = sf.getHight() / 2 + 70;
		}
	}

}
