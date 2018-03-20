package myFile;

import java.io.BufferedWriter;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReadDBAddExcel {
	
	static File file = new File("D:\\Desktop\\eclipse-workspace\\ReadAndFileWrite\\resources\\file2.xlsx");
	public Connection conn;
	
	String name = null;
	String pass = null;
	String fullname = null;
	String email = null;

	BufferedWriter bufferedWriter;
	Statement statement;
	ResultSet result;


	public static void main(String[] args) throws SQLException {
		ReadDBAddExcel readDBAddExcel=new ReadDBAddExcel();
		readDBAddExcel.readDBAndAddExcel();
	}
	private void readDBAndAddExcel() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/sampledb";
		String username = "root";
		String password = "besli";

		conn = DriverManager.getConnection(url, username, password);

		String sql = "SELECT * FROM users";

		statement = conn.createStatement();
		result = statement.executeQuery(sql);
		if (conn != null) {
			System.out.println("Connected to the database sampledb");
		}
		int count = 0;

		while (result.next()) {
			name = result.getString("username");
			pass = result.getString("password");
			fullname = result.getString("fullname");
			email = result.getString("email");

			String output = "%d: %s - %s - %s - %s";
			System.out.println(String.format(output, ++count, name, pass, fullname, email));

		}
	}

}
