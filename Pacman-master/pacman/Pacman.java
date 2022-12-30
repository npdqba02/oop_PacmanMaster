package pacman;

import javax.swing.JFrame;
import menu.Menu;

public class Pacman extends JFrame {

	public Pacman() {
		add(new Model());
	}

	public static void main(String[] args) {
		Menu menu = new Menu();

	}

}
