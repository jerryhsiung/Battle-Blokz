import java.sql.*;


public class DatabaseApp {
	Connection conn;
	PreparedStatement pstmt;
	Statement stmt;
	ResultSet rst;
	String word;
	
	public DatabaseApp(Connection conn){
		this.conn = conn;
	}
	
	
//	public static void main(String[] args) throws ClassNotFoundException, SQLException {
//		Class.forName("com.mysql.jdbc.Driver");
//		conn = DriverManager.getConnection("jdbc:mysql://localhost/201_project", "root", "");
		
		//addUser("may", "usc", "maywu@usc.edu");
		
		//updateScores("may", 15, true);
	
//		}	
	
	public void addUser(String username, String password, String email) {
		try {
			pstmt = conn.prepareStatement("INSERT INTO players(username, password, email, wins, numgames, sentlines) VALUES(?,?,?,?,?,?)");
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.setString(3, email);
			pstmt.setInt(4, 0);
			pstmt.setInt(5, 0);
			pstmt.setInt(6, 0);
			pstmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateScores(String username, int lines, boolean winner) {
		try {
			if(winner) {
				pstmt = conn.prepareStatement("UPDATE players SET sentlines = sentlines + ?, numgames = numgames + 1, wins = wins + 1 WHERE username = ?");
				pstmt.setInt(1, lines);
				pstmt.setString(2, username);
			}
			else {
				pstmt = conn.prepareStatement("UPDATE players SET sentlines = sentlines + ?, numgames = numgames + 1 WHERE username = ?");
				pstmt.setInt(1, lines);
				pstmt.setString(2, username);
			}
			
			pstmt.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void forgotPassword(String email) {
		//add code to send out an email
	}
	
	public boolean verifyLogin(String username, String password) {
		boolean flag = false;
		String temp;
		try {
			pstmt = conn.prepareStatement("SELECT password FROM players WHERE username = (?)");
			pstmt.setString(1, username);
			rst = pstmt.executeQuery();
			rst.next();
			temp = rst.getString("password");
			flag = temp.equals(password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag) {
			return true;
		}
		else
			return false;
	}
	
}
