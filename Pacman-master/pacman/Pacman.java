package pacman;

import javax.swing.JFrame;
import menu.Menu;

public class Pacman extends JFrame{

	private static final long serialVersionUID = 1L;


	public Pacman() {
		add(new Model());
	}
	
	
	public static void main(String[] args) {
            Menu menu = new Menu();
		
	}

}
