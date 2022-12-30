package pacman;
import javax.swing.JFrame;


public class Pacman extends JFrame{

	public Pacman() {
		add(new Model());
	}
	
	
	public static void main(String[] args) {
		Menu menu = new Menu();
		menu.setVisible(true);
		menu.setTitle("Pacman");
		menu.setSize(380,420);
		menu.setDefaultCloseOperation(HIDE_ON_CLOSE);
		menu.setLocationRelativeTo(null);
	}

}
