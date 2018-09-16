
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Team {

	public final int NUM_PLAYERS = 5;
	private Player[] players;
	private Color tColor;
	private int side;
	private int width, height;
	private SoccerField sf;
	private int score;

	public Team() {
		this(Color.WHITE, 0);
	}

	public Team(Color c, int side) {
		score = 0;
		tColor = c;
		this.side = side;
		sf = new SoccerField();
		players = new Player[NUM_PLAYERS];
		players[0] = new Goalkeeper(tColor, side);
		players[1] = new LeftBrake(tColor, side);
		players[2] = new RightBrake(tColor, side);
		players[3] = new LeftForward(tColor, side);
		players[4] = new RightForward(tColor, side);
	}

	// rearranges players relatively to width and height
	public void reArrange(int w, int h) {
		for (int i = 0; i < players.length; i++) {
			players[i].goToInitialPosition(w, h);
		}
	}

	public void drawMe(Graphics g) {
		for (int i = 0; i < players.length; i++) {
			players[i].drawMe(g);
		}
		if (side == 0) {
			g.drawString("Yellow team: " + score, 50, 50);
		}
		if (side == 1) {
			g.drawString("Red team: " + score, sf.getWidth() - 100, 50);
		}
	}

	public void addScore() {
		score++;
	}

	// team is not moveable
	public void move(Ball b, boolean onlyGoalKeeper) {
		sf.setSize(width, height);
		if (onlyGoalKeeper) {
			players[0].decideWhatToDo(b, false, sf);
		} else {
			for (int i = 0; i < players.length; i++) {
				players[i].decideWhatToDo(b, false, sf);
			}
		}
	}

	// check the distance between ball and the players
	public void checkBall(Ball b) {
		for (int i = 0; i < players.length; i++) {
			double dist = Math.sqrt((b.getX() - players[i].getX()) * (b.getX() - players[i].getX())
					+ (b.getY() - players[i].getY()) * (b.getY() - players[i].getY()));

			if (dist < 2 * b.RADIUS) { // AI, ball touch the player
				players[i].decideWhatToDo(b, true, sf);
			}
		}
	}

	public void movePlayers(Ball b, KeyEvent e, SoccerField sf) {
		Rectangle lb = sf.rightForward0_leftBrake1();
		Rectangle rb = sf.leftForward0_rightBrake1();
		Rectangle lf = sf.rightBrake0_leftForward1();
		Rectangle rf = sf.leftBrake0_rightForward1();
		for (int i = 1; i < players.length; i++) {
			if (i == 1 && lb.contains(b.getX(), b.getY())) {
				getOrders(e, i, b);
			} else if (i == 2 && rb.contains(b.getX(), b.getY())) {
				getOrders(e, i, b);
			} else if (i == 3 && lf.contains(b.getX(), b.getY())) {
				getOrders(e, i, b);
			} else if (i == 4 && rf.contains(b.getX(), b.getY())) {
				getOrders(e, i, b);
			}
		}
	}

	public void getOrders(KeyEvent e, int i, Ball b) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP: // move forward
			players[i].setCenter(players[i].getX() + players[i].getSpeed() * Math.cos(players[i].getAlpha()),
					players[i].getY() + players[i].getSpeed() * Math.sin(players[i].getAlpha()));
			break;
		case KeyEvent.VK_DOWN: // move backward
			players[i].setCenter(players[i].getX() - players[i].getSpeed() * Math.cos(players[i].getAlpha()),
					players[i].getY() - players[i].getSpeed() * Math.sin(players[i].getAlpha()));
			break;
		case KeyEvent.VK_LEFT:
			players[i].setAlpha(players[i].getAlpha() - Math.PI / 4);
			break;
		case KeyEvent.VK_RIGHT:
			players[i].setAlpha(players[i].getAlpha() + Math.PI / 4);
			break;
		}
		// when you move to the ball, the player shot to the gate
		players[i].shotBall(b, sf);
	}

	public void setLimits(int w, int h) {
		width = w;
		height = h;
	}

}
