import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.JFrame;

public class Game {
	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);
		JFrame f = new JFrame();
		DrawPanel dp = new DrawPanel();

		chooseGameType(s, dp);

		f.add(dp);
		f.setSize(800, 500);
		f.setLocation(100, 100);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public static void chooseGameType(Scanner s, DrawPanel dp) {
		int answer = 0;
		boolean isValidInput = false;
		while (!isValidInput) {
			try {
				System.out.println("Choose your game type:");
				System.out.println("1: AI vs AI");
				System.out.println("2: AI vs Human (play with keyboard)");
				answer = s.nextInt();
				isValidInput = true;
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				s.nextLine();
			}
		}
		setAnswer(answer, dp);
		while (answer < 1 || answer > 2) {
			System.out.println("choose 1 or 2");
			answer = s.nextInt();
			setAnswer(answer, dp);
		}
	}

	public static void setAnswer(int answer, DrawPanel dp) {
		if (answer == 1)
			dp.setGameType(false);
		if (answer == 2)
			dp.setGameType(true);
	}

}
