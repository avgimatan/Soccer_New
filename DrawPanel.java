
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

public class DrawPanel extends JPanel {

	private Timer t;
	private SoccerField sf;
	private Ball b;
	private Team t1, t2;
	private boolean hasStarted;
	private boolean gameType;

	public DrawPanel() {
		sf = new SoccerField();
		b = new Ball();
		t1 = new Team(Color.YELLOW, 0);
		t2 = new Team(Color.RED, 1);
		t = new Timer();
		hasStarted = false;
		gameType = false;
		t.schedule(new MyTimerTask(), 2000, 30);
		addKeyListener(new MyKeyboardAdapter());
		setFocusable(true); // to get focus for keyboard
		requestFocusInWindow();
		addMouseListener(new MouseHelper());
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		int h = getHeight();
		int w = getWidth();
		Rectangle rg = sf.RightGateBorders();
		Rectangle lg = sf.LeftGateBorders();

		sf.setSize(w, h);
		sf.drawMe(g);
		if (!hasStarted) {
			b.goToInitialPosition(w, h);
			t1.reArrange(w, h);
			t2.reArrange(w, h);
		}

		// check goal
		if (rg.contains(b.getX(), b.getY()) && b.getTouchBorders()) {
			t1.addScore();
			b.setTouchBorders(false);
			t1.reArrange(w, h);
			t2.reArrange(w, h);
			b.goToInitialPosition(w, h);
		}
		if (lg.contains(b.getX(), b.getY()) && b.getTouchBorders()) {
			t2.addScore();
			b.setTouchBorders(false);
			t1.reArrange(w, h);
			t2.reArrange(w, h);
			b.goToInitialPosition(w, h);
		} // end check

		t1.setLimits(w, h);
		t2.setLimits(w, h);
		t1.drawMe(g);
		t2.drawMe(g);
		b.drawMe(g);

	}
	
	public void setGameType(boolean g){
		gameType = g;
	}

	private class MyTimerTask extends TimerTask {
		public void run() {
			hasStarted = true;
			// check borders
			b.checkAngle(sf, sf.getWidth(), sf.getHight());
			// check players
			t1.checkBall(b);
			t2.checkBall(b);
			b.move();

			// false - all players move, true - only goalkeeper move
			// true - for option AI vs Human, only on t2
			t1.move(b, false);
			t2.move(b, gameType);

			repaint();
		}
	}

	private class MyKeyboardAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			// only t2 is controlled by keyboard, AI vs Human
			if (gameType) {
				t2.movePlayers(b, e, sf);
			}
		}
	}

	private class MouseHelper extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			int x, y;
			x = e.getX();
			y = e.getY();
			b.setCenter(x, y);
		}
	}
}
