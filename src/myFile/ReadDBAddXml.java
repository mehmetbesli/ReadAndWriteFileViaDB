package myFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReadDBAddXml {

	private static String name;
	private static String pass;
	private static String fullname;
	private static String email;
	private static int count;
	Connection conn = null;
	Statement statement;
	ResultSet result;

	public static void main(String[] args) {
		Connection conn = null;
		Statement statement;
		ResultSet result;

		String url = "jdbc:mysql://localhost:3306/sampledb";
		String username = "root";
		String password = "besli";
		try {
			conn = DriverManager.getConnection(url, username, password);
			if (conn != null) {
				System.out.println("Connected to DB");
			} else {
				System.out.println("Could not connect to DB");
				throw new SQLException("Database baðlanamadý");
			}

			String sql = "SELECT * FROM users";

			statement = conn.createStatement();
			result = statement.executeQuery(sql);

			while (result.next()) {
				name = result.getString("username");
				pass = result.getString("password");
				fullname = result.getString("fullname");
				email = result.getString("email");

				String output = "%d: %s - %s - %s - %s";
				System.out.println(String.format(output, ++count, name, pass, fullname, email));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
