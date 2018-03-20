package myFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReadTextWriteDB {

	static File file = new File("D:\\Desktop\\eclipse-workspace\\ReadAndFileWrite\\resources\\data.txt");
	public Connection conn;

	public static void main(String[] args) throws IOException, SQLException {
		ReadTextWriteDB readFileWrite = new ReadTextWriteDB();
		readFileWrite.readTextAndWriteDatabase();
	}

	private void readTextAndWriteDatabase() throws IOException, SQLException {
		String[] ar = null;
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(file));
			String lineStr;
			while ((lineStr = in.readLine()) != null) {
				System.out.println(lineStr);

				if (lineStr.equals("username,password,fullname,email"))
					continue;

				ar = lineStr.split(",");

				databaseConn();

				try {
					String sql = "INSERT INTO Users (username, password, fullname, email) VALUES (?, ?, ?, ?)";

					PreparedStatement statement = conn.prepareStatement(sql);
					statement.setString(1, ar[0]);
					statement.setString(2, ar[1]);
					statement.setString(3, ar[2]);
					statement.setString(4, ar[3]);

					int rowsInserted = statement.executeUpdate();
					if (rowsInserted > 0) {
						System.out.println("Inserted successfully!");
					}
				} catch (SQLException ex) {
					ex.printStackTrace();

				}
			}
		} catch (IOException e) {
			System.out.println("File Read Error");
		}
		in.close();
		conn.close();
	}

	public void databaseConn() {
		try {
			String url = "jdbc:mysql://localhost:3306/sampledb";
			String username = "root";
			String password = "besli";

			conn = DriverManager.getConnection(url, username, password);
			if (conn != null) {
				System.out.println("Connected to the database sampledb");
			}
		} catch (SQLException ex) {
			System.out.println("user/password is invalid");
			ex.printStackTrace();
		}
	}

}
