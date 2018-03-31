package myFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelAddDB {

	private static final String myFile = "D:\\Desktop\\eclipse-workspace\\ReadAndFileWrite\\resources\\data2.xlsx";
	static Connection conn = null;

	public static void main(String[] args) throws IOException, SQLException {
		ReadExcelAddDB readExcelAddDB = new ReadExcelAddDB();
		readExcelAddDB.readExcelAndWriteDatabase();
	}

	private void readExcelAndWriteDatabase() throws IOException, SQLException {
		FileInputStream excelFile = null;
		Workbook workbook = null;
		try {
			excelFile = new FileInputStream(new File(myFile));
			workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = datatypeSheet.iterator();

			while (rowIterator.hasNext()) {
				Row currentRow = rowIterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();

				String[] cellArray = new String[4];
				int index = 0;
				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					if (currentCell == null)
						break;
					cellArray[index] = currentCell.getStringCellValue();
					index++;
				}
				if (cellArray[0]==null) {
					continue;
				}
				getConnection();
				try {
					String sql = "INSERT INTO Users (username, password, fullname, email) VALUES (?, ?, ?, ?)";
					PreparedStatement statement = conn.prepareStatement(sql);
					statement.setString(1, cellArray[0]);
					statement.setString(2, cellArray[1]);
					statement.setString(3, cellArray[2]);
					statement.setString(4, cellArray[3]);

					int rowsInserted = statement.executeUpdate();
					if (rowsInserted > 0) {
						System.out.println("A new user was inserted successfully!");
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				System.out.println();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.close();
			if (workbook != null) {
				workbook.close();
			}
			if (excelFile != null) {
				excelFile.close();
			}
		}
	}

	private static void getConnection() {
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