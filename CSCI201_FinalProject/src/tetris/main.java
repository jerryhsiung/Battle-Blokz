package tetris;

import javax.swing.JFrame;

public class main extends JFrame {
	public main() {
		super("Tetris");
		this.setLocation(100,100);
		this.setSize(300,660);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gui board = new gui();
		
		this.add(board);
		
		this.setVisible(true);
	}
	
	public static void main(String [] args) {
		main test = new main();
	}
	
}
