import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


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
		window = new GUI(database);
		window.run();
		window.play();
	}

}
