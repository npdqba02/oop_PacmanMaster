package chibi;

import javax.swing.JFrame;

public class Chibi extends JFrame{

	private static final long serialVersionUID = 1L;


	public Chibi() {
		add(new Model());
	}
	
	
	public static void main(String[] args) {
		Chibi chib = new Chibi();
		chib.setVisible(true);
		chib.setTitle("Chibi");
		chib.setSize(380,420);
		chib.setDefaultCloseOperation(EXIT_ON_CLOSE);
		chib.setLocationRelativeTo(null);
		
	}

}
