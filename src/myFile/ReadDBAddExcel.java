package myFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadDBAddExcel {

	public Connection conn;

	String name = null;
	String pass = null;
	String fullname = null;
	String email = null;

	BufferedWriter bufferedWriter;
	Statement statement;
	ResultSet result;

	public static void main(String[] args) throws SQLException, IOException {
		ReadDBAddExcel readDBAddExcel = new ReadDBAddExcel();
		readDBAddExcel.readDBAndAddExcel();
	}

	private void readDBAndAddExcel() throws IOException {
		String url = "jdbc:mysql://localhost:3306/sampledb";
		String username = "root";
		String password = "besli";
		XSSFWorkbook workbook = null;
		XSSFSheet spreadsheet = null;
		FileOutputStream out = null;

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
			int count = 0;
			// Create blank workbook
			workbook = new XSSFWorkbook();
			// Create a blank sheet
			spreadsheet = workbook.createSheet();
			// Write the workbook in file system
			out = new FileOutputStream(
					new File("D:\\Desktop\\eclipse-workspace\\ReadAndFileWrite\\resources\\file2.xlsx"));

			// Create row object
			XSSFRow row;
			int rowid = 0;

			while (result.next()) {
				name = result.getString("username");
				pass = result.getString("password");
				fullname = result.getString("fullname");
				email = result.getString("email");

				String output = "%d: %s - %s - %s - %s";
				System.out.println(String.format(output, ++count, name, pass, fullname, email));

				// This data needs to be written (Object[])
				List<String> empinfo = new ArrayList<>();
				// empinfo.put("1", new Object[] { name, pass, fullname, email });
				empinfo.add(name);
				empinfo.add(pass);
				empinfo.add(fullname);
				empinfo.add(email);

				row = spreadsheet.createRow(rowid++);
				int cellid = 0;
				for (String key : empinfo) {
					Cell cell = row.createCell(cellid++);
					cell.setCellValue(key);
				}
			}
			workbook.write(out);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (workbook != null)
				workbook.close();
			if (out != null) {
				out.close();
			}

		}

	}
}
