import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class Main {
	static Connection conn;
	static DatabaseApp database;
	static GUI window;

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException{
		
		//connect to database
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost/201_project", "root", "");
		database = new DatabaseApp(conn);
		
		//run the GUI interface
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		window = new GUI(database);
		window.run();
		window.play();
		
		new Timer().scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				window.updatelines();
			}
		},
		50, 50
		);
	}

}
