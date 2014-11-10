
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class main extends JFrame {
	
	public static JLabel jl;
	public static int lines;
	
	public main() {
		super("Tetris");
		this.setLocation(100,100);
		this.setSize(300,660);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		lines = 0;
		
		jl = new JLabel("Lines Cleared - " + lines);
		
		gui board = new gui();
		
		this.add(board);
		this.add(jl,BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
	
	public static void updateLabel() {
		lines++;
		jl.setText("Lines Cleared - " + lines);
	}
	
	public static void main(String [] args) {
		main test = new main();
	}
	
}
